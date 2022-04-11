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
<%@include file="header.jsp" %>

<div class="page">
    <div class="films-navigation">
        <div class="search-result"><fmt:message key="search.result" bundle="${content}"/></div>
        <form method="get" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="search_film"/>
            <div class="search">
                <button type="submit" class="search-button"><i class="fa fa-search"></i></button>
                <input class="search-input" type="text" name="query"
                       placeholder="<fmt:message key="admin.search" bundle="${content}"/>" value="${query}">
            </div>
        </form>
    </div>

    <c:choose>
        <c:when test="${error != null}">
            <h1 class="section-tittle"><fmt:message key="${error}" bundle="${content}"/></h1>
        </c:when>
        <c:when test="${films.isEmpty()}">
            <h1 class="section-tittle"><fmt:message key="search.nothing.found" bundle="${content}"/></h1>
        </c:when>
        <c:otherwise>
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
        </c:otherwise>
    </c:choose>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
