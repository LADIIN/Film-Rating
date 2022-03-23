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

    <script>
        function showName() {
            var name = document.getElementById('fileInput');
            document.getElementById('input-label').innerText = name.files.item(0).name;
        }
    </script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/mouseOnlyNumberInput.js"></script>
    <script src="https://kit.fontawesome.com/8972068f93.js" crossorigin="anonymous"></script>
    <title>Add film</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="film-form">
    <form id="film-info" method="POST" action="${pageContext.request.contextPath}/upload_controller"
          enctype="multipart/form-data">
        <input type="hidden" name="command" value="add_film"/>
        <h1><fmt:message key="add.film.header" bundle="${content}"/></h1>
        <div class="content">
            <div class="input-field">
                <input class="text-input" name="title" type="text" maxlength="128"
                       placeholder="<fmt:message key="film.title" bundle="${content}"/>">
            </div>
            <div class="input-field">
                <ul class="selects-list">
                    <li>
                        <span><fmt:message key="film.type" bundle="${content}"/>:</span>
                        <select name="type">
                            <option value="Movie">Movie</option>
                            <option value="Series">TV series</option>
                            <option value="Cartoon">Cartoon</option>
                        </select>
                    </li>
                    <li>
                        <span><fmt:message key="film.genre" bundle="${content}"/>:</span>
                        <select name="genre">
                            <c:forEach var="genre" items="${genres}" varStatus="loop">
                                <option value="${genre.getName()}">${genre.getName()}</option>
                            </c:forEach>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="input-field">
                <input class="text-input" name="director" type="text" maxlength="128"
                       placeholder="<fmt:message key="film.director" bundle="${content}"/>"/>
            </div>
            <div class="input-field">
                <ul class="selects-list">
                    <li>
                        <span><fmt:message key="film.country" bundle="${content}"/>:</span>
                        <select name="country">
                            <c:forEach var="country" items="${countries}" varStatus="loop">
                                <option value="${country.getName()}">${country.getName()}</option>
                            </c:forEach>
                            <%--                            <option value="Australia">Australia</option>--%>
                            <%--                            <option value="Austria">Austria</option>--%>
                            <%--                            <option value="Belarus">Belarus</option>--%>
                            <%--                            <option value="Belgium">Belgium</option>--%>
                            <%--                            <option value="Brazil">Brazil</option>--%>
                            <%--                            <option value="Canada">Canada</option>--%>
                            <%--                            <option value="China">China</option>--%>
                            <%--                            <option value="Denmark">Denmark</option>--%>
                            <%--                            <option value="Egypt">Egypt</option>--%>
                            <%--                            <option value="Finland">Finland</option>--%>
                            <%--                            <option value="France">France</option>--%>
                            <%--                            <option value="Germany">Germany</option>--%>
                            <%--                            <option value="Greece">Greece</option>--%>
                            <%--                            <option value="Iceland">Iceland</option>--%>
                            <%--                            <option value="India">India</option>--%>
                            <%--                            <option value="Italy">Italy</option>--%>
                            <%--                            <option value="Japan">Japan</option>--%>
                            <%--                            <option value="Mexico">Mexico</option>--%>
                            <%--                            <option value="Netherlands">Netherlands</option>--%>
                            <%--                            <option value="New Zealand">New Zealand</option>--%>
                            <%--                            <option value="Norway">Norway</option>--%>
                            <%--                            <option value="Poland">Poland</option>--%>
                            <%--                            <option value="Portugal">Portugal</option>--%>
                            <%--                            <option value="Russia">Russia</option>--%>
                            <%--                            <option value="Saudi Arabia">Saudi Arabia</option>--%>
                            <%--                            <option value="Singapore">Singapore</option>--%>
                            <%--                            <option value="Spain">Spain</option>--%>
                            <%--                            <option value="Sweden">Sweden</option>--%>
                            <%--                            <option value="Switzerland">Switzerland</option>--%>
                            <%--                            <option value="Turkey">Turkey</option>--%>
                            <%--                            <option value="Ukraine">Ukraine</option>--%>
                            <%--                            <option value="UK">United Kingdom</option>--%>
                            <%--                            <option value="USA">USA</option>--%>
                        </select>
                    </li>
                    <li style="max-width: 40%">
                        <span><fmt:message key="film.year" bundle="${content}"/>:</span>
                        <input id="number-input" class="year-input" name="year" type="number" min="1895"
                               max="${currentYear}"
                               step="1"
                               value="2022"/>
                    </li>
                </ul>
            </div>
            <div class="upload-image-field">
                <input type="file" id="fileInput" name="image" class="input-file" accept=".jpg, .jpeg, .png"
                       onchange="showName()">
                <label id="input-label" for="fileInput"><i class="fa-regular fa-image"></i>
                    <fmt:message key="add.film.choose.poster" bundle="${content}"/>
                </label>
            </div>
            <c:if test="${error != null}">
                <div class="error-message"><fmt:message key="${error}" bundle="${content}"/></div>
            </c:if>
        </div>
    </form>

    <div class="action">
        <button type="submit" form="film-info"><fmt:message key="add.film.upload" bundle="${content}"/></button>
    </div>
</div>
</body>
</html>