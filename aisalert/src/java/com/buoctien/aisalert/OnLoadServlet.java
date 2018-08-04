/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author Administrator
 */
public class OnLoadServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("On Load Servlet start");
        PublicObjects.initObjects(this.getServletContext());
        System.out.println("On Load Servlet started");
    }

    @Override
    public void destroy() {
        PublicObjects.destroyObjects();
        super.destroy();
    }

}
