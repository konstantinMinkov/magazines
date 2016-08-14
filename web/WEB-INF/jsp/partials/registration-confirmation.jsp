<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 31.07.2016
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <div class="center-align margin-top">
        <h2 style="margin-top: 80px">
            <fmt:message key="message.send"/> <br>
            <fmt:message key="confirmationCode.enter"/>
        </h2>
        <h3><fmt:message key="attempts.left"/> <b>${attempts}</b></h3><br>
        <form action="/user/confirm" method="post" style="margin-top: 80px; width: 100%; max-width: 250px; display: inline-block">
            <input type="text" name="confirmationCode" class="form-control input-form"><br>
            <button class="btn btn-primary btn-block btn-form">
                <fmt:message key="submit"/>
            </button>
        </form>
    </div>
</div>
