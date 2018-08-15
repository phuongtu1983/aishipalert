/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert;

import com.buoctien.alert.util.ConfigUtil;
import java.util.Properties;

/**
 *
 * @author DELL
 */
public class PublicObjects {

    private static AlertTimerTask alertTimer = null;
    private static String configFileName;
    private static String aisUrl;

    public static void initObjects(String fileName) {
        try {
            AISObjectList.initObjects();
            configFileName = fileName;
            try {
                Properties props = ConfigUtil.readConfig(fileName);
                if (props != null) {
                    String url = props.getProperty("ais_url");
                    if (url != null && !url.isEmpty()) {
                        aisUrl = url;
                    }
                }
            } catch (Exception e) {
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
            if (alertTimer != null) {
                alertTimer.cancel();
                alertTimer = null;
            }
        } catch (Exception ex) {
            System.out.println("destroyObjects:" + ex);
        }
    }

    public static String getConfigFileName() {
        return configFileName;
    }

    public static boolean isTurnOn() {
        if (alertTimer == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String getAisUrl() {
        return aisUrl;
    }

}
