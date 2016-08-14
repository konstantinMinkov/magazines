<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 09.07.2016
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">
            <img src="https://docs.google.com/uc?id=${edition.driveId}&export=download"
                 class="img-responsive">
        </div>
        <div class="col-xs-12 col-sm-6 col-md-6 col-sm-8">
            <div class="container-fluid">
                <div class="panel panel-default background-grey">
                    <div class="panel-body">
                        <h1 class="center-align">${edition.name}</h1>
                        <h4><fmt:message key="category"/>: ${category.name}</h4>
                        <h4><fmt:message key="price"/>: $${edition.price}</h4>
                        <h4><fmt:message key="editionsPerMonth.placeholder"/>: ${edition.editionsPerMonth} </h4>
                        <h5>${edition.description}</h5>
                        <div class="center-align">
                            <form name="modifyEdition" method="get" action="/admin/modifyEdition">
                                <%--<input type="hidden" name="command" value="modifyEdition">--%>
                                <input type="hidden" name="id" value="${edition.id}">
                                <button type="submit" class="btn btn-primary margin-top">
                                    <fmt:message key="modify"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
