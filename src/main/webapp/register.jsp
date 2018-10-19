<!DOCTYPE html>
<html>

<head>
  <title>Register</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
  <!-- on va peut etre pas garder cette div-->
  <div class="main">
    <form action="" method="post">
        <fieldset>
            <p>Register a dev account</p>
            <input type="text" name="Fistname" placeholder="Firstname" required autofocus> <br />
            <input type="text" name="Lastname" placeholder="Lastname" required> <br />
            <input type="email" name="email" placeholder="Email Address" required><br />
            <input type="password" name="password" placeholder="Password" required><br />
            <input type="password" name="confirm_password" placeholder="Confirm Password" required><br />
            <select name="secret_question" required>
                <option value="life">Is my life fucked up?</option>
                <option value="studies">Where did you study?</option>
            </select><br />
            <input type="text" name="secret_answer" placeholder="Secret Answer" required><br />
            <input type="submit" name="register" value="Register">
            
        </fieldset>
    </form>
  </div>

</body>

</html>