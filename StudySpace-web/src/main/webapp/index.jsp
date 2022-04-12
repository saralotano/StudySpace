<%--
  Created by IntelliJ IDEA.
  User: sara
  Date: 01/09/2021
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="./css/indexStyle.css">

    <head>
        <title>StudySpace</title>
    </head>

    <body>

        <div><br>
            <img src="./images/study.svg" alt="Icon" id="logo">
        </div>

        <form class="w3-container" name="login" action = "./controllerLogin" method="POST">
            <div class="w3-section">
                <label><b>Username</b></label>
                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="username" required autofocus>
                <label><b>Password</b></label>
                <input class="w3-input w3-border" type="password" placeholder="Enter Password" name="password" required>
                <button id="login-button" class="w3-button w3-block w3-section w3-padding" type="submit">Login</button>
            </div>
        </form>

    </body>
</html>