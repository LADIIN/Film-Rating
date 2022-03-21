<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Movify</title>
</head>

<body>
<div style="overflow-x:scroll;">

    <%@include file="header.jsp" %>

    <div class="page">
        <div class="films-navigation">
            <div class="categories-navigation">
                <a href="${pageContext.request.contextPath}/controller?command=main_page&film_type=Movie" style="
                <c:if test="${film_type.equals('Movie')}">border-bottom: 3px solid #F73A4C;font-weight: 500;</c:if>">
                    <fmt:message key="section.films" bundle="${content}"/>
                </a>
                <a href="${pageContext.request.contextPath}/controller?command=main_page&film_type=Series"
                   style=" <c:if
                           test="${film_type.equals('Series')}">border-bottom: 3px solid #F73A4C;font-weight: 500;</c:if>">
                    <fmt:message key="section.series" bundle="${content}"/>
                </a>
            </div>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="search_film"/>
                <div class="search">
                    <button type="submit" class="search-button"><i class="fa fa-search"></i></button>
                    <input class="search-input" type="text" name="query"
                           placeholder="<fmt:message key="admin.search" bundle="${content}"/>" value="${search_query}">
                </div>
            </form>
        </div>


        <div class="movies-grid">
            <c:forEach var="film" items="${films}" varStatus="loop">
                <a class="card"
                   href="${pageContext.request.contextPath}/controller?command=film_page&id=${film.getId()}">
                    <img src="data:image/jpg;base64,${film.getPosterImage()}" alt="Avatar" style="width:100%">
                    <div class="card-title">${film.getTitle()}</div>
                    <span class="genre">${film.getGenre().getName()}</span>
                    <span class="year">${film.getYear().toString()}</span>
                </a>
            </c:forEach>

        </div>

        <div class="pagination-wrapper">
            <div class="pagination">
                <c:if test="${page > 1}">
                    <a href="${pageContext.request.contextPath}/controller?command=films_page&page=${page - 1}">&laquo;</a>
                </c:if>
                <c:if test="${pages > 1}">
                    <c:forEach var="pageNumber" begin="1" end="${pages}">
                        <c:choose>
                            <c:when test="${pageNumber eq page}">
                                <a class="active">${pageNumber}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/controller?command=main_page&film_type=${film_type.toString()}&page=${pageNumber}">${pageNumber}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <c:if test="${page < pages}">
                    <a href="${pageContext.request.contextPath}/controller?command=films_page&film_type=${film_type.toString()}&page=${page + 1}">&raquo;</a>
                </c:if>
            </div>
        </div>

    </div>
</div>
</body>
</html>