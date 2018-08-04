/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.util.ConfigUtil;
import com.buoctien.aisalert.util.SerialUtil;
import gnu.io.SerialPort;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author DELL
 */
public class TestAlertThread extends Thread {

    String configFileName = "";
    String action = "";

    public TestAlertThread(String configFileName, String action) {
        this.configFileName = configFileName;
        this.action = action;
    }

    @Override
    public void run() {
        try {
            Properties props = ConfigUtil.readConfig(configFileName);
            String aisPort = props.getProperty("wireless_port");
            String aisBaudrate = props.getProperty("wireless_baudrate");
            SerialUtil serialUtil = new SerialUtil();
            SerialPort port = serialUtil.getSerialPort(aisPort, Integer.parseInt(aisBaudrate));
            if (port != null) {
                OutputStream outputStream = port.getOutputStream();
                if (!this.action.isEmpty()) {
                    if (this.action.equals("red")) {
                        outputStream.write(65);
                    } else if (this.action.equals("rednormal")) {
                        outputStream.write(66);
                    } else if (this.action.equals("redemergency")) {
                        outputStream.write(67);
                    } else if (this.action.equals("yellow")) {
                        outputStream.write(68);
                    } else if (this.action.equals("yellownormal")) {
                        outputStream.write(69);
                    } else if (this.action.equals("yellowemergency")) {
                        outputStream.write(70);
                    } else if (this.action.equals("off")) {
                        outputStream.write(71);
                    }
                }

                outputStream.close();
                port.close();
                port = null;
            }
        } catch (Exception ex) {
        }
    }

}
