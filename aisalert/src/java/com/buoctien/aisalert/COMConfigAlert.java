/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.aisalert;

import com.buoctien.aisalert.bean.AISBean;
import com.buoctien.aisalert.bean.StaticBean;
import com.buoctien.aisalert.util.ConfigUtil;
import com.buoctien.aisalert.util.SerialUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class COMConfigAlert extends HttpServlet {

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
            String fileName = this.getServletContext().getRealPath("/config.properties");
            String save = request.getParameter("save");
            if (save != null && !save.isEmpty()) {
                if (request.getParameter("config") != null) {
                    try {
                        Properties props = new Properties();
                        props.setProperty("ais_port", request.getParameter("aisPort") == null ? "" : request.getParameter("aisPort"));
                        props.setProperty("ais_baudrate", request.getParameter("aisBaudrate") == null ? "" : request.getParameter("aisBaudrate"));
                        props.setProperty("wireless_port", request.getParameter("wirelessPort") == null ? "" : request.getParameter("wirelessPort"));
                        props.setProperty("wireless_baudrate", request.getParameter("wirelessBaudrate") == null ? "" : request.getParameter("wirelessBaudrate"));
                        ConfigUtil.saveConfig(fileName, props);
                    } catch (Exception ex) {
                    }
                } else if (request.getParameter("redalert") != null) {
                    AISObjectList.setTestAlertType(AISBean.RED_ALERT);
                } else if (request.getParameter("yellowalert") != null) {
                    AISObjectList.setTestAlertType(AISBean.YELLOW_ALERT);
                } else if (request.getParameter("turnoffalert") != null) {
                    AISObjectList.setTestAlertType(AISBean.OFF_ALERT);
                } else if (request.getParameter("tomainpage") != null) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("mainpage.do");
                    dispatcher.forward(request, response);
                    return;
                } else if (request.getParameter("testconnect") != null) {
                    AISObjectList.setTestConnection(true);
                }

            }
            Properties props = ConfigUtil.readConfig(fileName);
            request.setAttribute(StaticBean.PROPERTIES, props);

            ArrayList ports = SerialUtil.getSerialPorts();
            request.setAttribute(StaticBean.COM_PORTS, ports);

            request.setAttribute("wireless_status", (AISObjectList.isWirelessOK() ? "Connected" : "Not Connected"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("com_config.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            System.out.print("Exception: " + ex.toString());
        }
    }
}
