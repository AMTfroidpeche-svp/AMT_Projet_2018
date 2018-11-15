<!DOCTYPE html>
<html>

<head>
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">
    <form action="login" method="post">
        <div class="loginFieldset">
            <!--<img src="AMTpeche.bmp" alt="AMTpeche">-->
            <p>Login</p>
            <input class="loginInput" type="email" name="email" placeholder="Email Address" autofocus required><br />
            <input class="loginInput" type="password" name="password" placeholder="Password" required><br />
            <input class="loginButon" type="submit" name="login" value="Login">

            <p>
              <a href="newPassword" name="password_forgotten">Password forgotten?</a>
            </p>
            <p>
                <a href="registration" name="register_account">Create a dev account</a>
            </p>
        </div>
    </form>
  </div>

</body>

</html>