<!DOCTYPE html>
<html>

<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="resetPassword" method="post">
        <fieldset>
            <p>Enter the token you received and your new password</p>
            <input type="text" name="token" placeholder="your token" required autofocus> <br />
            <input type="password" name="password" placeholder="your new password" required autofocus> <br />

            <input type="submit" name="resetPassword" value="reset your password">

        </fieldset>
    </form>
</div>

</body>

</html>