<%-- 
    Document   : config
    Created on : May 3, 2018, 10:16:37 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.util.Properties"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configure parameters</title>
    </head>
    <body>
        <form action="/aisalert/testalert.do" method="POST">
            <table style="border-width: 0px">
                <tr>
                    <td><input type="submit" name="red" value="Test Red"/></td>
                    <td><input type="submit" name="rednormal" value="Test Red Normal"/></td>
                    <td><input type="submit" name="redemergency" value="Test Red Emergency"/></td>
                </tr>
                <tr>
                    <td><input type="submit" name="yellow" value="Test Yellow"/></td>
                    <td><input type="submit" name="yellownormal" value="Test Yellow Normal"/></td>
                    <td><input type="submit" name="yellowemergency" value="Test Yellow Emergency"/></td>
                </tr>
                <tr><td colspan="3"><input type="submit" name="off" value="Test Off"/></td></tr>
            </table>
            <input type="hidden" name="tested" value="1"/>
        </form>
    </body>
</html>
