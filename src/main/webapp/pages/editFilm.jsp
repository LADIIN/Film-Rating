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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/mouseOnlyNumberInput.js"></script>

    <script>
        function showName() {
            var name = document.getElementById('fileInput');
            document.getElementById('input-label').innerText = name.files.item(0).name;
        }
    </script>

    <script>
        window.onload = function () {
            let select = document.querySelector('#type');
            select.value = "${film.getType().toString()}";
            select = document.querySelector('#country');
            select.value = "${film.getCountry().getName()}";
            select = document.querySelector('#genre');
            select.value = "${film.getGenre().getName()}";
        }
    </script>
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <title>Edit Film</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="film-form">
    <form id="film-form" method="post" action="${pageContext.request.contextPath}/upload_controller"
          enctype="multipart/form-data">
        <input type="hidden" name="command" value="edit_film"/>
        <input type="hidden" name="id" value="${film.getId()}"/>
        <h1><fmt:message key="edit.film.header" bundle="${content}"/></h1>
        <div class="content">
            <div class="input-field">
                <input class="text-input" name="title" type="text" maxlength="128"
                       placeholder="<fmt:message key="film.title" bundle="${content}"/>" value="${film.getTitle()}"/>
            </div>
            <div class="input-field">
                <ul class="selects-list">
                    <li>
                        <span><fmt:message key="film.type" bundle="${content}"/>:</span>
                        <select id="type" name="type">
                            <option value="Movie">Movie</option>
                            <option value="Series">Series</option>
                            <option value="Cartoon">Cartoon</option>
                        </select>
                    </li>
                    <li>
                        <span><fmt:message key="film.genre" bundle="${content}"/>:</span>
                        <select id="genre" name="genre">
                            <c:forEach var="genre" items="${genres}" varStatus="loop">
                                <option value="${genre.getName()}">${genre.getName()}</option>
                            </c:forEach>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="input-field">
                <input class="text-input" name="director" type="text" maxlength="128"
                       placeholder="<fmt:message key="film.director" bundle="${content}"/>"
                       value="${film.getDirector()}"/>
            </div>
            <div class="input-field">
                <ul class="selects-list">
                    <li>
                        <span><fmt:message key="film.country" bundle="${content}"/>:</span>
                        <select id="country" name="country">
                            <c:forEach var="country" items="${countries}" varStatus="loop">
                                <option value="${country.getName()}">${country.getName()}</option>
                            </c:forEach>
                        </select>
                    </li>
                    <li style="max-width: 40%">
                        <span><fmt:message key="film.year" bundle="${content}"/>:</span>
                        <input id="year-input" class="year-input" name="year" type="number" min="1895"
                               max="${currentYear}"
                               step="1"
                               value="${film.getYear()}"/>
                    </li>
                </ul>
            </div>
            <div class="upload-image-field">
                <input type="file" id="fileInput" name="image" class="input-file" accept=".jpg, .jpeg, .png"
                       onchange="showName()">
                <label id="input-label" for="fileInput"><i class="fa-regular fa-image"></i>
                    <fmt:message key="edit.film.change.poster" bundle="${content}"/>
                </label>
            </div>
            <c:if test="${error != null}">
                <div class="error-message">${error}</div>
            </c:if>
        </div>
    </form>

    <div class="action">
        <button type="submit" form="film-form"><fmt:message key="edit.film.save" bundle="${content}"/></button>
    </div>
</div>

</body>
</html>