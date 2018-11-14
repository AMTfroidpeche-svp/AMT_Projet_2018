<%@ include file="/WEB-INF/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
        <title>Admin Panel</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>

    <body>
        <h1>Admin Panel</h1>

    <fieldset>
        <legend>Look for users</legend>
        <input type="text" name="findEmailText" placeholder="user email" />
        <input type="button" name="findEmail" value="Search" />
    </fieldset>

    </body>

</html>