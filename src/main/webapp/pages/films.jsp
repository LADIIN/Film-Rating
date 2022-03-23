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
    <script src="${pageContext.request.contextPath}/js/setMessageTimeout.js"></script>

    <script>
        var modal = document.getElementById('confirm-modal');
        window.onclick = function (event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    </script>

    <title>Films</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="page">
    <h1 class="section-tittle"><fmt:message key="films.information" bundle="${content}"/></h1>

    <div class=" navigation">
        <form method="get" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="search_film"/>
            <ul>
                <li class="search">
                    <button type="submit" class="search-button"><i class="fa fa-search"></i></button>
                    <input class="search-input" type="text" name="query"
                           placeholder="<fmt:message key="admin.search" bundle="${content}"/>" value="${search_query}">
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=add_film_page" class="single-button">
                        <i class="fa-solid fa-plus"></i> <fmt:message key="films.add" bundle="${content}"/></a>
                </li>
            </ul>
        </form>
    </div>

    <table id="MyTable">
        <thead>
        <tr class="header">
            <th style="width:5%;">№</th>
            <th style="width:40%;"><fmt:message key="film.title" bundle="${content}"/></th>
            <th class="column" style="width:20%;"><fmt:message key="film.type" bundle="${content}"/></th>
            <th class="column" style="width:25%;"><fmt:message key="film.genre" bundle="${content}"/></th>
            <th class="column" style="width:10%;"><fmt:message key="film.rating" bundle="${content}"/></th>
            <th class="column" style="width:10%"><fmt:message key="film.edit" bundle="${content}"/></th>
            <th class="column" style="width:10%"><fmt:message key="film.Delete" bundle="${content}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="film" items="${films}" varStatus="loop">
            <tr class="row">
                <td data-label="№">${loop.index + 1 + (page - 1) * filmsOnPage}</td>
                <td data-label="<fmt:message key="film.title" bundle="${content}"/>" class="column-left">
                    <a class="film-link"
                       href="${pageContext.request.contextPath}/controller?command=film_page&id=${film.getId()}">
                            ${film.getTitle()}</a></td>
                <td data-label="<fmt:message key="film.type" bundle="${content}"/>"
                    class="column">${film.getType().toString()}</td>
                <td data-label="<fmt:message key="film.genre" bundle="${content}"/>"
                    class="column">${film.getGenre().getName()}</td>
                <td data-label="<fmt:message key="film.rating" bundle="${content}"/>"
                    class="column">${film.getRating()}</td>
                <td data-label="<fmt:message key="film.edit" bundle="${content}"/>" class="column">
                    <a href="${pageContext.request.contextPath}/controller?command=edit_film_page&id=${film.getId()}"
                       class="edit-link"><i class="fa-solid fa-pen-to-square"></i></a>
                <td data-label="<fmt:message key="film.Delete" bundle="${content}"/>" class="column">
                    <a class="block-link"
                       href="${pageContext.request.contextPath}/controller?command=delete_film&id=${film.getId()}"
                       onclick="confirm('Are you sure?')">
                        <i class="fa-solid fa-trash-can"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>

        <c:forEach var="i" begin="1" end="${filmsOnPage - films.size()}">
            <tr class="row">
                <td class="column">${i + (page - 1) * filmsOnPage + films.size()}</td>
                <td class="column-left"></td>
                <td class="column"></td>
                <td class="column"></td>
                <td class="column"></td>
                <td class="column"></td>
                <td class="column"></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <div class="pagination-wrapper">
        <div class="pagination">
            <c:if test="${page > 1}">
                <a href="${pageContext.request.contextPath}/controller?command=films_page&page=${page - 1}">&laquo;</a>
            </c:if>
            <c:forEach var="pageNumber" begin="1" end="${pages}">
                <c:choose>
                    <c:when test="${pageNumber eq page}">
                        <a class="active">${pageNumber}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/controller?command=films_page&page=${pageNumber}">${pageNumber}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${page < pages}">
                <a href="${pageContext.request.contextPath}/controller?command=films_page&page=${page + 1}">&raquo;</a>
            </c:if>
        </div>
    </div>
    <c:if test="${message != null}">
        <div id="message">${message}</div>
    </c:if>

    <div id="confirm-modal" class="modal">
                 <span onclick="document.getElementById('confirm-modal').style.display='none'" class="close"
                       title="Close Modal">&times;</span>
        <form class="modal-content" action=${pageContext.request.contextPath}/controller>
            <input type="hidden" name="command" value="delete_film"/>
            <input type="hidden" name="id" value="${film.getId()}"/>

            <div class="content">
                <h1>Delete Account</h1>
                <p>Are you sure you want to delete your account?</p>
            </div>

            <div class="clearfix">
                <div class="action">
                    <button type="submit">Cancel</button>
                    <button type="submit">Delete</button>
                </div>
            </div>
        </form>
    </div>

</div>

</body>
</html>