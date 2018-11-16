<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/header.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/profile.css">
</head>

<body>

<div class="flex-container">
    <c:if test="${sessionScope.error ne null}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        ${sessionScope.error}
    </div>
    </c:if>
    <c:if test="${sessionScope.success ne null}">
    <div class="success">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        ${sessionScope.success}
    </div>
    </c:if>
    <h1>Profile </h1>
    <div class="user-info">
        <div id="img-profile">
            <img class="profilePicture" src="${sessionScope.userSession.imageUrl}" alt="profile.jpg" width="200px" height="200px" style="float:left;">
            <br/>
            <form action="profile" method="post">
                <label>url to your profile picture:</label>
                <input type="url" name="profilePictureLink" placeholder="max: 200x200px" value="${sessionScope.userSession.imageUrl}">
                <input class="loginButon" type="submit" name="profilePictureButton" value="Save image">
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
        <input class="loginButon" type="submit" name="saveDescription" value="Save description">
    </form>
</div>

</body>

</html>