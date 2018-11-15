<%@include file="/WEB-INF/header.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <title>Create App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

<div class="main">
    <fieldset class="fieldset">
        <form action="createApp" method="post">
            <h1>Add a new Application</h1>
            <br/>
            <label >Application Name:</label> <br/>
            <input class="loginInput" type="text" name="appName" placeholder="app Name" required autofocus/> <br/><br/><br/>
            <label>Description:</label> <br/>
            <textarea class="loginInput" name="appDescr" cols="50" rows="10"></textarea> <br/><br/>
            <input class="loginButon" type="submit" name="createAppButton" value="Add">
        </form>
    </fieldset>
</div>

</body>

</html>