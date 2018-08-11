/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.util.SerialUtil;
import gnu.io.SerialPort;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class AISPortWrapper implements AISPortEvent {

    private SerialPort aisDataPort = null;
    private String configFileName;

    public void initPort(String configFileName) {
        this.configFileName = configFileName;
        openPort();
    }

    public SerialPort getAisDataPort() {
        return aisDataPort;
    }

    public SerialPort openPort() {
        if (aisDataPort == null) {
            aisDataPort = SerialUtil.initAlertPort(configFileName, "ais_port", "ais_baudrate");
        }
        return aisDataPort;
    }

    public void closePort() {
        if (aisDataPort != null) {
            aisDataPort.close();
            aisDataPort = null;
        }
    }

    @Override
    public void terminateAISPort() {
        System.out.println("terminateAISPort: " + new Date().toString());
        closePort();
    }

}
