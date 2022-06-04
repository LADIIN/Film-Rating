<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Rubik:400,700'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
</head>
<body>
<%@include file="header.jsp" %>

<div class="login-form">
    <form id="register" method="GET" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="register_page"/>
    </form>
    <form id="login" method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="login"/>
        <h1><fmt:message key="welcome.message" bundle="${content}"/></h1>
        <div class="content">
            <div class="input-field">
                <fmt:message key="login" bundle="${content}" var="login"/>
                <label>
                    <input name="login" type="login" placeholder="${login}" for="login" autocomplete="nope">
                </label>
            </div>
            <div class="input-field">
                <fmt:message key="password" bundle="${content}" var="password"/>
                <input name="password" type="password" placeholder="${password}" for="password"
                       autocomplete="new-password">
            </div>
            <c:if test="${errorMessage != null}">
                <div class="error-message">
                    <fmt:message key="${errorMessage}" bundle="${content}"/>
                </div>
            </c:if>
        </div>
        <div class="action">
            <button type="submit" form="register"><fmt:message key="sign.up" bundle="${content}"/></button>
            <button type="submit" form="login"><fmt:message key="sign.in" bundle="${content}"/></button>
        </div>
    </form>
</div>
</body>
</html>
