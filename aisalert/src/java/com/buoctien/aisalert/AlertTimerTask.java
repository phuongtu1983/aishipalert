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
public class AlertTimerTask extends TimerTask {

    private SerialPort alertDataPort = null;
    private final String configFileName;
    private boolean scheduled;
    private int secCount = 0;
    private final int resetSecond = 7200; // 2 tieng = 2 * 60 * 60
    private final int changeToOff = 2; // 2 lan
    private String alertType = AISBean.OFF_ALERT;
    private int changeToOffCount = 0;

    public AlertTimerTask(String configFileName) {
        this.configFileName = configFileName;
        this.scheduled = false;
        if (this.alertDataPort == null) {
            this.alertDataPort = initAlertPort();
        }
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
            ArduinoUtil util = new ArduinoUtil(alertDataPort);
            util.turnAlert(AISBean.OFF_ALERT, 0);
            closePort();
        }
        return super.cancel();
    }

    @Override
    public void run() {
        if (alertDataPort == null) {
            secCount = 0;
            alertType = AISBean.OFF_ALERT;
            changeToOffCount = 0;
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
        if (AISObjectList.getListSize() == 0) {
            return;
        }
        AlertBean alert = AISObjectList.getAlert();
        if (alert == null) {
            return;
        }
        if ((alertType.equals(AISBean.RED_ALERT) || alertType.equals(AISBean.YELLOW_ALERT))
                && alert.getAlertArea().equals(AISBean.OFF_ALERT)) {
            if (changeToOffCount++ < changeToOff) {
                return;
            }
        }
        changeToOffCount = 0;
        ArduinoUtil util = new ArduinoUtil(alertDataPort);
        alertType = util.turnAlert(alert.getAlertArea(), alert.getSoundType());
    }

    private void closePort() {
        try {
            alertDataPort.close();
            alertDataPort = null;
        } catch (Exception ex) {

        }
    }

    private SerialPort initAlertPort() {
        return SerialUtil.initAlertPort(configFileName, "wireless_port", "wireless_baudrate");
    }
}
