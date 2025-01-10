<?php
require_once 'helpers.php';
?>

<!doctype html>
<html lang="en">
<head>
<?php include_once('include/meta.php') ?>
	<title>Yoga Course | Arden University</title>
</head>
<body>
	<!--Doc Wrapper-->
	<div id="wrapper">
		<?php 
		include_once('include/header.php'); 
		$request = $_SERVER['REQUEST_URI'];
				$parts = explode('/', trim($request, '/'));
				$yoga_type = end($parts);
		?>
		<h2 class="feature"><?php echo $yoga_type; ?> Yoga</h2>
		<div class="video-list">
    <!-- Video Item 1 -->
			<?php 
				require_once 'database/connection.php';

				$conn = connectDB();
				$sql = "SELECT * FROM categories WHERE type = ? AND status = 1";
				$stmt = $conn->prepare($sql);
				$stmt->bind_param("s", $yoga_type);
				$stmt->execute();
				$result = $stmt->get_result();

				// Check if there are results and output data
				if ($result->num_rows > 0) {
					while ($row = $result->fetch_assoc()) {
						// print_r($row);  
						
						echo '<div class="video-item">
						<iframe class="video-iframe" src="'.$row['link'].'" 
								title="Video 1" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
						<div class="video-details">
							<h3 class="video-title">'.$row['title'].'</h3>
							<p class="video-description">'.$row['description'].'</p>
						</div>
					</div>';
					}
				} else {
					echo "<h3 class='video-title' style='text-align:center;'>No records found in the database.</h3>";
				}

			?>
		</div>
		<footer>
			<p>&copy;yogaClass, <?php echo date('Y'); ?></p>
		</footer>


	</div>

</html>