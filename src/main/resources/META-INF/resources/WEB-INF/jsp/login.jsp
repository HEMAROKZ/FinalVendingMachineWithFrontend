<!-- login.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Add your styles and meta tags here -->
    <!-- ... -->
</head>
<body>
    <form id="loginForm">
        <!-- Your existing form elements here -->
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required onfocus="handleFocus(this)" onblur="handleBlur(this)"><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="button" onclick="submitLoginForm()">Login</button>
    </form>

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
        function submitLoginForm() {
            var username = $('#username').val();
            var password = $('#password').val();

            $.ajax({
                type: 'POST',
                url: '/authenticate',
                contentType: 'application/json',
                data: JSON.stringify({ "username": username, "password": password }),
                success: function(data) {
                    // Handle success, e.g., redirect to home page
                    window.location.href = '/home';
                },
                error: function(error) {
                    // Handle error, e.g., display an error message
                    console.log(error);
                }
            });
        }
    </script>
</body>
</html>
