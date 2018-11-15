<!DOCTYPE html>
<html>

<head>
    <title>Question</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
<div class="main">
    <form action="questionAuth" id="questionAuth" method="post">
        <div class="loginFieldset">
            <p>${question}</p>
            <input class="loginInput" type="text" name="response" id="responseField" placeholder="response" required autofocus> <br />

            <input class="loginButon" type="submit" name="questionAuthButton" id="submitQuestionAuth" value="response">

        </div>
    </form>
</div>

</body>

</html>