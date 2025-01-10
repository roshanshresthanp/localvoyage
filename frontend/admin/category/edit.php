<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>

    <link rel="stylesheet" href="../../../assets/admin/css/custom.css">
</head>
<body>

<!-- Sidebar -->
<!-- require '../auth/auth.php'; -->

 <?php
//   include('../include/sidebar.php');

  include __DIR__ . '/../include/sidebar.php';

  ?>

<!-- Main Content -->
<div class="main-content">  

<?php
message();
?>

    <!-- Top Bar -->
    <div class="top-bar">
        <h3>Yoga Course</h3>
        <!-- <div class="admin-info">Welcome, Admin</div> -->
    </div>

    <!-- Add User Form -->
    <div class="form-container">
    <h2>Yoga Course</h2>
    <form class="form-control" action="../../../controllers/categoryController.php " enctype="multipart/form-data" method="POST">
    <input type="hidden" name="_method" value="PUT">
        <!-- Text input -->
         <?php 
            $parts = explode('/', trim($_SERVER['REQUEST_URI'], '/'));
            $id = end($parts);

            // echo $id;

            require_once 'database/connection.php';
            $sql = "SELECT * FROM categories WHERE id = ".$id;
            $result = connectDB()->query($sql);  

            $data = $result->fetch_assoc();
            $title = $data['title'] ?? null;
            $description = $data['description'] ?? null; 
            $status = $data['status']?? null;
            $link = $data['link'] ?? null; 
            $type = $data['type'] ?? null; 

            // echo $status;
            // var_dump(($result->fetch_assoc())['title']);


            require_once 'config.php';


            // die($id);
         ?> 
                 <input type="hidden" name=id value="<?php echo $id?>" >

        <label for="name">Title</label>
        <input type="text" name=title id="name" value="<?php echo $title?>" required>

        <!-- Email input -->
        <!-- <label for="email">Email</label>
        <input type="email" name = "" id="email" placeholder="Enter your email" required> -->

        <!-- Textarea -->
        <label for="message">Description</label>
        <textarea id="message" placeholder="Enter description" name="description"> <?php echo $description?></textarea>

        <!-- Radio buttons -->
        <div class="radio-group">
            <label>Publish Status</label>
            <label><input type="radio" <?php if($status == 1) echo 'checked'; ?> name="status" value="1" required> Active</label>
            <label><input type="radio" name="status" <?php if($status == 0) echo 'checked'; ?> value="0"> Inactive</label>
        </div>

        <label for="country">Yoga Type</label>
        <select id="country" required name="type">
           <?php 
          foreach (YOGA_TYPE as $yoga) {
            echo '<option value="' . $yoga . '" ' . 
                 (($yoga == $type) ? "selected" : "") . 
                 '>' . $yoga . '</option>';
        }
           ?>
        </select>

        <label for="name">Video URL</label>
        <input type="text" name="link" id="link" placeholder="Enter video URL" value="<?php echo $link?>">

        <!-- Checkboxes -->
        <!-- <div class="checkbox-group">
            <label>Interests</label>
            <label><input type="checkbox" name="interests" value="sports"> Sports</label>
            <label><input type="checkbox" name="interests" value="music"> Music</label>
            <label><input type="checkbox" name="interests" value="tech"> Technology</label>
        </div> -->

        <!-- Select dropdown -->
        <!-- <label for="country">Country</label>
        <select id="country" required>
            <option value="">Select your country</option>
            <option value="us">United States</option>
            <option value="ca">Canada</option>
            <option value="uk">United Kingdom</option>
            <option value="in">India</option>
            <option value="other">Other</option>
        </select> -->

        <!-- Date input -->
        <!-- <label for="dob">Date of Birth</label>
        <input type="date" id="dob" required> -->

        <!-- Time input -->
        <!-- <label for="appointment">Preferred Appointment Time</label>
        <input type="time" id="appointment"> -->

        <!-- File input -->
        <!-- <label for="resume">Image</label>
        <input type="file" id="resume" accept=".jpeg,.jpg,.png,.svg,.web" name="image"> -->

        <!-- Submit button -->
        <button type="submit">Submit</button>
    </form>
</div>
</div>

</body>
</html>
