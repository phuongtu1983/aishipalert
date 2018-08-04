<%-- 
    Document   : config
    Created on : May 3, 2018, 10:16:37 AM
    Author     : Administrator
--%>

<%@page import="com.buoctien.aisalert.bean.SerialBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.buoctien.aisalert.bean.StaticBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.util.Properties"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configure parameters</title>
    </head>
    <body>
        <form action="/aisalert/comconfigalert.do" method="POST">
            <table style="border-width: 0px">
                <%
                    Properties props = (Properties) request.getAttribute(StaticBean.PROPERTIES);
                    ArrayList port = (ArrayList) request.getAttribute(StaticBean.COM_PORTS);
                    String aisPort = props.getProperty("ais_port");
                    String aisBaudrate = props.getProperty("ais_baudrate");
                    String wirelessPort = props.getProperty("wireless_port");
                    String wirelessBaudrate = props.getProperty("wireless_baudrate");
                %>
                <tr>
                    <td height='100' style='padding-right: 10px'>Cổng kết nối thiết bị AIS - Baudrate</td>
                    <td>
                        <select name="aisPort">
                            <%
                                if (port != null) {
                                    for (int i = 0; i < port.size(); i++) {
                                        SerialBean bean = (SerialBean) port.get(i);
                                        String selected = "";
                                        if (bean.getName().equals(aisPort)) {
                                            selected = "selected";
                                        }
                            %>
                            <option value="<%=bean.getId()%>" <%=selected%>><%=bean.getName()%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <input type='text' name='aisBaudrate' value='<%=aisBaudrate%>'>
                    </td>
                </tr>
                <tr>
                    <td height='100' style='padding-right: 10px'>Cổng kết nối cảnh báo - Baudrate</td>
                    <td>
                        <select name="wirelessPort">
                            <%
                                if (port != null) {
                                    for (int i = 0; i < port.size(); i++) {
                                        SerialBean bean = (SerialBean) port.get(i);
                                        String selected = "";
                                        if (bean.getName().equals(wirelessPort)) {
                                            selected = "selected";
                                        }
                            %>
                            <option value="<%=bean.getId()%>" <%=selected%>><%=bean.getName()%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <input type='text' name='wirelessBaudrate' value='<%=wirelessBaudrate%>'>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <input type="submit" value="Submit"/>
                        <input type="hidden" name="save" value="1"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
