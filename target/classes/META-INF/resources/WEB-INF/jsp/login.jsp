<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            text-align: center;
            background: url('img.png') center center fixed;
            background-size: cover;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        h2 {
            color: #333;
        }

        form {
                width: 500px;
                height: auto;
                margin: 20px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                background: rgba(255, 255, 255, 0.5); /* Adjust the alpha value as needed */
            }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input {
            width: calc(100% - 16px);
            padding: 8px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }

        img {
            width: 100px; /* Adjust the width as needed */
            height: auto; /* Maintain aspect ratio */
            margin-bottom: 20px; /* Add some space below the image */
        }
    </style>
</head>
<body>

<form action="/authenticate" method="post">
    <h1>LOGIN</h1>
        <img src="<c:url value='/user.png' />" alt="User Image">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required onfocus="handleFocus(this)" onblur="handleBlur(this)"><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="submit">Login</button>
    </form>

    <script>
        function handleFocus(element) {
            element.style.border = '2px solid #4CAF50'; // Example: Green border when focused
        }

        function handleBlur(element) {
            element.style.border = '1px solid #ccc'; // Example: Default border when not focused
        }
    </script>
</body>
</html>
