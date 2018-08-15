/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buoctien.ais;

import com.buoctien.ais.bean.AlertBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author DELL
 */
@Path("/json/ais")
public class GetAlertService {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public AlertBean getAlert() {
        AlertBean alert = new AlertBean();
        if (AISObjectList.isAnyShipDisplay()) {
            alert = AISObjectList.getAlert();
        }
        return alert;
    }
}
