<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp" %>
<html>

<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/profile.css">
</head>

<body>

<div class="flex-container">
    <h1>Profile </h1>
    <div class="user-info">
        <div id="img-profile">
            <img src="${sessionScope.userSession.imageUrl}" alt="profile.jpg" width="200px" height="200px" style="float:left;">
            <br/>
            <form action="profile" method="post">
                <label>url to your profile picture:</label>
                <input type="url" name="profilePictureLink" placeholder="max: 200x200px" value="${sessionScope.userSession.imageUrl}">
                <input type="submit" name="profilePictureButton" value="Save image">
            </form>
        </div>
        <div>
            <h2 id="fullName">Full name: ${sessionScope.userSession.firstName} ${sessionScope.userSession.lastName}</h2>
            <h2 id="email">Email: ${sessionScope.userSession.email}</h2>
        </div>
    </div>

    <h2>My Description</h2>
    <form action="profile" method="post">
        <textarea class="loginInput" name="userDescr" cols="100" rows="10">${sessionScope.userSession.description}</textarea>
        <input type="submit" name="saveDescription" value="Save Changes">
    </form>
</div>

</body>

</html>