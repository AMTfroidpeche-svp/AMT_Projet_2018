<!DOCTYPE html>
<html>

<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="resetPassword" id="resetPassword" method="post">
        <fieldset>
            <p>Enter the token you received and your new password</p>
            <input type="text" name="token" id="tokenField" placeholder="your token" required autofocus> <br />
            <input type="password" name="password" id="passwordField" placeholder="your new password" required autofocus> <br />

            <input type="submit" name="resetPassword" id="resetPasswordSubmit" value="reset your password">

        </fieldset>
    </form>
</div>

</body>

</html>