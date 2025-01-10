<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
</head>

<body>
    <div id="wrapper">
        <?php include_once 'include/header.php'; ?>
        <div class="pt"></div>
        <div class="login-container">

            <?php
            if (isset($_SESSION['logged_in']) && !empty($_SESSION['logged_in'])) {
                echo '<h3 style="padding: bottom 15px;">User is already logged in</h3>';
            }else{
                echo '<h3 style="padding-bottom:15px">Log in</h3>';
            }
            ?>
            <!-- <form id="loginForm" onsubmit="return validateLoginForm()"> -->
            <form action="controllers/loginController.php" method="POST">

                <?php
                if (isset($_SESSION['message'])) {
                    echo '<div class="message">' . $_SESSION['message'] . '</div>';
                    unset($_SESSION['message']); // Clear the message after displaying it
                }
                ?>
                <div id="error-message" class="error">Please fill in all fields correctly.</div>


                <!-- Email Field -->
                <input type="email" id="email" placeholder="Email" name="email">

                <!-- Password Field -->
                <input type="password" id="password" placeholder="Password" name="password">

                <!-- Role Selection -->
                <select id="role">
                    <option value="" disabled selected>Select Role</option>
                    <option value="admin">Admin</option>
                    <!-- <option value="customer">Customer</option> -->
                </select>

                <!-- Submit Button -->
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
    <script>
        // Function to validate the login form
        function validateLoginForm() {
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();
            const role = document.getElementById('role').value;
            const errorMessage = document.getElementById('error-message');

            // Check if all fields are filled
            if (email === '' || password === '' || role === '') {
                errorMessage.style.display = 'block';
                return false;
            } else {
                errorMessage.style.display = 'none';
                alert('Login successful!');
                return true;
            }
        }
    </script>

</body>

</html>
