<!DOCTYPE html>
<html>

<head>
    <title>Password forgotten</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="newPassword" id="passwordForgotten" method="post">
        <div class="loginFieldset">
            <p>Enter your email address</p>
            <input class="loginInput" type="text" name="email" id="emailField" placeholder="toto@example.com" required autofocus> <br />

            <input class="loginButon" type="submit" name="passwordForgottenButton" id="submitPasswordForgotten" value="reset your password"> <br />

            <p><a href="login" class="loginLink" id="backButton">Back</a></p>

        </div>
    </form>
</div>

</body>

</html>