/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.util.TimerUtil;
import gnu.io.SerialPort;
import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author DELL
 */
public class AISTimerTask extends TimerTask {
    
    private AISThread aisThread = null;
    private AISPortWrapper dataPort;
    private boolean scheduled;
    private boolean isStop;
    private final int resetSecond = 3600; // 1 tieng = 1 * 60 * 60
    private int secCount = 0;
    private final int noDataSecond = 3; // 3 lan
    private int noDataCount = 0;
    
    public AISTimerTask(AISPortWrapper dataPort) {
        this.dataPort = dataPort;
        this.scheduled = false;
        this.isStop = false;
        this.secCount = 0;
        this.noDataCount = 0;
    }
    
    public synchronized void schedule(long delay, long period) {
        if (!scheduled) {
            scheduled = true;
            TimerUtil.getInstance().schedule(this, delay, period);
        }
    }
    
    @Override
    public boolean cancel() {
        if (aisThread != null && !aisThread.isInterrupted()) {
            aisThread.setCancel(true);
            aisThread.interrupt();
            aisThread = null;
            dataPort.terminateAISPort();
            this.isStop = true;
        }
        return super.cancel();
    }
    
    @Override
    public void run() {
        try {
            if (this.isStop || dataPort == null) {
                return;
            }
            if (secCount++ >= resetSecond && AISObjectList.getListSize() == 0 && aisThread.isStoped()) {
                reconnectPort();
                secCount = 0;
                return;
            }
            if (aisThread == null || aisThread.isInterrupted()) {
                SerialPort aisDataPort = dataPort.getAisDataPort();
                if (aisDataPort == null) {
                    aisDataPort = dataPort.openPort();
                    if (aisDataPort == null) {
                        AISObjectList.setAisOK(false);
                        return;
                    }
                }
                AISObjectList.setAisOK(true);
                aisThread = new AISThread(aisDataPort);
                System.out.println("new Thread: " + new Date().toString());
                if (!aisThread.isAlive()) {
                    aisThread.start();
                }
            } else if (aisThread.isStoped()) {
                if (!aisThread.isHasData()) {
                    if (noDataCount++ >= noDataSecond) {
                        reconnectPort();
                        noDataCount = 0;
                        aisThread.setHasData(true);
                        return;
                    }
                } else {
                    noDataCount = 0;
                }
                aisThread.read();
            }
        } catch (Exception ex) {
            
        }
    }
    
    private void reconnectPort() {
        if (dataPort != null) {
            dataPort.terminateAISPort();
            dataPort.openPort();
        }
    }
}
