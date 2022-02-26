<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale.content" var="content"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="header">
    <div class="container">
        <div class="header-wrapper">
            <a href="" class="title">Movify</a>

            <ul class="header-list">
                <li>
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
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=log_out" class="list-item"><i class="fa fa-fw fa-user"></i><fmt:message key="log.out"
                                                                                               bundle="${content}"/></a>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
