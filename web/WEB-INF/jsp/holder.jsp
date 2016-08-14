<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 04.07.2016
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>magazines</title>
    <link rel="icon" href="../images/open-magazine-16px.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
    <fmt:setLocale value="${locale}"/>
    <fmt:setBundle basename="messages"/>
    <header>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${userURI}/main">
                    magazines
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <c:choose>
                    <c:when test="${user == null}">
                        <ul class="nav navbar-nav">
                            <li><a href="${userURI}/login"><fmt:message key="login"/> </a></li>
                            <li><a href="/user/signup"><fmt:message key="signup"/> </a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="nav navbar-nav navbar-right">
                            <c:if test="${cart != null}">
                                <li><a href="/user/cart">
                                    <span class="glyphicon glyphicon-shopping-cart"></span>
                                    <span class="label label-primary label-as-badge">${cart.size()}</span>
                                </a></li>
                            </c:if>
                            <li><a href="${userURI}/profile">
                                <span class="glyphicon glyphicon-user"></span> ${user.login}
                            </a></li>
                            <li><a href="${userURI}/logout">
                                <span class="glyphicon glyphicon-log-out"></span>
                                <fmt:message key="logout"/>
                            </a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="${userURI}/${command}?locale=en" class="locale-link" style="padding: 0 15px;margin: 9px 0;">
                            <img src="../images/usa.png" style="max-height: 32px">
                        </a>
                    </li>
                    <li>
                        <a href="${userURI}/${command}?locale=ru" class="locale-link" style="padding: 0 15px;margin: 9px 0;">
                            <img src="../images/ru.png" style="max-height: 32px">
                        </a>
                    </li>
                </ul>
                <c:if test="${ !(command eq 'search')}">
                    <form action="${userURI}/search" method="post" class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <fmt:message key="search.placeholder" var="search_placeholder"/>
                            <input type="text" name="magazine" class="form-control" placeholder="${search_placeholder}">
                        </div>
                        <button type="submit" class="btn btn-default"><fmt:message key="search"/> </button>
                    </form>
                </c:if>
            </div>
            </div>
        </nav>
    </header>
    <section>
        <jsp:include page="${page}"/>
    </section>
    <footer></footer>
</body>
</html>
