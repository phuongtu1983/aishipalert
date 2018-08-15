/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais.bean;

import com.buoctien.ais.geoposition.Coordinates;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author DELL
 */
public class StaticBean {

    public static final double MIDPOINTLATITUDE = 10.663402010340304;
    public static double MIDPOINTLONGTITUDE = 106.79608941078186;

    public static final int OUTSIDERADIUS = 3000;
    public static final int DISPLAYRADIUS = 1000;
    public static final int YELLOWRADIUS = 520;
    public static final int REDRADIUS = 314;
    public static final int REDSMALLRADIUS = 200;

    public static final int YELLOWPOINTQUANTITY = 5;
    public static final ArrayList<Coordinates> ARRAYCENTERPOINT = new ArrayList<Coordinates>(
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
