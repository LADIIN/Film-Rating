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


    <script>
        function showFunctions(id) {
            document.getElementById(id).classList.toggle("show");
        }

        // Close the dropdown menu if the user clicks outside of it
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

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/mouseOnlyNumberInput.js"></script>


    <title>Users</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="page">
    <h1 class="section-tittle"><fmt:message key="search.result" bundle="${content}"/></h1>


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

    <c:choose>
        <c:when test="${error != null}">
            <h1 class="section-tittle"><fmt:message key="${error}" bundle="${content}"/></h1>
        </c:when>
        <c:when test="${users.isEmpty()}">
            <h1 class="section-tittle"><fmt:message key="search.nothing.found" bundle="${content}"/></h1>
        </c:when>
        <c:otherwise>
            <div>
                <table id="MyTable">
                    <thead>
                    <tr class="head">
                        <th scope="col" style="width:5%;">№</th>
                        <th scope="col" style="width:40%;"><fmt:message key="user.name" bundle="${content}"/></th>
                        <th scope="col" class="column" style="width:15%"><fmt:message key="user.role"
                                                                                      bundle="${content}"/></th>
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
                            <td data-label="<fmt:message key="user.block.status" bundle="${content}"/>" class="column">
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
                                        <a onclick="return confirm('<fmt:message key="confirm.message"
                                                                                 bundle="${content}"/>')"
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
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<%@include file="footer.jsp" %>
</body>
</html>