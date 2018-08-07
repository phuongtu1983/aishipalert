<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page import="com.buoctien.aisalert.bean.StaticBean"%>
<html>
    <head>
        <title>Boat Alert System</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/style.css" rel="stylesheet"/>
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyDrAAurpEZNCEeHdIMZ1jJkMg8odoWZeVU"></script>
        <script src="js/script.js"></script>

    </head>
    <body>
        <form action="/aisalert/control.do" method="POST">
            <table style="border-width: 0px">
                <tr><td>
                        <%
                            int isOn = (int) request.getAttribute(StaticBean.ON_OFF);
                            if (isOn == 1) {
                        %>
                        <input type="submit" name="turnoff" value="Turn off"/>
                        <%
                        } else {
                        %>
                        <input type="submit" name="turnon" value="Turn on"/>
                        <%
                            }
                        %>
                    </td>
                    <td><input type="submit" name="configports" value="Ports"/></td>
                </tr>
            </table>
            <input type="hidden" name="submited" value="1"/>
        </form>
        <table style="width: 100%">
            <tr>
                <td>
                    <span id="alertSpan">Alert type</span>
                    <button onclick="enableGetAISAjax()">Turn on map</button> 
                    <button onclick="disableGetAISAjax()">Turn off map</button>                     
                    <div>AIS: <span id="aisSpan"></span></div>
                    <div>Alert: <span id="wirelessSpan"></span></div>
                </td>
            </tr>
            <tr><td><p id="map"></p></td></tr>
        </table>
    </body>
</html>
