<!DOCTYPE html>
<%@include file="/WEB-INF/header.jsp"%>
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
            <img src="profile.jpg" alt="profile image" width="200px" height="200px" style="float:left;">
        </div>
        <div>
            <h2>Full name: ${sessionScope.userSession.firstName} ${sessionScope.userSession.lastName}</h2>
            <h2>Email: ${sessionScope.userSession.email}</h2>
        </div>
    </div>
    <div class="user-descr">
        blablabalbalbalb
    </div>
  </div>

</body>

</html>