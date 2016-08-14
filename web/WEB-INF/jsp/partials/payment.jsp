<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 14.08.2016
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container" style="width: 100%; justify-content: center">
    <div style="width: 350px; margin: auto" class="padding-top-100">
        <form class="login-form" method="POST" action="/user/payment">
            <br>
            <fmt:message key="card.fullName" var="fullNamePlaceholder"/>
            <input type="text" name="fullName" value="${param.get("fullName")}" class="form-control input-form"
                   placeholder="${fullNamePlaceholder}"> <br>
            <fmt:message key="card.number" var="cardNumberPlaceholder"/>
            <input type="text" name="cardNumber" value="${param.get("cardNumber")}" class="form-control input-form"
                   placeholder="${cardNumberPlaceholder}"> <br>
            <fmt:message key="card.date" var="cardDatePlaceholder"/>
            <input type="text" name="date" value="${param.get("date")}" class="form-control input-form"
                   placeholder="${cardDatePlaceholder}" onfocus="(this.type='date')"> <br>
            <input type="number" name="cv" value="${param.get("cv")}" class="form-control input-form" placeholder="CV" min="001" max="999"> <br>
            <button type="submit" class="btn btn-success btn-block btn-form">
                <fmt:message key="pay"/>
            </button> <br>
        </form>
        <c:if test="${errors != null && errors.hasErrors()}">
            <div class="alert alert-danger" role="alert">
                <strong>
                    <fmt:message key="oops"/>
                </strong>
                ${errors.getError("general")}
            </div>
        </c:if>
    </div>
</div>