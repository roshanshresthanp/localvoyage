<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>

    <link rel="stylesheet" href="../../assets/admin/css/custom.css">
</head>
<body>

<!-- Sidebar -->
<!-- require '../auth/auth.php'; -->

 <?php
//   include('../include/sidebar.php');

  include __DIR__ . '/../include/sidebar.php';
    require_once 'database/connection.php';

//   die(url('admin/category/show').'sds' );
  ?>

<!-- Main Content -->
<div class="main-content">  

<?php
message();
?>

<!-- <div class="alert">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  This is a simple alert message!
</div> -->
    <!-- Top Bar -->
    <div class="top-bar">
        <h3>Yoga Course</h3>
        <div class="admin-info">
           <a href="<?php echo url('admin/category/show') ?>" class="btn">List Course</a>
        </div>    </div>

    <!-- Add User Form -->
    <div class="form-container">
    <h2>Yoga Course</h2>
    <form class="form-control" action="../../controllers/categoryController.php " enctype="multipart/form-data" method="POST">
        <!-- Text input -->
        <label for="name">Title</label>
        <input type="text" name="title" id="name" placeholder="Enter title" required>

        <!-- Textarea -->
        <label for="message">Description</label>
        <textarea id="message" placeholder="Enter description" name="description"></textarea>

        <!-- Radio buttons -->
        <div class="radio-group">
            <label>Publish Status</label>
            <label><input type="radio" name="status" value="1" required> Active</label>
            <label><input type="radio" name="status" value="0"> Inactive</label>
        </div>

           <!-- Select dropdown -->
        <label for="country">Yoga Type</label>
        <select id="country" required name="type">
           <?php 
            foreach(YOGA_TYPE as $yoga){
                echo '<option value="'.$yoga.'">'.$yoga.'</option>';
            }
           ?>
        </select>

        <label for="name">Video URL</label>
        <input type="text" name="link" id="link" placeholder="Enter video URL" required>
        <button type="submit">Submit</button>
    </form>
</div>
</div>

</body>
</html>
