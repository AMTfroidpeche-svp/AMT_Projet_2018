<!DOCTYPE html>
<html>

<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="questionAuth" id="questionAuth" method="post">
        <fieldset>
            <p>${question}</p>
            <input type="text" name="response" id="responseField" placeholder="response" required autofocus> <br />

            <input type="submit" name="questionAuthButton" id="submitQuestionAuth" value="response">

        </fieldset>
    </form>
</div>

</body>

</html>