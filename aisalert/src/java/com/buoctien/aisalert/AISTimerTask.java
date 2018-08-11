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

    public AISTimerTask(AISPortWrapper dataPort) {
        this.dataPort = dataPort;
        this.scheduled = false;
        this.isStop = false;
        this.secCount = 0;
    }

    public synchronized void schedule(long delay, long period) {
        if (!scheduled) {
            scheduled = true;
            TimerUtil.getInstance().schedule(this, delay, period);
        }
    }

    @Override
    public boolean cancel() {
        closeThread();
        return super.cancel();
    }

    private void closeThread() {
        if (aisThread != null && !aisThread.isInterrupted()) {
            aisThread.setCancel(true);
            aisThread.interrupt();
            aisThread = null;
            this.isStop = true;
        }
    }

    @Override
    public void run() {
        try {
            if (secCount++ >= resetSecond && AISObjectList.getListSize() == 0) {
                if (dataPort != null) {
                    dataPort.terminateAISPort();
                    closeThread();
                    this.isStop = false;
                }
                secCount = 0;
                return;
            }
            if (aisThread == null || aisThread.isInterrupted()) {
                if (this.isStop) {
                    return;
                }
                if (dataPort == null) {
                    return;
                }

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
                this.isStop = false;
            } else if (aisThread.isStoped()) {
                aisThread.run();
            } else if (aisThread.isError()) {
                if (dataPort != null) {
                    dataPort.terminateAISPort();
                    closeThread();
                    this.isStop = false;
                }
            }
        } catch (Exception ex) {

        }
    }
}
