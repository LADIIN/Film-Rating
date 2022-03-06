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
    <h1 class="section-tittle"><fmt:message key="films.information" bundle="${content}"/></h1>

    <div class=" navigation">
        <ul>
            <li class="search">
                <a href="#" class="search-button"><i class="fa fa-search"></i></a>
                <input class="search-input" type="text"
                       placeholder="<fmt:message key="admin.search" bundle="${content}"/>">
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=add_film_page" class="single-button"><i
                        class="fa-solid fa-plus"></i> <fmt:message key="films.add"
                                                                   bundle="${content}"/></a>
            </li>
        </ul>
    </div>


    <table id="MyTable">
        <tr class="header">
            <th style="width:5%;">№</th>
            <th style="width:40%;"><fmt:message key="film.title" bundle="${content}"/></th>
            <th class="column" style="width:20%;"><fmt:message key="film.type" bundle="${content}"/></th>
            <th class="column" style="width:25%;"><fmt:message key="film.genre" bundle="${content}"/></th>
            <th class="column" style="width:10%;"><fmt:message key="film.rating" bundle="${content}"/></th>
            <th class="column" style="width:10%"><fmt:message key="film.edit" bundle="${content}"/></th>
            <th class="column" style="width:10%"><fmt:message key="film.Delete" bundle="${content}"/></th>
        </tr>

        <c:forEach var="film" items="${films}" varStatus="loop">
        <a class="row">
            <td class="column">${loop.index + 1 + (page - 1) * filmsOnPage}</td>
            <td class="column-left">
                <a class="film-link"
                   href="${pageContext.request.contextPath}/controller?command=film_page&id=${film.getId()}">
                        ${film.getTitle()}</a></td>
            <td class="column">${film.getType().toString()}</td>
            <td class="column">${film.getGenre().toString()}</td>
            <td class="column">${film.getRating()}</td>
            <td class="column"><a
                    href="${pageContext.request.contextPath}/controller?command=edit_film_page&id=${film.getId()}"
                    class="edit-link"><i class="fa-solid fa-pen-to-square"></i></a>
            <td class="column"><a
                    href="${pageContext.request.contextPath}/controller?command=delete_film&id=${film.getId()}"
                    class="block-link"><i class="fa-solid fa-trash-can"></i></a></td>
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

</div>

</body>
</html>