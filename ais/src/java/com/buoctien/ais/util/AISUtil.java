/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais.util;

import dk.dma.ais.reader.AisReader;
import dk.dma.ais.reader.AisReaders;
import gnu.io.SerialPort;
import java.io.FileInputStream;

/**
 *
 * @author DELL
 */
public class AISUtil {

    public static AisReader readFromFile(String fileName) {
        try {
            AisReader reader = AisReaders.createReaderFromInputStream(new FileInputStream(fileName));
            return reader;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return null;
    }

    public static AisReader readFromSerialPort(SerialPort port) {
        try {
            if (port != null) {
                AisReader reader = AisReaders.createReaderFromInputStream(port.getInputStream());
                return reader;
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return null;
    }
}
