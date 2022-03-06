<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <title>Users</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="page">
    <h1 class="section-tittle"><fmt:message key="users.information" bundle="${content}"/></h1>

    <table id="MyTable">
        <tr class="header">
            <th style="width:5%;">№</th>
            <th style="width:40%;"><fmt:message key="user.name" bundle="${content}"/></th>
            <th class="column" style="width:15%"><fmt:message key="user.role" bundle="${content}"/></th>
            <th class="column" style="width:20%;"><fmt:message key="user.status" bundle="${content}"/></th>
            <th class="column" style="width:20%"><fmt:message key="user.block" bundle="${content}"/></th>
        </tr>
        <c:forEach var="user" items="${users}" varStatus="loop">
            <tr class="row">
                <td class="column">${loop.index + 1 + (page - 1) * usersOnPage}</td>
                <td class="column-left">${user.getLogin()}</td>
                <td class="column">
                    <c:choose>
                        <c:when test="${user.isAdmin()}">
                            <fmt:message key="user.role.admin" bundle="${content}"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="user.role.user" bundle="${content}"/>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td class="column">${user.getStatus()}</td>
                <c:choose>
                    <c:when test="${user.isBlocked()}">
                        <td class="column"><a
                                href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.getId()}"
                                onclick="return confirm('Are you sure?')"
                                class="block-link"><i class="fa-solid fa-lock"></i></a></td>
                    </c:when>
                    <c:otherwise>
                        <td class="column"><a
                                href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.getId()}"
                                onclick="return confirm('Are you sure?')"
                                class="unblock-link"><i class="fa-solid fa-unlock"></i></a></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>

    <div class="pagination-wrapper">
        <div class="pagination">
            <c:if test="${page > 1}">
                <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${page - 1}">&laquo;</a>
            </c:if>
            <c:if test="${pages > 1}">
                <c:forEach var="pageNumber" begin="1" end="${pages}">
                    <c:choose>
                        <c:when test="${pageNumber eq page}">
                            <a class="active">${pageNumber}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${pageNumber}">${pageNumber}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            <c:if test="${page < pages}">
                <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${page + 1}">&raquo;</a>
            </c:if>
        </div>
    </div>

</div>

</body>
</html>