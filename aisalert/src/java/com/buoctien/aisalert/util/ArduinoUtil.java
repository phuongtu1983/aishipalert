/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert.util;

import com.buoctien.aisalert.bean.AISBean;
import gnu.io.SerialPort;
import java.io.OutputStream;

/**
 *
 * @author DELL
 */
public class ArduinoUtil {

    private SerialPort serialPort = null;

    public ArduinoUtil(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public String turnAlert(String alert, int soundType) {
        if (serialPort == null) {
            return AISBean.OFF_ALERT;
        }
        try {
            OutputStream outputStream = serialPort.getOutputStream();
            if (alert.equals(AISBean.RED_ALERT)) {
                switch (soundType) {
                    case 0:
                        outputStream.write(65);
                        break;
                    case 1:
                        outputStream.write(66);
                        break;
                    case 2:
                        outputStream.write(67);
                        break;
                }
                return AISBean.RED_ALERT;
            } else if (alert.equals(AISBean.YELLOW_ALERT)) {
                switch (soundType) {
                    case 0:
                        outputStream.write(68);
                        break;
                    case 1:
                        outputStream.write(69);
                        break;
                    case 2:
                        outputStream.write(70);
                        break;
                }
                return AISBean.YELLOW_ALERT;
            } else if (alert.equals(AISBean.OFF_ALERT)) {
                // turn off alert
                outputStream.write(71);
                return AISBean.OFF_ALERT;
            }
            outputStream.close();
        } catch (Exception ex) {
        }
        return AISBean.OFF_ALERT;
    }
}
