<!DOCTYPE html>
<html>

<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="resetPassword" method="post">
        <div class="loginFieldset">
            <p>Enter the token you received and your new password</p>
            <input class="loginInput" type="text" name="token" placeholder="your token" required autofocus> <br />
            <input class="loginInput" type="password" name="password" placeholder="your new password" required autofocus> <br />

            <input class="loginButon" type="submit" name="resetPassword" value="reset your password">

        </div>
    </form>
</div>

</body>

</html>