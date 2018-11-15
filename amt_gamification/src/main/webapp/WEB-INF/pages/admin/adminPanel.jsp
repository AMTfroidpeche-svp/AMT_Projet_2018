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
<fieldset class="fieldset">
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
                        <th>Is Active</th>
                    </tr>

                    <c:forEach items="${users}" var="i" varStatus="loop">
                        <c:if test="${!(loop.last and fn:length(users) gt appsPerPage)}">
                            <tr>
                                <td><a href="adminUsersManagement?user=${i.email}&page=1"/>${i.email}</td>
                                <td>${i.firstName}</td>
                                <td>${i.lastName}</td>
                                <td id="permissionLevelTD">${i.permissionLevel}</td>
                                <td>${i.isActive}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <div class="footer">
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
            <p>Click on a user to manage his profile</p>
            <p>Permission Level: 0 = user, 1 = admin</p>
        </div>
    </form>
</fieldset>
</body>

</html>