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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
<div class="header">
    <div class="container">
        <div class="header-wrapper">
            <a href="" class="title">Movify</a>
            <div class="dropdown">
                <button class="dropbtn"><i class='fa fa-language'></i>
                    <i class="fa fa-caret-down"></i>
                </button>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=ru_RU">RU</a>
                    <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=by_BY">BY</a>
                    <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=en_US">ENG</a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="login-form">
    <form method="post" action="${pageContext.request.contextPath}/controller">
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
        </div>
        <div class="action">
            <button type="submit"><fmt:message key="sign.in" bundle="${content}"/></button>
        </div>
        <c:if test="${errorMessage != null}">
            <div class="error-message">
                <fmt:message key="${errorMessage}" bundle="${content}"/>
            </div>
        </c:if>

    </form>
</div>
</body>
</html>
