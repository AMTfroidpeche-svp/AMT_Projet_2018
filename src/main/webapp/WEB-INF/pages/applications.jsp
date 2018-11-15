<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/header.jsp" %>
<!DOCTYPE html>

<html>

<head>
    <title>My Apps</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/applications.css">

</head>

<body>


<div class="flex-container">
    <h1>Applications: </h1>
    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
    <c:forEach items="${apps}" var="i">



            <div class="app-container">
                <div class="app-name">
                        ${i.appName}
                </div>

                <div class="app-content">
                    <div class="app-info">

                        <img src="profile.jpg" width="50px" height="50px"/> <br/>
                        <form action="app" method="post">
                            <input type="submit" name="edit_${i.API_TOKEN}" value="Edit"> <br/>

                            <input type="submit" name="delete_${i.API_TOKEN}" value="Delete">
                        </form>
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
    <a href='app?page=${page-1}'>Previous Page </a>
</c:if>
<c:if test="${fn:length(apps) gt appsPerPage}">
    <a href='app?page=${page+1}'> Next Page</a>
</c:if>
</div>

</body>

</html>