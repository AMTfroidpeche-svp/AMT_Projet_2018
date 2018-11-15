<%@include file="/WEB-INF/header.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <title>Create App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

<div class="main">
    <fieldset>
        <form action="createApp" id="createApp" method="post">
            <legend>Add a new Application</legend>
            <br/>
            <label>Application Name:</label> <br/>
            <input type="text" name="appName" id="appNameField" placeholder="app Name" required autofocus/> <br/><br/><br/>
            <label>Description:</label> <br/>
            <textarea name="appDescr" id="appDescrField" cols="50" rows="10"></textarea> <br/><br/>
            <input type="submit" name="createAppButton" id="submitCreateApp" value="Add">
        </form>
    </fieldset>
</div>

</body>

</html>