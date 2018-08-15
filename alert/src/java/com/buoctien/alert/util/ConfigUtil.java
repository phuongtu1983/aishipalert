/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.alert.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author DELL
 */
public class ConfigUtil {

    public static Properties readConfig(String path) {
        File configFile = null;
        FileReader reader = null;
        try {
            configFile = new File(path);
            reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            return props;
        } catch (FileNotFoundException ex) {
            // file does not exist
            System.out.print("FileNotFoundException: " + ex.toString());
        } catch (IOException ex) {
            // I/O error
            System.out.print("IOException: " + ex.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.print("IOException: " + e.toString());
                }
            }
        }
        return null;
    }

    public static void saveConfig(String path, Properties props) {
        File configFile = null;
        FileWriter writer = null;
        try {
            configFile = new File(path);
            writer = new FileWriter(configFile);
            props.store(writer, "");
        } catch (FileNotFoundException ex) {
            // file does not exist
            System.out.print("FileNotFoundException: " + ex.toString());
        } catch (IOException ex) {
            // I/O error
            System.out.print("IOException: " + ex.toString());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.print("IOException: " + e.toString());
                }
            }
        }
    }
}
