/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import javax.servlet.ServletContext;

/**
 *
 * @author DELL
 */
public class PublicObjects {

    private static AISPortWrapper aisPort = null;
    private static AISTimerTask aisTimer = null;
    private static AlertTimerTask alertTimer = null;
    private static String configFileName;

    public static void initObjects(ServletContext context) {
        try {
            configFileName = context.getRealPath("/config.properties");
            if (aisTimer == null) {
                new AISObjectList();
                if (aisPort == null) {
                    aisPort = new AISPortWrapper();
                }
                if (aisPort.getAisDataPort() == null) {
                    aisPort.initPort(configFileName);
                }
                aisTimer = new AISTimerTask(aisPort);
                aisTimer.run();
                aisTimer.schedule(0, 1000);
            }
            if (alertTimer == null) {
                alertTimer = new AlertTimerTask(configFileName);
                alertTimer.run();
                alertTimer.schedule(0, 1000);
            }
        } catch (Exception ex) {
        }
    }

    public static void destroyObjects() {
        try {
            if (alertTimer != null) {
                alertTimer.cancel();
                alertTimer = null;
            }
            if (aisTimer != null) {
                aisTimer.cancel();
                aisTimer = null;
            }
            AISObjectList.destroyObjects();
            System.out.println("On Load Servlet stopped");
        } catch (Exception ex) {
            System.out.println("destroyObjects:" + ex);
        }
    }

    public static boolean isTurnOn() {
//        if (aisTimer == null || alertTimer == null) {
        if (aisTimer == null) {
            return false;
        } else {
            return true;
        }
    }
}
