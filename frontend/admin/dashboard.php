<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>
</head>
<body>
<link rel="stylesheet" href="assets/admin/css/custom.css">
<!-- Sidebar -->
<?php 
require_once 'database/connection.php';

include('include/sidebar.php') 
?>

<!-- Main Content -->
<div class="main-content">
    <!-- Top Bar -->
    <div class="top-bar">
        <h3>Dashboard</h3>
        <!-- <div class="admin-info">Welcome</div> -->
    </div>

    <!-- Dashboard Content -->
    <div class="dashboard-cards">
        <div class="card">
            <h4>Yoga Type</h4>
            <p><?php echo count(YOGA_TYPE) ?></p>
        </div>
        <div class="card">
            <h4>Yoga Classes</h4>
            <p><?php   
                $conn = connectDB();
                $stmt = $conn->prepare("SELECT COUNT(*) as count FROM categories");
                $stmt->execute();
                $stmt->bind_result($count);
                $stmt->fetch();
                echo $count;
            ?></p>
        </div>
    </div>
    
</div>

    



</body>
</html>
