<!DOCTYPE html>
<html>

<head>
    <title>Reset Password</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="resetPassword" id="resetPassword" method="post">
        <div class="loginFieldset">
            <p>Enter the token you received and your new password</p>
            <input class="loginInput" type="text" name="token" id="tokenField" placeholder="your token" required autofocus> <br />
            <input class="loginInput" type="password" name="password" id="passwordField" placeholder="your new password" required> <br />

            <input class="loginButon" type="submit" name="resetPassword" id="resetPasswordSubmit" value="reset your password">

        </div>
    </form>
</div>

</body>

</html>