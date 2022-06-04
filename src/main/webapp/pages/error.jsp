<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>404</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
</head>
<body>
<div class="error">
    <h1>404</h1>
    <div class="prompt"><fmt:message key="error.not.found" bundle="${content}"/></div>
    <div class="go-back-button">
        <a href="${pageContext.request.contextPath}/controller?command=main_page&film_type=Movie"
           class="go-back-button"><fmt:message key="go.main.page" bundle="${content}"/></a>
    </div>
</div>
</body>
</html>