<!DOCTYPE html>
<html>

<head>
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">

      <% if(request.getAttribute("error") != null) {%>
      <div class="alert">
          <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
          <%=request.getAttribute("error")%>
      </div>
      <%}%>

    <form action="login" method="post">
        <div class="loginFieldset">
            <!--<img src="AMTpeche.bmp" alt="AMTpeche">-->

            <p>Login</p>
            <input class="loginInput" type="email" name="email" id="emailField" placeholder="Email Address" autofocus required><br />
            <input class="loginInput" type="password" name="password" id="passwordField" placeholder="Password" required><br />
            <input class="loginButon" type="submit" name="login" id="submitLogin" value="Login">

            <p>
              <a href="newPassword" name="password_forgotten" id="linkPasswordForgotten">Password forgotten?</a>
            </p>
            <p>
                <a href="registration" name="register_account" id="linkRegisterAccount">Create a dev account</a>
            </p>
        </div>
    </form>
  </div>

</body>

</html>