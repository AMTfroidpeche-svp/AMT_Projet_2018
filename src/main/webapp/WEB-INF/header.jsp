<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">

    <nav>
        <a href="profile">Profile</a>
        <c:if test="${sessionScope.userSession.permissionLevel == 0}"><a href="app">Applications</a></c:if>
        <a href="logout">Log out</a>
    </nav>

</head>

<body>
</body>

</html>