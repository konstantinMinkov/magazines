<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 09.07.2016
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="/WEB-INF/taglib.tld" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <%--<h1 class="center-align">Admin panel.</h1>--%>
    <form name="addEdition" method="get" action="/admin/modifyEdition">
        <button class="btn btn-primary">
            <fmt:message key="addEdition"/>
        </button>
    </form>
    <c:choose>
        <c:when test="${ !paymentUserMap.isEmpty()}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h1 class="center-align">
                        <fmt:message key="paymentsHistory"/>
                    </h1>
                </div>
                <div class="center-align">
                    <c:choose>
                        <c:when test="${errors == null}">
                            <br>
                        </c:when>
                        <c:otherwise>
                            <br>
                            <div class="alert alert-danger" role="alert">
                                <strong>
                                    <fmt:message key="oops"/>
                                </strong>
                                    ${errors.getError("general")}
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <form method="post" action="/admin/profile" class="form-inline">
                        <div class="form-group">
                            <label>
                                <fmt:message key="date.from"/>
                            </label>
                            <input type="datetime-local" name="from" class="form-control" value="${param.get("from")}">
                        </div>
                        <div class="form-group">
                            <label>
                                <fmt:message key="date.to"/>
                            </label>
                            <input type="datetime-local" name="to" class="form-control" value="${param.get("to")}">
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="search"/>
                        </button> <br>
                    </form>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th><fmt:message key="user"/></th>
                            <th><fmt:message key="price"/>, $</th>
                            <th><fmt:message key="paymentDate"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="paymentUser" items="${paymentUserMap.entrySet()}">
                            <tr>
                                <td>${paymentUser.key.id}</td>
                                <td>${paymentUser.value.login}</td>
                                <td>${paymentUser.key.price}</td>
                                <td><date:dateTimeFormatter languageTag="${locale}" datetime="${paymentUser.key.timeCreated}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <h1 class="center-align">
                <fmt:message key="paymentsNone"/>
            </h1>
        </c:otherwise>
    </c:choose>
    <nav>
        <ul class="pager">
            <c:if test="${previousPage != null}">
                <li class="prev">
                    <a href="${userURI}/profile?page=${previousPage}">
                        <span aria-hidden="true">&larr;</span>
                        <fmt:message key="previous"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${nextPage != null}">
                <li class="next">
                    <a href="${userURI}/profile?page=${nextPage}">
                        <fmt:message key="next"/>
                        <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
