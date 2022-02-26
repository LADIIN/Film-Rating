<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
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
    <div class="category"><fmt:message key="section.films" bundle="${content}"/></div>
    <div class="movies-grid">
        <c:forEach var="movie" items="${movies}" varStatus="loop">
            <a class="card" href="#">
                <img src="data:image/jpg;base64,${movie.getPosterPath()}" alt="Avatar" style="width:100%">
                <div class="card-title">${movie.getTitle()}</div>
                <span class="genre">${movie.getGenre().toString()}</span>
                <span class="year">${movie.getYear().toString()}</span>
            </a>
        </c:forEach>

    </div>
    <div class="category"><fmt:message key="section.series" bundle="${content}"/></div>
    <div class="movies-grid">
        <c:forEach var="series" items="${series}" varStatus="loop">
            <a class="card" href="#">
                <img src="data:image/jpg;base64,${series.getPosterPath()}" alt="Avatar" style="width:100%">
                <div class="card-title">${series.getTitle()}</div>
                <span class="genre">${series.getGenre().toString()}</span>
                <span class="year">${series.getYear().toString()}</span>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>