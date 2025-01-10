<?php
require_once 'helpers.php';
?>

<!doctype html>
<html lang="en">
<head>
<?php include_once('include/meta.php') ?>
	<title>Yoga Training | Arden University</title>
</head>
<body>
	<!--Doc Wrapper-->
	<div id="wrapper">
		<?php include_once('include/header.php') ?>
		<h2 class="feature">Explore Yoga Courses</h2>
		<div class="video-list">
    <!-- Video Item 1 -->
			<?php 
				// require_once 'database/connection.php';
				$conn = connectDB();

				$sql = "SELECT * FROM categories WHERE status = 1";
				$stmt = $conn->prepare($sql);
				$stmt->execute();
				$result = $stmt->get_result();

				// Check if there are results and output data
				if ($result->num_rows > 0) {
					// Fetch and display each row if needed
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
		

		<!--Start of Contact Us Section-->
		<a name="contact-link">
		<section class="contact" style="padding-top: 15px;">
			<div class="contactWrapper">
				<h3 style="text-decoration: underline;">CONTACT US</h3>
				<br>
				<p class="contact-address">
					yogaClass<br>
					<strong class="phone"><?php echo $contact ?></strong><br><br>
					<?php echo $address ?>
					yogaClass@gmail.com
				</p>
				<br>
				<ul class="social">
					<li><a href="#"><i class="fa fa-facebook"></i></a></li>
					<li><a href="#"><i class="fa fa-twitter"></i></a></li>
					<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
					<li><a href="#"><i class="fa fa-youtube"></i></a></li>
				</ul>
			</div>
			<div class="contactWrapper">
				<h3 style="text-decoration: underline;">OUR LOCATION</h3>
				<br>
				<div class="google-map">
				<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2436.0443638323745!2d-1.4705229233279817!3d52.36961557202218!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4877498da2c26cb9%3A0x4968f82759308638!2sArden%20University!5e0!3m2!1sen!2suk!4v1731587537780!5m2!1sen!2suk" width="500" height="250" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
				</div>
				

			</div>
		</section>
		<!--End of Contact Us Section-->
      
		<!--Copyright section-->
		<footer>
			<p>&copy;yogaClass, <?php echo date('Y'); ?></p>
		</footer>


	</div>
	<!--End Doc Wrapper-->


</html>