/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais;

import com.buoctien.ais.util.TimerUtil;
import gnu.io.SerialPort;
import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author DELL
 */
public class AISTimerTask extends TimerTask {

    private AISThread aisThread = null;
    private AISPortWrapper dataPort = null;
    private boolean scheduled;
    private boolean isStop;
    private final int resetSecond = 7200; // 2 tieng = 2 * 60 * 60
    private int secCount = 0;
    private final int noDataSecond = 10; // 10 lan
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
            this.isStop = true;
            aisThread.setCancel(true);
            aisThread.interrupt();
            aisThread = null;
            if (dataPort != null) {
                dataPort.terminateAISPort();
            }
        }
        return super.cancel();
    }

    @Override
    public void run() {
        try {
            if (this.isStop || dataPort == null) {
                return;
            }
            SerialPort aisDataPort = dataPort.getAisDataPort();
            if (aisDataPort == null) {
                return;
            }
            if (secCount++ >= resetSecond && AISObjectList.getListSize() == 0 && aisThread.isStoped()) {
                System.out.println("AISTimerTask stopped: " + new Date().toString());
                AISObjectList.setAisOK(false);
                this.isStop = true;
                return;
            }
            if (aisThread == null || aisThread.isInterrupted()) {
                aisThread = new AISThread(aisDataPort);
                System.out.println("new AISThread: " + new Date().toString());
                if (!aisThread.isAlive()) {
                    aisThread.start();
                }
            } else if (aisThread.isStoped()) {
                if (!aisThread.isHasData()) {
                    if (noDataCount++ >= noDataSecond) {
                        System.out.println("AISTimerTask no data: " + new Date().toString());
                        AISObjectList.setAisOK(false);
                        this.isStop = true;
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
}
