/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.util.SerialUtil;
import com.buoctien.aisalert.util.TimerUtil;
import gnu.io.SerialPort;
import java.util.TimerTask;

/**
 *
 * @author DELL
 */
public class AISTimerTask extends TimerTask {

    private AISThread aisThread = null;
    private SerialPort dataPort;
    private final String writtenFileName;
    private final String configFileName;
    private boolean scheduled;

    public AISTimerTask(SerialPort dataPort, String configFileName, String writtenFileName) {
        this.dataPort = dataPort;
        this.configFileName = configFileName;
        this.writtenFileName = writtenFileName;
        scheduled = false;
    }

    public synchronized void schedule(long delay, long period) {
        if (!scheduled) {
            scheduled = true;
            TimerUtil.getInstance().schedule(this, delay, period);
        }
    }

    @Override
    public boolean cancel() {
        if (aisThread != null && aisThread.isAlive()) {
            aisThread.interrupt();
            aisThread = null;
        }
        return super.cancel();
    }

    @Override
    public void run() {
        try {
            if (aisThread == null || aisThread.isInterrupted()) {
                if (dataPort == null) {
                    dataPort = SerialUtil.initAlertPort(configFileName, "ais_port", "ais_baudrate");
                }
                if (dataPort == null) {
                    AISObjectList.setAisOK(false);
                    return;
                }
                AISObjectList.setAisOK(true);
                aisThread = new AISThread(writtenFileName, dataPort);
                aisThread.start();
            } else if (aisThread.isStoped()) {
                aisThread.interrupt();
                aisThread = null;
            }
        } catch (Exception ex) {

        }
    }
}
