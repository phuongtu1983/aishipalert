/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author Administrator
 */
public class OnLoadServlet extends HttpServlet {

    @Override
    public void init() {
        PublicObjects.initObjects(this.getServletContext().getRealPath("/config.properties"));
        PublicObjects.initRepairConnectThread();
        PublicObjects.setStarted(true);
        System.out.println("On Load Servlet started");
    }

    @Override
    public void destroy() {
        PublicObjects.destroyObjects();
        PublicObjects.destroyRepairConnectThread();
        super.destroy();
        System.out.println("On Load Servlet destroy");
    }

}
