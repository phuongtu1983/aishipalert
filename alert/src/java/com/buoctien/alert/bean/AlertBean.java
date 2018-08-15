/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert.bean;

/**
 *
 * @author DELL
 */
public class AlertBean {

    private String alertArea;
    private int soundType;

    public AlertBean() {
        this.soundType = -1;
        this.alertArea = "";
    }

    public AlertBean(int soundType, String alertArea) {
        this.alertArea = alertArea;
        this.soundType = soundType;
    }

    public String getAlertArea() {
        return alertArea;
    }

    public void setAlertArea(String alertArea) {
        this.alertArea = alertArea;
    }

    public int getSoundType() {
        return soundType;
    }

    public void setSoundType(int soundType) {
        this.soundType = soundType;
    }

}
