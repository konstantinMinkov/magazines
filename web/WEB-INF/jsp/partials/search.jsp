<%--
  Created by IntelliJ IDEA.
  User: Konstantin Minkov
  Date: 11.07.2016
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container-fluid background-blue-dark">
    <div class="container">
        <h3 class="white-font">
            <fmt:message key="findYours"/> <b>${editionsCount}</b> <fmt:message key="editions"/>
        </h3>
        <form action="${userURI}/search" method="post">
            <%--<input type="hidden" name="command" value="search">--%>
            <div class="row">
                <div class="col-xs-12 col-sm-7 col-md-7 col-lg-7 no-padding">
                    <fmt:message key="search.placeholder" var="searchPlaceholder"/>
                    <input type="text" name="magazine" value="${magazine}" placeholder="${searchPlaceholder}" class="form-control search-input">
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 no-padding">
                    <select name="category" class="form-control search-input">
                        <c:choose>
                            <c:when test="${category != null}">
                                <option disabled><fmt:message key="selectCategory"/></option>
                                <option value="${category.name}" selected>${category.name}</option>
                                <c:forEach items="${categories}" var="categoryNode">
                                    <c:if test="${ !(category eq categoryNode)}">
                                        <option value="${categoryNode.name}">${categoryNode.name}</option>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option value="news" disabled selected><fmt:message key="selectCategory"/></option>
                                <c:forEach items="${categories}" var="categoryNode">
                                    <option value="${categoryNode.name}">${categoryNode.name}</option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-2 col-lg-2">
                    <button type="submit" class="btn btn-danger search-button">
                        <fmt:message key="search"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<br>
<c:choose>
    <c:when test="${ !editions.isEmpty()}">
        <jsp:include page="../includes/editions-list.jsp"/>
    </c:when>
    <c:otherwise>
        <h1 class="center-align">
            <fmt:message key="editions.notFound"/>
        </h1>
    </c:otherwise>
</c:choose>
<nav>
    <ul class="pager">
        <c:if test="${previousPage != null}">
            <li><a href="${userURI}/search?page=${previousPage}">
                <span aria-hidden="true">&larr;</span>
                <fmt:message key="previous"/>
            </a></li>
        </c:if>
        <c:if test="${nextPage != null}">
            <li><a href="${userURI}/search?page=${nextPage}">
                <fmt:message key="next"/>
                <span aria-hidden="true">&rarr;</span>
            </a></li>
        </c:if>
    </ul>
</nav>