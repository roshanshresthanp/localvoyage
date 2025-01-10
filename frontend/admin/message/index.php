<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>

    <!-- <link rel="stylesheet" href="../assets/admin/css/custom.css"> -->
    <link rel="stylesheet" href="../assets/admin/css/custom.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<!-- Sidebar -->
<!-- require '../auth/auth.php'; -->

 <?php

// include __DIR__ . '/../config.php';
//   include('../include/sidebar.php');

  include __DIR__ . '/../include/sidebar.php';

  ?>

<!-- Main Content -->
<div class="main-content">
    <!-- Top Bar -->

<?php
message();
?>
    <div class="top-bar">
        <h3>Customer Enquiry</h3>
        <div class="admin-info">
           <!-- <a href="<?php echo url('admin/category/create') ?>" class="btn">Add category</a> -->
        </div>
    </div>
   

    <table>
        <tr>
            <td> S.N </td>
            <td> Full Name </td>
            <td> Email</td>
            <td>Message</td>
            <!-- <td>Action</td> -->
        </tr>
        <?php 

        require_once 'database/connection.php';
         $sql = "SELECT * FROM contacts";
         $result = connectDB()->query($sql);     
         if ($result->num_rows > 0) {
            $sn = 1;
             while ($row = $result->fetch_assoc()) {
                 echo "<tr>
                            <td>".$sn++." </td>
                            <td>".$row['name']." </td>
                            <td>".$row['email']." </td>
                            <td>".$row['message']." </td>
                        <tr>";
             }
         }
    ?>
                        
       

        </tr>
    </table>
    </table>
    
</div>

</body>
</html>
