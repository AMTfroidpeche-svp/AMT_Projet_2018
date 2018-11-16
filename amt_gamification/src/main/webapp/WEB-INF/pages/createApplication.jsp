<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/header.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <title>Create App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

<div class="main">

    <% if(request.getAttribute("error") != null) {%>
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <%=request.getAttribute("error")%>
    </div>
    <%}%>

    <fieldset class="fieldset">
        <form action="createApp" id="createApp" method="post">
            <h1>Add a new Application</h1>
            <br/>
            <label >Application Name:</label> <br/>
            <input class="loginInput" type="text" name="appName" id="appNameField" placeholder="app Name" required autofocus/> <br/><br/><br/>
            <label>Description:</label> <br/>
            <textarea class="loginInput" name="appDescr" id="appDescrField" cols="50" rows="10"></textarea> <br/><br/>
            <input class="loginButon" type="submit" name="createAppButton" id="submitCreateApp" value="Add">
        </form>
    </fieldset>
</div>

</body>

</html>