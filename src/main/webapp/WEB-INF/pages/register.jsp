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
            <input class="loginInput" type="text" name="firstName" id="firstNameField" placeholder="Firstname" required autofocus> <br />
            <input class="loginInput" type="text" name="lastName" id="lastNameField" placeholder="Lastname" required> <br />
            <input class="loginInput" type="email" name="email" id="emailField" placeholder="Email Address" required><br />
            <input class="loginInput" type="password" name="password" id="passwordField" placeholder="Password" required><br />
            <input class="loginInput" type="password" name="passwordConfirmation" id="passwordConfirmationField" placeholder="Confirm Password" required><br />
            <select class="loginInput" name="secretQuestion" id="secretQuestionField" required>
                <option value="life">Is my life fucked up?</option>
                <option value="studies">Where did you study?</option>
            </select><br />
            <input class="loginInput" type="text" name="secretAnswer" id="secretAnswerField" placeholder="Secret Answer" required><br />
            <input class="loginButon" type="submit" name="register" id="registerSubmit" value="Register"> <br />

            <p><a href="login" class="loginLink" id="backButton">Back</a></p>
        </div>


    </form>
  </div>

</body>

</html>