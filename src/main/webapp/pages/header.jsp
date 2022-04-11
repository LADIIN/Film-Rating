<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/mobileHeader.js"></script>
</head>
<body>
<div class="header">
    <div class="container">
        <div class="header-wrapper">
            <a <c:if test="${user != null}">
                href="${pageContext.request.contextPath}/controller?command=main_page&film_type=${film_type}"</c:if>
                    class="title">Moviefy</a>

            <ul class="header-list">
                <c:if test="${user.isAdmin()}">
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=users_page"
                           class="list-item"> <i
                                class="fa-solid fa-users"></i> <fmt:message key="users" bundle="${content}"/></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=films_page&page=1"
                           class="list-item"><i class="fa-solid fa-film"></i> <fmt:message key="films"
                                                                                           bundle="${content}"/></a>
                    </li>
                </c:if>
                <c:if test="${user != null}">
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=rating_page"
                           class="list-item"><i class="fa-solid fa-ranking-star"></i> <fmt:message key="rating"
                                                                                                  bundle="${content}"/></a>
                    </li>
                </c:if>
                <li>
                    <div class="dropdown">
                        <button class="drop-button"><i class='fa fa-language'></i>
                            <i class="fa fa-caret-down"></i>
                        </button>
                        <div class="dropdown-content">
                            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=ru_RU">RU</a>
                            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=by_BY">BY</a>
                            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=en_US">ENG</a>
                        </div>
                    </div>
                </li>
                <c:if test="${user != null}">
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=log_out" class="list-item"><i
                                class="fa fa-fw fa-user"></i><fmt:message key="log.out" bundle="${content}"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<div class="mobile-header">
    <a <c:if test="${user != null}">
        href="${pageContext.request.contextPath}/controller?command=main_page&film_type=${film_type}"</c:if>
            class="logo">Moviefy</a>
    <div id="myLinks">
        <c:if test="${user.isAdmin()}">
            <a href="${pageContext.request.contextPath}/controller?command=users_page">
                <i class="fa-solid fa-users"></i> <fmt:message key="users" bundle="${content}"/>
            </a>
            <a href="${pageContext.request.contextPath}/controller?command=films_page&page=1">
                <i class="fa-solid fa-film"></i> <fmt:message key="films" bundle="${content}"/>
            </a>
        </c:if>
        <button id="dropdown-button" class="dropdown-button" onclick="showContent()">
            <i class='fa fa-language'></i> <i class="fa fa-caret-down"></i>
        </button>
        <div id="dropdown-content" class="dropdown-container">
            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=ru_RU">RU</a>
            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=by_BY">BY</a>
            <a href="${pageContext.request.contextPath}/controller?command=locale&newLocale=en_US">ENG</a>
        </div>
        <c:if test="${user != null}">
            <a href="${pageContext.request.contextPath}/controller?command=log_out"><i
                    class="fa fa-fw fa-user"></i><fmt:message key="log.out" bundle="${content}"/>
            </a>
        </c:if>
    </div>
    <a href="javascript:void(0);" class="icon" onclick="showMenu()">
        <i class="fa fa-bars"></i>
    </a>
</div>


</body>
</html>
