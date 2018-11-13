<!DOCTYPE html>
<html>

<head>
  <title>Register</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">
    <form action="registration" method="post">
        <fieldset>
            <p>Register a dev account</p>
            <input type="text" name="firstName" placeholder="Firstname" required autofocus> <br />
            <input type="text" name="lastName" placeholder="Lastname" required> <br />
            <input type="email" name="email" placeholder="Email Address" required><br />
            <input type="password" name="password" placeholder="Password" required><br />
            <input type="password" name="passwordConfirmation" placeholder="Confirm Password" required><br />
            <select name="secretQuestion" required>
                <option value="life">Is my life fucked up?</option>
                <option value="studies">Where did you study?</option>
            </select><br />
            <input type="text" name="secretAnswer" placeholder="Secret Answer" required><br />
            <input type="submit" name="register" value="Register">
        </fieldset>
    </form>
  </div>

</body>

</html>