<%@ include file="/WEB-INF/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit an App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <% if(request.getAttribute("error") != null) {%>
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <%=request.getAttribute("error")%>
    </div>
    <%}%>

    <fieldset class="fieldset">
        <h1>Edit an App</h1>
        <form action="editApp" method="post">

            <input class="loginInput" type="text" name="appName" value="${requestScope.appName}" required autofocus> <br />
            <textarea name="appDescr" cols="50" rows="10">${requestScope.appDescr}</textarea> <br />
            <input class="loginButon" type="submit" name="edit" value="Save changes">

        </form>
    </fieldset>
</body>
</html>
