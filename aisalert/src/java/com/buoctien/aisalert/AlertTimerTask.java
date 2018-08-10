/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.bean.AISBean;
import com.buoctien.aisalert.bean.AlertBean;
import com.buoctien.aisalert.util.ArduinoUtil;
import com.buoctien.aisalert.util.SerialUtil;
import com.buoctien.aisalert.util.TimerUtil;
import gnu.io.SerialPort;
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
    private final int changeAlert = 60; // 2 lan
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
            closePort();
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

        if (!AISObjectList.isAnyShipDisplay()) {
//            return;
        }
        AlertBean alert = AISObjectList.getAlert();
        if (alert == null) {
            return;
        }
        if (alertType.equals(alert.getAlertArea())) {
            if (changeAlertCount++ < changeAlert) {
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
        } catch (Exception ex) {

        }
    }

    private SerialPort initAlertPort() {
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
