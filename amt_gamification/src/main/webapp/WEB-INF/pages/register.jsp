<!DOCTYPE html>
<html>

<head>
  <title>Register</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <div class="main">
    <form action="registration" id="registration" method="post">
        <fieldset>
            <p>Register a dev account</p>
            <input type="text" name="firstName" id="firstNameField" placeholder="Firstname" required autofocus> <br />
            <input type="text" name="lastName" id="lastNameField" placeholder="Lastname" required> <br />
            <input type="email" name="email" id="emailField" placeholder="Email Address" required><br />
            <input type="password" name="password" id="passwordField" placeholder="Password" required><br />
            <input type="password" name="passwordConfirmation" id="passwordConfirmationField" placeholder="Confirm Password" required><br />
            <select name="secretQuestion" id="secretQuestionField" required>
                <option value="life">Is my life fucked up?</option>
                <option value="studies">Where did you study?</option>
            </select><br />
            <input type="text" name="secretAnswer" id="secretAnswerField" placeholder="Secret Answer" required><br />
            <input type="submit" name="register" id="registerSubmit" value="Register">
        </fieldset>
    </form>
  </div>

</body>

</html>