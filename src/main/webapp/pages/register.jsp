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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <title>Moviefy</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/confirmPassword.js"></script>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="register-form">
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="register"/>
        <h1><fmt:message key="sign.up" bundle="${content}"/></h1>
        <div class="content">
            <div class="input-field">
                <label>
                    <fmt:message key="login" bundle="${content}" var="login"/>
                    <input name="login" type="login" placeholder="${login}" autocomplete="nope"
                           pattern="^[a-zA-Z][a-zA-Z0-9-_]{3,16}$" required/>
                </label>
            </div>
            <div class="input-field">
                <label>
                    <fmt:message key="password" bundle="${content}" var="password"/>
                    <input id="password" name="password" type="password" placeholder="${password}"
                           autocomplete="new-password" pattern="^(?=.*\d)(?=.*\p{Lower})[\d\p{Alpha}]{8,16}$" required/>
                </label>
            </div>

            <div class="input-field">
                <label>
                    <fmt:message key="password.confirm" bundle="${content}" var="ConfirmPassword"/>
                    <input id="confirmPassword" name="password" type="password" placeholder="${ConfirmPassword}"
                           autocomplete="new-password"
                           pattern="^(?=.*\d)(?=.*\p{Lower})[\d\p{Alpha}]{8,16}$" required/>
                </label>
            </div>
            <div class="input-field">
                <fmt:message key="email" bundle="${content}" var="email"/>
                <input name="email" type="email" placeholder="${email}" autocomplete="nope"
                       pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" required/>
            </div>
            <c:if test="${registerError != null}">
                <div class="error-message"><fmt:message key="${registerError}" bundle="${content}"/></div>
            </c:if>
            <div id="confirm-error" class="error-message" style="visibility: hidden">
                <fmt:message key="passwords.mismatch" bundle="${content}"/>.
            </div>
        </div>

        <div class="action">
            <button type="submit" onclick="return validate()"><fmt:message key="sign.up" bundle="${content}"/></button>
        </div>
    </form>
</div>

</body>
</html>