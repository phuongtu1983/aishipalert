/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.bean.AISBean;
import com.buoctien.aisalert.bean.AlertBean;
import com.buoctien.aisalert.bean.StaticBean;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class AISObjectList {

    private final static ArrayList aisList = new ArrayList();
    private static boolean aisOK = false;
    private static boolean wirelessOK = false;
    private static String testAlertType = AISBean.OFF_ALERT;

    public static void addObject(AISBean bean) {
        AISBean obj = null;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getMMSI().equals(bean.getMMSI())) {
                obj.setPosition(bean.getPosition());
                return;
            }
        }
        aisList.add(bean);
    }

    public static void removeObject(AISBean bean) {
        AISBean obj = null;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getMMSI().equals(bean.getMMSI())) {
                aisList.remove(i);
                return;
            }
        }
    }

    public static boolean isContains(String key) {
        AISBean obj = null;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getMMSI().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static AISBean get(String key) {
        AISBean obj = null;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getMMSI().equals(key)) {
                return obj;
            }
        }
        return null;
    }

    public static ArrayList getList() {
        return aisList;
    }

    public static boolean isAnyShipDisplay() {
        AISBean obj = null;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getDistance() < StaticBean.DisplayRadius) {
                return true;
            }
        }
        return false;
    }

    public static AlertBean getAlert() {
        AISBean obj = null;
        AlertBean resultBean = new AlertBean();
        long currentMilisec = new Date().getTime();
        long hours = 0;
        for (int i = 0; i < aisList.size(); i++) {
            obj = (AISBean) aisList.get(i);
            if (obj.getMilisec() != 0) {
                hours = (currentMilisec - obj.getMilisec()) / (60 * 60 * 1000) % 24;
                if (hours >= 2) {
                    aisList.remove(i);
                    continue;
                }
            }
            if (AISBean.RED_ALERT.equals(obj.getAlertArea()) || AISBean.YELLOW_ALERT.equals(obj.getAlertArea())) {
                resultBean.setAlertArea(obj.getAlertArea());
                if (obj.getShipType() >= 60 && obj.getShipType() <= 89 && obj.getShipType() != 70) {
                    // 60 - 69 : Passenger
                    // 71 - 74 : hang hoa nguy hiem
                    // 80 - 89 : tanker
                    resultBean.setSoundType(2); // am thanh don dap
                } else if (obj.getShipType() == 70) {
                    // 70 : cac loai hang hoa khac
                    resultBean.setSoundType(1); // am thanh khong don dap
                } else {
                    resultBean.setSoundType(0); // tat am thanh
                }
                if (AISBean.RED_ALERT.equals(obj.getAlertArea())) {
                    break;
                }
            }
        }
        return resultBean;
    }

    public static void destroyObjects() {
        if (aisList == null) {
            return;
        }
        aisList.clear();
    }

    public static synchronized boolean isAisOK() {
        return aisOK;
    }

    public static synchronized void setAisOK(boolean status) {
        if (aisOK != status) {
            aisOK = status;
        }
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

}
