<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/header.jsp" %>
<!DOCTYPE html>

<html>

<head>
    <title>My Apps</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/adminPanel.css">
    <link rel="stylesheet" type="text/css" href="css/applications.css">

</head>

<body>


<div class="flex-container">

    <h1>Profile </h1>

    <div class="profile-container">
        <div class="user-info">
            <div id="img-profile">
                <img src="profile.jpg" alt="profile image" width="200px" height="200px" style="float:left;">
            </div>
            <div>
                <h2 id="fullName">Full name: ${sessionScope.userSession.firstName} ${sessionScope.userSession.lastName}</h2>
                <h2 id="email">Email: ${sessionScope.userSession.email}</h2>
            </div>
            <div>
                <button class="adminResetButton">Reset Password</button> <br />
                <p>Actif? wesh</p>
                <label class="switch">
                    <input type="checkbox">
                    <span class="slider round"></span>
                </label>
            </div>
        </div>
    </div>

    <h1>Applications </h1>
    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
    <c:forEach items="${apps}" var="i">


        <div class="app-container" id="appContainer">
            <div class="app-name">
                    ${i.appName}
            </div>
            <div class="app-content">
                <div class="app-info">

                    <img src="profile.jpg" width="50px" height="50px"/> <br/>

                </div>
                <div class="app-descr">
                    <div class="app-descr-text">
                            ${i.description}
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

</div>

<div class="footer">
    <!-- Previous/Next page buttons -->
    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
    <c:if test="${page ne 1}">
        <a href='app?page=${page-1}' id="linkPreviousPage">Previous Page </a>
    </c:if>
    <c:if test="${fn:length(apps) gt appsPerPage}">
        <a href='app?page=${page+1}' id="linkNextPage"> Next Page</a>
    </c:if>
</div>

</body>

</html>