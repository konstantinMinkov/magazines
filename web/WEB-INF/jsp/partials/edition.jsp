<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 07.07.2016
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">
                    <img src="https://docs.google.com/uc?id=${edition.driveId}&export=download" class="img-responsive">
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-sm-8">
                    <div class="container-fluid">
                        <div class="panel panel-default background-grey">
                            <div class="panel-body">
                                <h1 class="center-align">${edition.name}</h1>
                                <h4><fmt:message key="category"/>: ${category.name}</h4>
                                <h4><fmt:message key="price"/>: $<t id="issue-cost">${edition.price}</t></h4>
                                <h4><fmt:message key="editionsPerMonth.placeholder"/>: ${edition.editionsPerMonth} </h4>
                                <h5>${edition.description}</h5>
                                <h4><fmt:message key="issues"/>: <t id="total-issues">${totalIssues}</t></h4>
                                <h4><fmt:message key="totalCost"/>: $<t id="total-cost">${edition.price}</t></h4> <br>
                                <div class="center-align">
                                    <c:choose>
                                        <c:when test="${cart.containsKey(edition)}">
                                            <form id="removeFromCart" class="margin-top" name="removeFromCart" method="post" action="/user/cart">
                                                <input type="hidden" name="_method" value="delete">
                                                <input type="hidden" name="editionId" value="${edition.id}">
                                            </form>
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                    <button form="removeFromCart" type="submit" class="btn btn-danger float-right"><fmt:message key="removeFromCart"/></button>
                                                </div>
                                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                    <form name="checkout" method="get" action="/user/cart" class="float-left">
                                                        <%--<input type="hidden" name="command" value="cart">--%>
                                                        <button type="submit" class="btn btn-success"><fmt:message key="goToCart"/></button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge float-left">1</span>
                                            <span class="badge float-right">12</span> <br>
                                            <input class="range" name="issues" type="range" min="1" max="12" value="1" oninput="onSliderInput()" id="editions-slider"><br>
                                            <form name="addToCart" method="post" action="/user/cart">
                                                <input type="hidden" name="_method" value="put">
                                                <input type="hidden" name="editionId" value="${edition.id}">
                                                <input type="number" name="issues" value="1" class="form-control" oninput="onIssuesInput()" id="editions-field"><br>
                                                <button type="submit" class="btn btn-primary margin-top">
                                                    <fmt:message key="addToCart"/>
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>

