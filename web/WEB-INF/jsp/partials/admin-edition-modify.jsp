
<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 09.07.2016
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <div class="row">
        <div id="imageContainer" class="col-xs-12 col-sm-6 col-md-6 col-lg-4">
            <c:choose>
                <c:when test="${edition != null && edition.driveId != null}">
                    <img id="editionImage"  alt="" src="https://docs.google.com/uc?id=${edition.driveId}&export=download"
                         height="200" class="img-responsive">
                </c:when>
                <c:otherwise>
                    <div id="imgPlaceholder"></div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-6 col-sm-8">
            <div class="container-fluid">
                <div class="panel panel-default background-grey">
                    <div class="panel-body">
                        <div class="center-align">
                            <c:if test="${errors.getError(\"general\") != null}">
                                <div class="alert alert-danger" role="alert">
                                        ${errors.getError("general")}
                                </div>
                            </c:if>
                            <form id="modifyEdition" name="modifyEdition" method="post" action="/admin/modifyEdition" enctype="multipart/form-data">
                                <%--<input type="hidden" name="command" value="modifyEditionAcceptForm">--%>
                                <fmt:message key="edition.name.placeholder" var="editionKeyPlaceholder"/>
                                <input type="text" class="form-control input-form" name="name" value="${edition.name}"
                                       placeholder="${editionKeyPlaceholder}"><br>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <select class="form-control input-form" name="categorySelect">
                                            <option value="news" disabled selected><fmt:message key="selectCategory"/></option>
                                            <c:forEach items="${categories}" var="categoryNode">
                                                <option value="${categoryNode.name}">${categoryNode.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                                        <input name="categoryNew" value="${category.name}" class="form-control input-form" placeholder="Или введите новую">
                                    </div>
                                </div>
                                <br>
                                <fmt:message key="edition.description.placeholder" var="editionDescriptionPlaceholder"/>
                                <textarea maxlength="1024" class="form-control textarea-form" name="description" placeholder="${editionDescriptionPlaceholder}">${edition.description}</textarea> <br>
                                <fmt:message key="editionsPerMonth.placeholder" var="editionsPerMonthPlaceholder"/>
                                <input type="number" class="form-control input-form" name="editionsPerMonth" value="${edition.editionsPerMonth}"
                                        placeholder="${editionsPerMonthPlaceholder}" min="1" max="4"> <br>
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <fmt:message key="pricePlaceholder" var="pricePlaceholder"/>
                                    <input type="number" class="form-control input-form" name="price" value="${edition.price}"
                                           placeholder="${pricePlaceholder}" step="0.01" min="0.01">
                                </div> <br>
                                <input type="file" name="image" class="form-control input-form" accept="image/jpeg image/png"
                                       onchange="loadImage(event)">
                            </form>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <form name="deleteEdition" method="post" action="/admin/modifyEdition" class="float-right">
                                    <input type="hidden" name="_method" value="delete">
                                    <input type="hidden" name="id" value="${edition.id}">
                                    <button type="submit" class="btn btn-danger margin-top">
                                        <fmt:message key="delete"/>
                                    </button>
                                </form>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <button form="modifyEdition" type="submit" class="btn btn-primary margin-top float-left">
                                    <fmt:message key="save"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
