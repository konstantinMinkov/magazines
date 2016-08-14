<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 08.07.2016
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<c:set scope="page" var="totalSum" value="0"/>
<div class="container">
    <c:choose>
        <c:when test="${cart == null || cart.isEmpty()}">
            <br>
            <h1 class="center-align"><fmt:message key="emptyCart"/></h1><br>
            <h1 class="center-align">
                <a href="/user">
                    <fmt:message key="find"/>
                </a>
                <fmt:message key="suitMagazine"/>
            </h1>
        </c:when>
        <c:otherwise>
            <div class="panel panel-default">
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th><fmt:message key="magazine"/></th>
                                <th><fmt:message key="price"/>, $</th>
                                <th><fmt:message key="issues"/></th>
                                <th><fmt:message key="total"/>, $</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="entry" items="${cart.entrySet()}">
                                <tr>
                                    <td><a href="/user/edition?id=${entry.key.id}">${entry.key.name}</a></td>
                                    <td>${entry.key.price}</td>
                                    <td>${entry.value}</td>
                                    <td>${Math.rint(entry.key.price * entry.value * 100)/100}</td>
                                    <c:set var="totalSum" value="${totalSum + Math.rint(entry.key.price * entry.value * 100)/100}"/>
                                    <td>
                                        <form name="removeFromCart" method="post" action="/user/cart">
                                            <input type="hidden" name="_method" value="delete">
                                            <input type="hidden" name="editionId" value="${entry.key.id}">
                                            <button type="submit" class="btn btn-danger">
                                                <fmt:message key="removeFromCart"/>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    <div class="container-fluid">
                        <div class="margin-top center-align">
                            <form name="checkOut" method="post" action="/user/cart">
                                <h4><strong><fmt:message key="toPay"/>: $${totalSum} </strong></h4>
                                <input type="hidden" name="command" value="checkOut">
                                <button type="submit" class="btn btn-success">
                                    <fmt:message key="checkOut"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
