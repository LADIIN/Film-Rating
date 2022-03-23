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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">

    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/mouseOnlyNumberInput.js"></script>
    <script>
        function showFunctions(id) {
            document.getElementById(id).classList.toggle("show");
        }

        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {
                var dropdowns = document.getElementsByClassName("dropdown-functions");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>


    <title>Users</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="page">
    <h1 class="section-tittle"><fmt:message key="users.information" bundle="${content}"/></h1>
    <div class=" navigation">
        <form method="get" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="search_user"/>
            <ul>
                <li class="search">
                    <button type="submit" class="search-button"><i class="fa fa-search"></i></button>
                    <input class="search-input" type="text" name="query"
                           placeholder="<fmt:message key="admin.search" bundle="${content}"/>" value="${search_query}">
                </li>
            </ul>
        </form>
    </div>

    <div>
        <table id="MyTable">
            <thead>
            <tr class="header">
                <th scope="col" style="width:5%;">№</th>
                <th scope="col" style="width:40%;"><fmt:message key="user.name" bundle="${content}"/></th>
                <th scope="col" class="column" style="width:15%"><fmt:message key="user.role" bundle="${content}"/></th>
                <th scope="col" class="column" style="width:10%;"><fmt:message key="user.status"
                                                                               bundle="${content}"/></th>
                <th scope="col" class="column" style="width:30%"><fmt:message key="user.block.status"
                                                                              bundle="${content}"/></th>
                <th scope="col" class="column" style="width:5%"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="loop">
                <tr class="row">
                    <td data-label="№" class="column">${loop.index + 1 + (page - 1) * usersOnPage}</td>
                    <td data-label="<fmt:message key="user.name" bundle="${content}"/>"
                        class="column-left">${user.getLogin()}</td>
                    <td data-label="<fmt:message key="user.role" bundle="${content}"/>" class="column">
                        <c:choose>
                            <c:when test="${user.isAdmin()}">
                                <fmt:message key="user.role.admin" bundle="${content}"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.role.user" bundle="${content}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td data-label="<fmt:message key="user.status" bundle="${content}"/>" class="column">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="change_status"/>
                            <input type="hidden" name="id" value="${user.getId()}"/>
                            <input id="number-input" class="status-input" name="status" type="number" min="0"
                                   step="1"
                                   value="${user.getStatus()}" pattern="^\d+$" required/>
                        </form>
                    </td>
                    <td data-label="<fmt:message key="user.block.status" bundle="${content}"/>" class="column">
                        <c:choose>
                            <c:when test="${user.isBlocked()}">
                                <i class="block-link fa-solid fa-lock"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="unblock-link fa-solid fa-unlock"></i>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td data-label="other" class="column">
                        <div class="dropdown-container">
                            <button onclick="showFunctions(${loop.index})" class="dropbtn">
                                <i class="fa-solid fa-ellipsis-vertical"></i>
                            </button>
                            <div id="${loop.index}" class="dropdown-functions">
                                <a href="${pageContext.request.contextPath}/controller?command=change_role&id=${user.getId()}">
                                    <c:choose>
                                        <c:when test="${user.isAdmin()}">
                                            <fmt:message key="user.set.role.user" bundle="${content}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="user.set.role.admin" bundle="${content}"/>
                                        </c:otherwise>
                                    </c:choose></a>
                                <a onclick="return confirm('Are you sure?')"
                                   href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.getId()}">
                                    <c:choose>
                                        <c:when test="${user.isBlocked()}">
                                            <fmt:message key="user.unblock" bundle="${content}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="user.block" bundle="${content}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach var="i" begin="1" end="${usersOnPage - users.size()}">
                <tr class="row">
                    <td class="column">${i + (page - 1) * usersOnPage + users.size()}</td>
                    <td class="column-left"></td>
                    <td class="column"></td>
                    <td class="column"></td>
                    <td class="column"></td>
                    <td class="column"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
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