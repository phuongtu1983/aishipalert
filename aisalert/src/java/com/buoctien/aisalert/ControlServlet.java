package com.buoctien.aisalert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class ControlServlet extends HttpServlet {

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
            AISObjectList.setTestAlertType("");
            AISObjectList.setTestConnection(false);
            String redirectPage = "mapservlet.do";
            String submited = request.getParameter("submited");
            if (submited != null && !submited.isEmpty()) {
                if (request.getParameter("turnon") != null) {
                    PublicObjects.initObjects(this.getServletContext());
                } else if (request.getParameter("turnoff") != null) {
                    PublicObjects.destroyObjects();
                }else if (request.getParameter("configports") != null) {
                    redirectPage = "comconfigalert.do";
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPage);
            dispatcher.forward(request, response);
        } catch (Exception ex) {

        }
    }
}
