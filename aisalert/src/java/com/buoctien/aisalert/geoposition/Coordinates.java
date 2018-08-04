/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert.geoposition;

/**
 *
 * @author DELL
 */
public class Coordinates {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Coordinates() {
        setLatitude(0);
        setLongitude(0);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Coordinates(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }
}
