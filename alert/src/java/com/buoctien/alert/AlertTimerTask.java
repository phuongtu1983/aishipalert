/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert;

import com.buoctien.alert.bean.AISBean;
import com.buoctien.alert.bean.AlertBean;
import com.buoctien.alert.util.ArduinoUtil;
import com.buoctien.alert.util.SerialUtil;
import com.buoctien.alert.util.TimerUtil;
import gnu.io.SerialPort;
import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author DELL
 */
public class AlertTimerTask extends TimerTask implements WirelessPortCloseEvent {

    private SerialPort alertDataPort = null;
    private final String configFileName;
    private boolean scheduled;
    private int secCount = 0;
    private final int resetSecond = 7200; // 2 tieng = 2 * 60 * 60
    private final int changeAlert = 30; // 30 lan = 60s = 30 * 2s
    private String alertType = AISBean.OFF_ALERT;
    private int changeAlertCount = 0;

    public AlertTimerTask(String configFileName) {
        this.configFileName = configFileName;
        this.scheduled = false;
        if (this.alertDataPort == null) {
            this.alertDataPort = initAlertPort();
        }
        sendDataArduino(AISBean.OFF_ALERT, 0);
    }

    public synchronized void schedule(long delay, long period) {
        if (!scheduled) {
            scheduled = true;
            TimerUtil.getInstance().schedule(this, delay, period);
        }
    }

    @Override
    public boolean cancel() {
        if (alertDataPort != null) {
            sendDataArduino(AISBean.OFF_ALERT, 0);
            closePort();
        }
        return super.cancel();
    }

    @Override
    public void run() {
        if (alertDataPort == null) {
            secCount = 0;
            alertType = AISBean.OFF_ALERT;
            changeAlertCount = 0;
            alertDataPort = initAlertPort();
            if (alertDataPort == null) {
                AISObjectList.setWirelessOK(false);
                return;
            }
        }

        if (secCount++ >= resetSecond) {
            System.out.println("closeAlertPort expired: " + new Date().toString());
            closePort();
            return;
        }
        AISObjectList.setWirelessOK(true);

        String testAlertType = AISObjectList.getTestAlertType();
        if (!testAlertType.isEmpty()) {
            sendDataArduino(testAlertType, 0);
            return;
        }
        if (AISObjectList.isTestConnection()) {
            closePort();
            this.alertDataPort = initAlertPort();
            AISObjectList.setTestConnection(false);
            return;
        }
        AlertBean alert = AISObjectList.getAlert();
        if (alert == null) {
            return;
        }
        if (alert.getAlertArea().isEmpty()) {
            if (alertType.equals(AISBean.OFF_ALERT)) {
                return;
            } else {
                //phong truong hop dang tu mau vang hoac mau do la chuyen ra ngoai vung
                if (changeAlertCount++ >= changeAlert) {
                    alertType = AISBean.OFF_ALERT;
                    changeAlertCount = 0;
                }
                sendDataArduino(AISBean.OFF_ALERT, 0);
                System.out.println("from not off to empty: " + new Date().toString());
                return;
            }
        }
        changeAlertCount = 0;
        alertType = sendDataArduino(alert.getAlertArea(), alert.getSoundType());
    }

    private void closePort() {
        try {
            if (alertDataPort != null) {
                alertDataPort.close();
                alertDataPort = null;
            }
            System.out.println("closeAlertPort: " + new Date().toString());
        } catch (Exception ex) {

        }
    }

    private SerialPort initAlertPort() {
        System.out.println("initAlertPort: " + new Date().toString());
        return SerialUtil.initAlertPort(configFileName, "wireless_port", "wireless_baudrate");
    }

    private String sendDataArduino(String light, int sound) {
        if (alertDataPort != null) {
            ArduinoUtil util = new ArduinoUtil(alertDataPort, this);
            return util.turnAlert(light, sound);
        }
        return AISBean.OFF_ALERT;
    }

    @Override
    public void terminatePort() {
        closePort();
    }
}
