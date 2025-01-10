<?php
session_start();
require '../auth/auth.php';
require_once '../database/connection.php';
//for update
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['_method']) && $_POST['_method'] == 'PUT') {
    
    // Set parameters and execute
    $title = isset($_POST['title']) ? trim($_POST['title']) : null;
    $description = isset($_POST['description']) ? trim($_POST['description']) : null;
    $status = isset($_POST['status']) ? $_POST['status'] : 1;
    $img = isset($_POST['image']) ? $_POST['image'] : null;
    $type = isset($_POST['type']) ? $_POST['type'] : null;
    $link = isset($_POST['link']) ? $_POST['link'] : null;
    
    $id = $_POST['id'];

    $conn = connectDB();
    $stmt = $conn->prepare("UPDATE categories SET title = ?, link = ?, type = ?, description = ?, status = ? WHERE id = ?");
    $stmt->bind_param("ssssii", $title, $link, $type, $description, $status, $id);

    if ($stmt->execute()) {
        $_SESSION["success"] = "Record Updated Successfully";
        header("Location: " . url('admin/category/show'));
        exit();
    } else {
        // echo "Error: " . $stmt->error;
        $_SESSION["error"] = "Problems in updating record";
        header("Location: " . $_SERVER['HTTP_REFERER']);
        exit();
    }
    // Close the statement
    $stmt->close();

    exit();

}

//for delete
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['_method']) && $_POST['_method'] == 'DELETE') {
    
    $id = $_POST['id'];
    $conn = connectDB();
    $stmt = $conn->prepare("DELETE FROM categories WHERE id = ?");
    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        $_SESSION["success"] = "Record Deleted Successfully";
        header("Location: " . url('admin/category/show'));
        exit();
    } else {
        // echo "Error: " . $stmt->error;
        $_SESSION["error"] = "Problems in deleting record";
        header("Location: " . url('admin/category/show'));
        exit();
    }
    // Close the statement
    $stmt->close();
    exit();

}


//for create
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $conn = connectDB();

    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO categories (title, type, link, description, status, image) VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssis", $title, $type, $link, $description, $status, $img);

    // Set parameters and execute
    $title = isset($_POST['title']) ? trim($_POST['title']) : null;
    $description = isset($_POST['description']) ? trim($_POST['description']) : null;
    $status = isset($_POST['status']) ? $_POST['status'] : 1;
    $img = isset($_POST['image']) ? $_POST['image'] : null;
    $type = isset($_POST['type']) ? $_POST['type'] : null;
    $link = isset($_POST['link']) ? $_POST['link'] : null;

    if ($stmt->execute()) {
        $_SESSION["success"] = "Record Added Successfully";
        header("Location: " . $_SESSION['previous_page']);
        exit();
    } else {
        // echo "Error: " . $stmt->error;
        $_SESSION["error"] = "Problem in adding record";
        header("Location: " . $_SERVER['HTTP_REFERER']);
        exit();
    }
    // Close the statement
    $stmt->close();

}



?>
