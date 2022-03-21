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

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/assets/js/setMessageTimeout.js"></script>

    <title>Movie page</title>
</head>

<body>
<jsp:include page="header.jsp"/>

<div class="page">
    <div class="film-introduction">
        <img src="data:image/jpg;base64,${film.getPosterImage()}" alt="Poster">

        <div class="film-information">
            <span class="film-title">${film.getTitle()}</span>
            <table>
                <tr>
                    <td class="info-line">
                        <fmt:message key="film.rating" bundle="${content}"/>:
                    </td>
                    <td class="data-line">${film.getRating()}</td>
                </tr>
                <tr>
                    <td class="info-line">
                        <fmt:message key="film.genre" bundle="${content}"/>:
                    </td>
                    <td class="data-line">${film.getGenre().getName()}</td>
                </tr>
                <tr>
                    <td class="info-line">
                        <fmt:message key="film.year" bundle="${content}"/>:
                    </td>
                    <td class="data-line">${film.getYear()}</td>
                </tr>
                <tr>
                    <td class="info-line">
                        <fmt:message key="film.director" bundle="${content}"/>:
                    </td>
                    <td class="data-line">${film.getDirector()}</td>
                </tr>
                <tr>
                    <td class="info-line">
                        <fmt:message key="film.country" bundle="${content}"/>:
                    </td>
                    <td class="data-line">${film.getCountry().getName()}</td>
                </tr>
            </table>
        </div>
    </div>

    <div class="description"><fmt:message key="review.message" bundle="${content}"/></div>

    <form method="get" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="add_review"/>
        <input type="hidden" name="filmId" value="${film.getId()}">
        <div class="review-area">
            <div id="full-stars-example-two">
                <div class="rating-group">
                    <input checked class="rating-input rating__input--none" name="rate" id="rating3-none"
                           value="0" type="radio">
                    <label aria-label="2 stars" class="rating-label" for="rating3-2"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-2" value="2" type="radio">
                    <label aria-label="3 stars" class="rating-label" for="rating3-3"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-3" value="3" type="radio">
                    <label aria-label="4 stars" class="rating-label" for="rating3-4"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-4" value="4" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-5"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-5" value="5" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-6"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-6" value="6" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-7"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-7" value="7" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-8"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-8" value="8" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-9"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-9" value="9" type="radio">
                    <label aria-label="5 stars" class="rating-label" for="rating3-10"><i
                            class="rating__icon rating__icon--star fa-solid fa-star"></i></label>
                    <input class="rating-input" name="rate" id="rating3-10" value="10" type="radio">
                </div>
            </div>

            <fmt:message key="review.placeholder" bundle="${content}" var="placeholder"/>


            <textarea class="review-textarea" name="reviewContent" placeholder="${placeholder}"></textarea>

            <c:if test="${error != null}">
            <div class="error-message"><fmt:message key="${error}" bundle="${content}"/></div>
            </c:if>

            <div style="width: 100%;">
                <button class="submit-button"><fmt:message key="review.submit" bundle="${content}"/></button>
            </div>
    </form>
</div>


<div class="description"><fmt:message key="reviews" bundle="${content}"/>:</div>

<div class="reviews">
    <c:if test="${reviews.isEmpty()}">
        <div class="no-reviews-message"><fmt:message key="reviews.no.reviews" bundle="${content}"/></div>
    </c:if>
    <c:forEach var="review" items="${reviews}">
        <div class="review">
            <div class="avatar"><img src="${pageContext.request.contextPath}/assets/user-profile.svg" alt="user"
                                     width="75"></div>
            <div class="information">
                <h6 class="username"> ${review.getUser().getLogin()}</h6>
                <h6 class="rate">
                    <c:forEach begin="1" end="${review.getRate()}" varStatus="loop">
                        <i class="fa-solid fa-star star"></i>
                    </c:forEach>
                    <c:forEach begin="1" end="${10 - review.getRate()}" varStatus="loop">
                        <i class="fa-solid fa-star"></i>
                    </c:forEach>
                        ${review.getRate()}/10

                </h6>
                <p class="content">${review.getContent()}</p>
                <div class="comment-footer">
                    <c:if test="${user.isAdmin()}">
                        <a class="delete-button"
                           href="${pageContext.request.contextPath}/controller?command=delete_review&id=${review.getId()}">
                            <i class="fa-solid fa-trash-can"></i>
                        </a>
                    </c:if>

                        <%--                    <span class="date">April 14, 2019</span>--%>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${message != null}">
        <div id="message">${message}</div>
    </c:if>

</div>


</body>
</html>