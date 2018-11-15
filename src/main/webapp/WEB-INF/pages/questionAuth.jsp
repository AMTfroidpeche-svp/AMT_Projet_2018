<!DOCTYPE html>
<html>

<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="questionAuth" method="post">
        <div class="loginFieldset">
            <p>${question}</p>
            <input class="loginInput" type="text" name="response" placeholder="response" required autofocus> <br />

            <input class="loginButon" type="submit" name="questionAuthButton" value="response">

        </div>
    </form>
</div>

</body>

</html>