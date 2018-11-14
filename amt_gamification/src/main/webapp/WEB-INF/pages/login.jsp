<!DOCTYPE html>
<html>

<head>
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">
    <form action="login" method="post">
        <fieldset class="loginFieldset">
            <p>Login</p>
            <input type="email" name="email" placeholder="Email Address" autofocus required><br />
            <input type="password" name="password" placeholder="Password" required><br />
            <input type="submit" name="login" value="Login">
            <p>
              <a href="newPassword" name="password_forgotten">Password forgotten?</a>
            </p>
            <p>
                <a href="registration" name="register_account">Create a dev account</a>
            </p>
        </fieldset>
    </form>
  </div>

</body>

</html>