<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 04.07.2016
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-0 col-sm-offset-3 col-md-offset-4 col-lg-offset-4
            col-xs-12 col-sm-6 col-md-4 col-lg-4">
            <h2 class="center-align">
                <fmt:message key="register"/>
            </h2>
            <h4 class="center-align">
                <fmt:message key="register.features"/>
            </h4>
            <br>
            <form name="signup" method="post" action="/user/signup" class="login-form">
                <%--<input type="hidden" name="command" value="signupAcceptForm"> <br>--%>
                <br>
                <fmt:message key="login.placeholder" var="loginPlaceholder"/>
                <input type="text" name="login" value="${login}" placeholder="${loginPlaceholder}" class="form-control input-form"> <br>
                <c:if test="${errors.getError(\"login\") != null}">
                    <div class="alert alert-danger" role="alert">
                        ${errors.getError("login")}
                    </div>
                </c:if>
                <fmt:message key="email.placeholder" var="email_placeholder"/>
                <input type="text" name="email" value="${email}" placeholder="${email_placeholder}" class="form-control input-form"> <br>
                <c:if test="${errors.getError(\"email\") != null}">
                    <div class="alert alert-danger" role="alert">
                        ${errors.getError("email")}
                    </div>
                </c:if>
                <fmt:message key="password.placeholder" var="passPlaceholder"/>
                <input type="password" name="password" value="${password}" placeholder="${passPlaceholder}" class="form-control input-form"> <br>
                <c:if test="${errors.getError(\"password\") != null}">
                    <div class="alert alert-danger" role="alert">
                        ${errors.getError("password")}
                    </div>
                </c:if>
                <fmt:message key="password.confirm" var="pass_confirm_placeholder"/>
                <input type="password" name="passwordConfirm" value="${passwordConfirm}" placeholder="${pass_confirm_placeholder}" class="form-control input-form"> <br>
                <c:if test="${errors.getError(\"passwordConfirm\") != null}">
                    <div class="alert alert-danger" role="alert">
                            ${errors.getError("passwordConfirm")}
                    </div>
                </c:if>
                <button type="submit" value="Sign up" class="btn btn-primary btn-block btn-form">
                    <fmt:message key="signup"/>
                </button> <br>
            </form>
            <c:choose>
                <c:when test="${errors.getError(\"general\") == null}">
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
            <h4 class="center-align">
                <fmt:message key="haveAnAccount"/>
                <a href="/user/login">
                    <fmt:message key="login"/> >
                </a>
            </h4>
        </div>
    </div>
</div>
