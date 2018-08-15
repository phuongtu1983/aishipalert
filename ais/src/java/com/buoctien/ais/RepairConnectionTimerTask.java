/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais;

import com.buoctien.ais.util.TimerUtil;
import java.util.TimerTask;

/**
 *
 * @author DELL
 */
public class RepairConnectionTimerTask extends TimerTask {

    private boolean scheduled;

    public RepairConnectionTimerTask() {
        this.scheduled = false;
    }

    public synchronized void schedule(long delay, long period) {
        if (!scheduled) {
            scheduled = true;
            TimerUtil.getInstance().schedule(this, delay, period);
        }
    }

    @Override
    public void run() {
        try {
            if (PublicObjects.isStarted() && !AISObjectList.isAisOK()) {
                PublicObjects.destroyObjects();
                PublicObjects.initObjects(PublicObjects.getConfigFileName());
            }
        } catch (Exception ex) {

        }
    }

}
