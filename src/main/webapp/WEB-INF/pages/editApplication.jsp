<%@ include file="/WEB-INF/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit an App</title>
</head>
<body>
    <fieldset class="fieldset">
        <h1>Edit an App</h1>
        <form action="editApp" method="post">
            <input type="hidden" name="appToken" value="${param['appToken']}" required>
            <input class="loginInput" type="text" name="appName" value="${requestScope.appName}" required autofocus> <br />
            <textarea name="appDescr" cols="50" rows="10">${requestScope.appDescr}</textarea> <br />
            <input class="loginButon" type="submit" name="edit" value="Save changes">

        </form>
    </fieldset>
</body>
</html>
