/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert;

import com.buoctien.alert.bean.AISBean;
import com.buoctien.alert.bean.AlertBean;
import com.buoctien.alert.util.WebServiceUtil;
import com.google.gson.Gson;

/**
 *
 * @author DELL
 */
public class AISObjectList {

    private static boolean wirelessOK = false;
    private static String testAlertType = AISBean.OFF_ALERT;
    private static boolean testConnection = false;

    public static void initObjects() {
        wirelessOK = false;
        testAlertType = AISBean.OFF_ALERT;
        testConnection = false;
    }

    public static AlertBean getAlert() {
        AlertBean bean = new AlertBean();
        try {
            String output = WebServiceUtil.getAISAlert();
            if (!output.isEmpty()) {
                Gson gson = new Gson();
                bean = gson.fromJson(output, AlertBean.class);
            }
        } catch (Exception ex) {
        }
        return bean;
    }

    public static synchronized boolean isWirelessOK() {
        return wirelessOK;
    }

    public static synchronized void setWirelessOK(boolean status) {
        if (wirelessOK != status) {
            wirelessOK = status;
        }
    }

    public static String getTestAlertType() {
        return testAlertType;
    }

    public static void setTestAlertType(String testAlertType) {
        AISObjectList.testAlertType = testAlertType;
    }

    public static boolean isTestConnection() {
        return testConnection;
    }

    public static void setTestConnection(boolean testConnection) {
        AISObjectList.testConnection = testConnection;
    }

}
