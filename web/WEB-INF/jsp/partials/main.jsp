<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 04.07.2016
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="jumbotron background-blue">
    <div class="container">
        <h1>magazines</h1>
        <h3><fmt:message key="main.slogan"/></h3>
        <br>
    </div>
</div>
<jsp:include page="../includes/editions-list.jsp"/>
<nav>
    <ul class="pager">
        <c:if test="${previousPage != null}">
            <li><a href="${userURI}/main?page=${previousPage}">
                <span aria-hidden="true">&larr;</span>
                <fmt:message key="previous"/>
            </a></li>
        </c:if>
        <c:if test="${nextPage != null}">
            <li><a href="${userURI}/main?page=${nextPage}">
                <fmt:message key="next"/>
                <span aria-hidden="true">&rarr;</span>
            </a></li>
        </c:if>
    </ul>
</nav>