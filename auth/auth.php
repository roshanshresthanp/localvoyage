<?php
require_once __DIR__ . '/../config.php';
const USERNAME = 'yoga@admin.com';
const PASSWORD = 'password';

// Login function
function login($username, $password) {
    if ($username === USERNAME && $password === PASSWORD) {
        $_SESSION['logged_in'] = true;
        header("Location: ".url('dashboard'));
        exit();
    }
    // echo "loinn failde";
    $_SESSION["message"] = "Please enter correct email or password !!";
    header("Location: " . $_SERVER['HTTP_REFERER']);
    exit();
}

// Logout function
function logout() {
    unset($_SESSION['logged_in']);
    session_destroy();
    header("Location: home");
    exit();
}

// Check if user is logged in
function isAuthenticated() {
    return isset($_SESSION['logged_in']) && $_SESSION['logged_in'] === true;
}

// Protect route function
function protectRoute() {
    if (!isAuthenticated()) {
        header('Location: login');
        exit();
    }
}
?>
