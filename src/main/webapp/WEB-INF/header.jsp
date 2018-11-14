<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">

    <nav>
        <a href="profile">Profile</a>
        <c:if test="${sessionScope.userSession.permissionLevel == 0}">
            <a href="app">My Apps</a>
            <a href="createApp">Create App</a>
        </c:if>
        <a href="logout">Logout</a>
    </nav>

</head>

<body>
</body>

</html>