<?php
session_start();
require '../auth/auth.php';


// loginController.php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = isset($_POST['email']) ? trim($_POST['email']) : null;
    $password = isset($_POST['password']) ? trim($_POST['password']) : null;

    if ($email && $password) {
        login($email,$password);
    } else {
        $_SESSION["message"] = "Please enter email and password !!";
        header("Location: " . $_SERVER['HTTP_REFERER']);
        exit();
    }
} else {
    echo "Something is wrong, please try again later";
}
?>
