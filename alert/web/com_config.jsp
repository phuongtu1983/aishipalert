<%-- 
    Document   : config
    Created on : May 3, 2018, 10:16:37 AM
    Author     : Administrator
--%>

<%@page import="com.buoctien.alert.bean.SerialBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.buoctien.alert.bean.StaticBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.util.Properties"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configure parameters</title>
    </head>
    <body>
        <form action="/alert/comconfigalert.do" method="POST">
            <table style="border-width: 0px">
                <%
                    Properties props = (Properties) request.getAttribute(StaticBean.PROPERTIES);
                    ArrayList port = (ArrayList) request.getAttribute(StaticBean.COM_PORTS);
                    String wirelessPort = props.getProperty("wireless_port");
                    String wirelessBaudrate = props.getProperty("wireless_baudrate");
                    String wirelessStatus = (String)request.getAttribute("wireless_status");
                %>
                <tr>
                    <td height='100' style='padding-right: 10px'>Wireless (Profolic USB to Serial Comm Port)- Baudrate</td>
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
                        <input type="submit" name="config" value="Submit"/>
                        <input type="hidden" name="save" value="1"/>
                    </td>
                </tr>
                <tr>
                    <td>Wireless connect status: <%=wirelessStatus%></td>
                    <td colspan="2">
                        <input type="submit" name="testconnect" value="Test connect"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" name="redalert" value="Test Red Alert"/>
                        <input type="submit" name="yellowalert" value="Test Yellow Alert"/>
                        <input type="submit" name="turnoffalert" value="Turn off Alert"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
