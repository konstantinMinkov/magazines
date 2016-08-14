<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 11.07.2016
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
    <div class="row">
        <c:forEach items="${editions}" var="edition">
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
                <a href="${userURI}/edition?id=${edition.id}" class="thumbnail">
                    <img src="https://docs.google.com/uc?id=${edition.driveId}&export=download"
                         class="img-responsive">
                </a>
            </div>
        </c:forEach>
    </div>
</div>
