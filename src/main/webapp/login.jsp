<!DOCTYPE html>
<html>

<head>
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <!-- on va peut etre pas garder cette div-->
  <div class="main">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <fieldset>
            <p>Login</p>
            <input type="email" name="email" placeholder="Email Address" autofocus required><br />
            <input type="password" name="password" placeholder="Password" required><br />
            <input type="submit" name="login" value="Login">
            <p>
              <a href="#" name="password_forgotten">Password forgotten?</a>
            </p>
            <p>
              <a href="registration" name="register_account">Create a dev account</a>
            </p>
        </fieldset>
    </form>
  </div>

</body>

</html>