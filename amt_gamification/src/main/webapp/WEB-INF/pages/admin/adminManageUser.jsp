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
    <link rel="stylesheet" type="text/css" href="css/adminManageUser.css">

</head>

<body>


<div class="flex-container">
    <c:if test="${sessionScope.success ne null}">
        <div class="success">
            <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                ${sessionScope.success}
        </div>
    </c:if>
    <h1>Profile </h1>

    <div class="profile-container">
        <div class="user-info">
            <div id="img-profile">
                <img src="${requestScope.user.imageUrl}" alt="profile.jpg" width="150px" height="150px" style="float:left;">
            </div>
            <div>
                <h2 id="fullName">Full
                    name: ${requestScope.user.firstName} ${requestScope.user.lastName}</h2>
                <h2 id="email">Email: ${requestScope.user.email}</h2>
            </div>
            <div class="activity">

                <form action="adminUsersManagement" method="post">
                    <input type="hidden" name="user" value="${requestScope.user.email}">
                    <c:choose>
                        <c:when test="${requestScope.user.isActive eq false}">
                            <input class="adminButton" type="submit" name="changeUserAccountActivity" value="Enable Account">
                        </c:when>
                        <c:otherwise>
                            <input class="adminButton" type="submit" name="changeUserAccountActivity" value="Disable Account">
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
            <div>
                <form action="adminUsersManagement" method="post">
                    <input type="hidden" name="user" value="${requestScope.user.email}">
                    <input type="submit" class="adminButton" name="resetUserPassword" value="Reset user password">
                </form>
                <br/>
            </div>
        </div>
    </div>

    <h1>Applications </h1>
    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
    <c:forEach items="${apps}" var="i" varStatus="loop">
        <c:if test="${!(loop.last and fn:length(apps) gt appsPerPage)}">
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
        </c:if>
    </c:forEach>

</div>

<div class="footer">
    <!-- Previous/Next page buttons -->
    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
    <c:if test="${page ne 1}">
        <a href='adminUsersManagement?user=${requestScope.user.email}&page=${page-1}' id="linkPreviousPage">Previous
            Page </a>
    </c:if>
    <c:if test="${fn:length(apps) gt appsPerPage}">
        <a href='adminUsersManagement?user=${requestScope.user.email}&page=${page+1}' id="linkNextPage"> Next Page</a>
    </c:if>
</div>

</body>

</html>