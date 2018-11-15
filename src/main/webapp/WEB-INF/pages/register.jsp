<!DOCTYPE html>
<html>

<head>
  <title>Register</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">
    <form action="registration" method="post">
        <div class="loginFieldset">
            <p>Register a dev account</p>
            <input class="loginInput" type="text" name="firstName" placeholder="Firstname" required autofocus> <br />
            <input class="loginInput" type="text" name="lastName" placeholder="Lastname" required> <br />
            <input class="loginInput" type="email" name="email" placeholder="Email Address" required><br />
            <input class="loginInput" type="password" name="password" placeholder="Password" required><br />
            <input class="loginInput" type="password" name="passwordConfirmation" placeholder="Confirm Password" required><br />
            <select class="loginInput" name="secretQuestion" required>
                <option value="life">Is my life fucked up?</option>
                <option value="studies">Where did you study?</option>
            </select><br />
            <input class="loginInput" type="text" name="secretAnswer" placeholder="Secret Answer" required><br />
            <input class="loginButon" type="submit" name="register" value="Register"> <br />

            <p><a href="login" class="loginLink">Back</a></p>
        </div>


    </form>
  </div>

</body>

</html>