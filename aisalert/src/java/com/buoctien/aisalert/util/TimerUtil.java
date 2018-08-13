/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert.util;

import com.buoctien.aisalert.AISTimerTask;
import com.buoctien.aisalert.AlertTimerTask;
import com.buoctien.aisalert.RepairConnectionTimerTask;
import java.util.Timer;

/**
 *
 * @author DELL
 */
public class TimerUtil {

    private static boolean isCanceled = false;
    private Timer timer;
    private static TimerUtil instance = null;

    private TimerUtil() {
        timer = null;
        timer = new Timer();
    }

    private void reloadTimer() {
        if (!isCanceled) {
            timer.cancel();
            timer = new Timer();
        }
    }

    public void cancel() {
        isCanceled = true;
        timer.cancel();
    }

    public static synchronized TimerUtil getInstance() {
        if (instance == null) {
            instance = new TimerUtil();
        }
        return instance;
    }

    public void schedule(AISTimerTask task, long delay, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay, period);
            } catch (IllegalStateException ex) {
                reloadTimer();
            }
        }
    }

    public void schedule(AlertTimerTask task, long delay, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay, period);
            } catch (IllegalStateException ex) {
                reloadTimer();
            }
        }
    }
    
    public void schedule(RepairConnectionTimerTask task, long delay, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay, period);
            } catch (IllegalStateException ex) {
                reloadTimer();
            }
        }
    }
}
