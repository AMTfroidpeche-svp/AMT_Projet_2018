<%@ include file="/WEB-INF/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/adminPanel.css">
</head>

<body>
<h1>Admin Panel</h1>
<fieldset>
    <form action="adminPanel" method="post">
        <legend>Look for users</legend>
        <div class="userList">
            <div>
                <input type="text" name="findEmailText" placeholder="user email"/>
                <input type="submit" name="findUser" value="Search"/>
            </div>

            <c:if test="${fn:length(users) gt 0}">
                <table>
                    <tr>
                        <th>Email</th>
                        <th>FirstName</th>
                        <th>LastName</th>
                        <th>Permission Level</th>
                    </tr>


                    <c:forEach items="${users}" var="i">
                        <tr>
                            <td>${i.email}</td>
                            <td>${i.firstName}</td>
                            <td>${i.lastName}</td>
                            <td id="permissionLevelTD">${i.permissionLevel}</td>
                        </tr>
                    </c:forEach>
                </table>
                <div>
                    <!-- Previous/Next page buttons -->
                    <fmt:parseNumber var="page" type="number" value="${param['page']}"/>
                    <c:if test="${page ne 1}">
                        <a href='adminPanel?page=${page-1}'>Previous Page</a>
                    </c:if>
                    <c:if test="${fn:length(users) gt usersPerPage}">
                        <a href='adminPanel?page=${page+1}'>Next Page</a>
                    </c:if>
                </div>
            </c:if>

            <p>Permission Level: 0 = user, 1 = admin</p>
        </div>
    </form>
</fieldset>
</body>

</html>