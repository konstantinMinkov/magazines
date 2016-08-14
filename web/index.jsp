<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 30.06.2016
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirecting...</title>
</head>
<body>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:set var="command" value="main" scope="request"/>
    <c:redirect url="/user"/>
</body>
</html>
