/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert.bean;

import dk.dma.enav.model.geometry.Position;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class AISBean {

    private String MMSI;
    private int navStatus;
    private Position position;
    private int shipType;
    private double distance;
    private int navigation;
    private int navigationImage;
    private String name;
    private String alertArea;
    private long milisec;

    public String getMMSI() {
        return MMSI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlertArea() {
        return alertArea;
    }

    public boolean setAlertArea(String alertArea) {
        boolean result = true;
        if (alertArea.equals(this.alertArea)) {
            result = false;
        }
        this.alertArea = alertArea;
        return result;
    }

    public void setMMSI(String MMSI) {
        this.MMSI = MMSI;
    }

    public AISBean() {
        this.MMSI = "";
        this.name = "";
        this.navStatus = -1;
        this.position = null;
        this.shipType = -1;
        this.navigation = 0;
        this.navigationImage = 0;
        this.alertArea = "";
        this.distance = 0;
        this.milisec = 0;
    }

    public AISBean(String MMSI, int navStatus, Position position, int shipType, String alertArea, double distance, int navigationImage) {
        this.MMSI = MMSI;
        this.name = "";
        this.navStatus = navStatus;
        this.position = position;
        this.shipType = shipType;
        this.navigation = 0;
        this.navigationImage = navigationImage;
        this.alertArea = alertArea;
        this.distance = distance;
        this.milisec = new Date().getTime();
    }

    public int getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(int navStatus) {
        this.navStatus = navStatus;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getNavigation() {
        return navigation;
    }

    public void setNavigation(int navigation) {
        this.navigation = navigation;
    }

    public long getMilisec() {
        return milisec;
    }

    public void setMilisec(long milisec) {
        this.milisec = milisec;
    }

    public int getNavigationImage() {
        return navigationImage;
    }

    public void setNavigationImage(int navigationImage) {
        this.navigationImage = navigationImage;
    }

    public static final String RED_ALERT = "RED";
    public static final String YELLOW_ALERT = "YELLOW";
    public static final String OFF_ALERT = "OFF";

}
