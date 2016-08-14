<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 05.07.2016
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="/WEB-INF/taglib.tld" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <c:choose>
        <c:when test="${ !subscriptionEditionMap.isEmpty()}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h1 class="center-align">
                        <fmt:message key="subscriptions"/>
                    </h1>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th><fmt:message key="magazine"/></th>
                            <th><fmt:message key="subscription.dateStarted"/></th>
                            <th><fmt:message key="subscription.dateEnding"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="subscriptionEdition" items="${subscriptionEditionMap.entrySet()}">
                            <tr>
                                <td><a href="/user/edition?id=${subscriptionEdition.value.id}">
                                    ${subscriptionEdition.value.name}
                                </a></td>
                                <td><date:dateFormatter languageTag="${locale}" date="${subscriptionEdition.key.dateCreated}"/></td>
                                <td><date:dateFormatter languageTag="${locale}" date="${subscriptionEdition.key.dateEnding}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h1 class="center-align">
                        <fmt:message key="paymentsHistory"/>
                    </h1>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th><fmt:message key="price"/>, $</th>
                            <th><fmt:message key="paymentDate"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="payment" items="${payments}">
                            <tr>
                                <td>${payment.id}</td>
                                <td>${payment.price}</td>
                                <td><date:dateTimeFormatter languageTag="${locale}" datetime="${payment.timeCreated}"/></td>
                                <%--<td>${payment.timeCreated}</td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="center-align">
                <h1>
                    You have no subscriptions yet.
                </h1>
            </div>
        </c:otherwise>
    </c:choose>
</div>
