/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class TestAlert extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tested = request.getParameter("tested");
            String action = "";
            if (tested != null && !tested.isEmpty()) {
                if (request.getParameter("red") != null) {
                    action="red";
                } else if (request.getParameter("rednormal") != null) {
                    action="rednormal";
                }else if (request.getParameter("redemergency") != null) {
                    action="redemergency";
                }else if (request.getParameter("yellow") != null) {
                    action="yellow";
                }else if (request.getParameter("yellownormal") != null) {
                    action="yellownormal";
                }else if (request.getParameter("yellowemergency") != null) {
                    action="yellowemergency";
                }else if (request.getParameter("off") != null) {
                    action="off";
                }
                String configFileName = this.getServletContext().getRealPath("/config.properties");
                TestAlertThread thread = new TestAlertThread(configFileName, action);
                thread.run();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("test_alert.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {

        }
    }

}
