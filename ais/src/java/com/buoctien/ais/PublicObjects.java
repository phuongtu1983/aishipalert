/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais;

/**
 *
 * @author DELL
 */
public class PublicObjects {

    private static AISPortWrapper aisPort = null;
    private static AISTimerTask aisTimer = null;
    private static RepairConnectionTimerTask repairTimer = null;
    private static String configFileName;
    private static boolean started = false;

    public static void initObjects(String fileName) {
        try {
            AISObjectList.initObjects();
            configFileName = fileName;
            if (aisTimer == null) {
                if (aisPort == null) {
                    aisPort = new AISPortWrapper();
                }
                if (aisPort.getAisDataPort() == null) {
                    aisPort.initPort(configFileName);
                    if (aisPort.getAisDataPort() != null) {
                        AISObjectList.setAisOK(true);
                    } else {
                        AISObjectList.setAisOK(false);
                    }
                }
                aisTimer = new AISTimerTask(aisPort);
                aisTimer.run();
                aisTimer.schedule(0, 1000);
            }
        } catch (Exception ex) {
        }
    }

    public static void destroyObjects() {
        try {
            if (aisTimer != null) {
                aisTimer.cancel();
                aisTimer = null;
            }
            if (aisPort == null) {
                aisPort.terminateAISPort();
                aisPort = null;
            }
            AISObjectList.destroyObjects();
        } catch (Exception ex) {
            System.out.println("destroyObjects:" + ex);
        }
    }

    public static void initRepairConnectThread() {
        try {
            if (repairTimer == null) {
                repairTimer = new RepairConnectionTimerTask();
                repairTimer.run();
                repairTimer.schedule(0, 2000);
            }
        } catch (Exception ex) {
        }
    }

    public static void destroyRepairConnectThread() {
        try {
            if (repairTimer != null) {
                repairTimer.cancel();
                repairTimer = null;
            }
        } catch (Exception ex) {
            System.out.println("destroyRepairConnectThread:" + ex);
        }
    }

    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        PublicObjects.started = started;
    }

    public static String getConfigFileName() {
        return configFileName;
    }

    public static boolean isTurnOn() {
        if (aisTimer == null) {
            return false;
        } else {
            return true;
        }
    }
}
