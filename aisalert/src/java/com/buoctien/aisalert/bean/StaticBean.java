/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert.bean;

import com.buoctien.aisalert.geoposition.Coordinates;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author DELL
 */
public class StaticBean {

    public static final double MidPointLatitude = 10.663402010340304;
    public static double MidPointLongtitude = 106.79608941078186;

    public static final int OutsideRadius = 2000;
    public static final int DisplayRadius = 1000;
    public static final int YellowRadius = 520;
    public static final int RedRadius = 314;
    public static final int RedSmallRadius = 200;

    public static final int YellowPointQuantity = 5;
    public static final ArrayList<Coordinates> ArrayCenterPoint = new ArrayList<Coordinates>(
            Arrays.asList(new Coordinates(10.66260333459467, 106.79513587939925),
                    new Coordinates(10.662226400982233, 106.79475031443121),
                    new Coordinates(10.663322933796216, 106.79588287214222),
                    new Coordinates(10.663760491420474, 106.79634085958105),
                    new Coordinates(10.664184868961435, 106.79679543725299),
                    new Coordinates(10.662978949781976, 106.79552010416523),
                    new Coordinates(10.663546984470017, 106.79615588901048),
                    new Coordinates(10.663977952040657, 106.7965698537846),
                    new Coordinates(10.664393792645135, 106.79700437380211),
                    new Coordinates(10.664600020294783, 106.79722331065)));
    
    public static final String PROPERTIES = "properties";
    public static final String COM_PORTS = "comports";
    public static final String ON_OFF = "onoff";

}
