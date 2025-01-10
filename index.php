<?php
session_start();
$_SESSION['previous_page'] = $_SERVER['REQUEST_URI'];
include('route/route.php');
require_once __DIR__. '/config.php';

?>

