/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais.geoposition;

/**
 *
 * @author DELL
 */
public class CoordinatesCalculations {

    public static boolean isInCircleArea(Coordinates coordinate, Coordinates center, int radius) {
        boolean res = false;
        if (getDistanceBetweenTwoPoints(coordinate, center) <= radius) {
            res = true;
        }
        return res;
    }

    public static double getDistanceBetweenTwoPoints(Coordinates c1, Coordinates c2) {
        double R = 6371000; // m
        double dLat = Math.toRadians(c2.getLatitude() - c1.getLatitude());
        double dLon = Math.toRadians(c2.getLongitude() - c1.getLongitude());
        double lat1 = Math.toRadians(c1.getLatitude());
        double lat2 = Math.toRadians(c2.getLatitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;

        return d;
    }
}
