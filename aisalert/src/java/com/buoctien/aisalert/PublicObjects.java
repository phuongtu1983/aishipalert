/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.util.SerialUtil;
import gnu.io.SerialPort;
import javax.servlet.ServletContext;

/**
 *
 * @author DELL
 */
public class PublicObjects {

    private static SerialPort aisDataPort = null;

    private static AISTimerTask aisTimer = null;
    private static AlertTimerTask alertTimer = null;

    public static void initObjects(ServletContext context) {
        try {
            String configFileName = context.getRealPath("/config.properties");
            if (aisDataPort == null) {
                aisDataPort = SerialUtil.initAlertPort(configFileName, "ais_port", "ais_baudrate");
            }
            String writtenFileName = context.getRealPath("/result.txt");
            if (aisTimer == null) {
                new AISObjectList();
                aisTimer = new AISTimerTask(aisDataPort, configFileName, writtenFileName);
                aisTimer.run();
                aisTimer.schedule(0, 1000);
            }
            if (alertTimer == null) {
                alertTimer = new AlertTimerTask(configFileName);
                alertTimer.run();
                alertTimer.schedule(0, 2000);
            }
        } catch (Exception ex) {
        }
    }

    public static void destroyObjects() {
        try {
            if (aisDataPort != null) {
                aisDataPort.close();
                aisDataPort = null;
            }
            if (aisTimer != null) {
                aisTimer.cancel();
                aisTimer = null;
            }
            if (alertTimer != null) {
                alertTimer.cancel();
                alertTimer = null;
            }
            AISObjectList.destroyObjects();
            System.out.println("On Load Servlet stopped");
        } catch (Exception ex) {

        }
    }

    public static boolean isTurnOn() {
        if (aisTimer == null || alertTimer == null) {
            return false;
        } else {
            return true;
        }
    }
}
