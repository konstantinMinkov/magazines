<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 04.07.2016
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="vk" var="vk"/>
<fmt:setBundle basename="messages"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-0 col-sm-offset-3 col-md-offset-4 col-lg-offset-4
            col-xs-12 col-sm-6 col-md-4 col-lg-4">
            <div class="logo-div center-align">
                <a href="${userURI}/main" style="text-decoration: none">
                    <img src="../images/open-magazine.png" class="picture-logo">
                    <h2 class="text-blue">
                        magazines
                    </h2>
                </a>
            </div>
            <h2 class="center-align" style="margin-top: -23px">
                <fmt:message key="enter"/>
            </h2>
            <br>
            <form name="login" method="post" action="${userURI}/login" class="login-form">
                <br>
                <fmt:message key="login.placeholder" var="loginPlaceholder"/>
                <input type="text" name="login" value="${login}" placeholder="${loginPlaceholder}" class="form-control input-form"> <br>
                <fmt:message key="password.placeholder" var="passPlaceholder"/>
                <input type="password" name="password" value="${password}" placeholder="${passPlaceholder}" class="form-control input-form"> <br>
                <button type="submit" value="Log in" class="btn btn-primary btn-block btn-form">
                    <fmt:message key="login"/>
                </button> <br>
            </form>
            <c:choose>
                <c:when test="${errors == null}">
                    <br>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" role="alert">
                        <strong>
                            <fmt:message key="oops"/>
                        </strong>
                        ${errors.getError("general")}
                    </div>
                </c:otherwise>
            </c:choose>
            <%--<h3>--%>
                <%--<a href="https://oauth.vk.com/authorize?client_id=${clientId}&redirect_uri=${redirectURI}&scope=email&response_type=code&display=page">--%>
                    <%--Login with VK--%>
                <%--</a>--%>
            <%--</h3>--%>
            <div style="height: 20px; margin-top: -23px;" class="center-align">
                <hr class="float-left divider-login"/><i><fmt:message key="or"/></i><hr class="float-right divider-login"/>
            </div>
            <fmt:message bundle="${vk}" key="clientId" var="clientId"/>
            <fmt:message bundle="${vk}" key="redirectURI" var="redirectURI"/>
            <%--<form method="get" action="https://oauth.vk.com/authorize?client_id=${clientId}&redirect_uri=${redirectURI}&scope=email&response_type=code&display=page">--%>
                <%--<button type="submit" class="btn btn-primary btn-vk btn-block input-form">--%>
                    <%--<img height="100%" class="float-left" src="../images/vk.png"/>--%>
                    <%--<fmt:message key="login.vk"/>--%>
                <%--</button>--%>
            <%--</form>--%>
            <%--<button type="hidden" class="btn" style="height: 0; width: 0; display: none"></button>--%>
            <br>
            <a  style="margin-top: -8px" href="https://oauth.vk.com/authorize?client_id=${clientId}&redirect_uri=${redirectURI}&scope=email&response_type=code&display=page" class="btn btn-primary btn-vk btn-block input-form">
                <img height="100%" class="float-left" src="../images/vk.png"/><fmt:message key="login.vk"/></a>
            <%--<br>--%>
            <h4 class="center-align" style="margin-top: 32px">
                <fmt:message key="notAMember"/>
                <a href="/user/signup">
                    <fmt:message key="signup"/> >
                </a>
            </h4>
        </div>
    </div>
</div>
