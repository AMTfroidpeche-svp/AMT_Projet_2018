<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">

    <nav id="nav">
        <a href="profile" id="profileNav">Profile</a>
        <c:if test="${sessionScope.userSession.permissionLevel == 0}">
            <a href="app?page=1" id="appNav">My Apps</a>
            <a href="createApp" id="createAppNav">Create App</a>
        </c:if>
        <c:if test="${sessionScope.userSession.permissionLevel == 1}">
            <a href="adminPanel" id="adminPanelNav">Manage Users</a>
        </c:if>
        <a href="logout" id="logoutNav">Logout</a>
    </nav>

</head>

<body>
</body>

</html>