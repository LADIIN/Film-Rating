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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <title>Users</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="page">
    <div class="section-tittle"><fmt:message key="rating.title" bundle="${content}"/></div>
    <c:forEach var="film" items="${films}" varStatus="loop">
        <a class="film-card" href="${pageContext.request.contextPath}/controller?command=film_page&id=${film.getId()}">
            <div class="rating-number">${loop.index + 1}</div>
            <img src="data:image/jpg;base64,${film.getPosterImage()}" alt="Poster">
            <div>
                <div class="information">
                    <div class="film-title">${film.getTitle()}</div>
                    <div class="rate">
                        <c:forEach begin="1" end="${film.getRating()}" varStatus="loop">
                            <i class="fa-solid fa-star star"></i>
                        </c:forEach>
                        <c:forEach begin="1" end="${10 - film.getRating()}" varStatus="loop">
                            <i class="fa-solid fa-star"></i>
                        </c:forEach>
                    </div>
                    <div class="line">
                        <span class="line-name"><fmt:message key="film.genre" bundle="${content}"/>:</span>
                            ${film.getGenre().getName()}
                    </div>
                    <div class="line">
                        <span class="line-name"> <fmt:message key="film.year" bundle="${content}"/>:</span>
                            ${film.getYear()}
                    </div>
                    <div class="line">
                        <span class="line-name"> <fmt:message key="film.director" bundle="${content}"/>:</span>
                            ${film.getDirector()}
                    </div>
                    <div class="line">
                        <span class="line-name"> <fmt:message key="film.country" bundle="${content}"/>:</span>
                            ${film.getCountry().getName()}
                    </div>

                </div>
            </div>
        </a>
    </c:forEach>
</div>
<%@include file="footer.jsp" %>
</body>
</html>