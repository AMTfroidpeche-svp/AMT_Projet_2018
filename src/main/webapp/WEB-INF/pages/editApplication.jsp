<%@ include file="/WEB-INF/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit an App</title>
</head>
<body>
    <fieldset>
        <p>Edit an App</p>
        <form action="editApp" method="post">
            <input type="text" name="appName" required autofocus> <br />
            <textarea name="appDescr" cols="50" rows="10"></textarea> <br />
            <input type="submit" name="edit" value="Save changes">
        </form>
    </fieldset>
</body>
</html>