<!DOCTYPE html>
<html>

<head>
    <title>Change Password</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="changePassword" method="post">
        <div class="loginFieldset">
            <p>Please,</p>
            <p>change your password</p>
            <input class="loginInput" type="password" name="oldPassword" placeholder="Old Password" required autofocus> <br />
            <input class="loginInput" type="password" name="password1" placeholder="New Password" required> <br />
            <input class="loginInput" type="password" name="password2" placeholder="Confirm Password" required> <br />

            <input class="loginButon" type="submit" name="changePasswordButton" value="Set new password">

        </div>
    </form>
</div>

</body>

</html>