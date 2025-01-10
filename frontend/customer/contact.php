<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us</title>
</head>
<body>
<div id="wrapper">
    <?php include_once('include/header.php') ?>	
            <div class="pt"></div>
        <div class="contact-container">
                <h3 style="padding-bottom:15px">Contact Us</h3>

                <?php
message();
?>
                <form id="contactForm" action="controllers/FrontController.php" method="POST">
                    <div id="error-message" class="error">Please fill in all fields correctly.</div>
                    
                    <input type="text" name="name" id="name" placeholder="Your Name">
                    <input type="email" id="email" name="email" placeholder="Your Email">
                    <textarea id="message" placeholder="Your Message" name="message"></textarea>
                    
                    <button type="submit">Submit</button>
                </form>
        </div>

        <!--Start of Contact Us Section-->
		<!-- <a name="contact-link"> -->
            <br><br>
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
    
</div>


<script>
    // Form validation function
    document.getElementById('contactForm').addEventListener('submit', function(event) {
        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value.trim();
        const message = document.getElementById('message').value.trim();
        const errorMessage = document.getElementById('error-message');
        
        // Check if all fields are filled
        if (name === '' || email === '' || message === '') {
            errorMessage.style.display = 'block';
            event.preventDefault(); // Prevent form submission
        } else {
            errorMessage.style.display = 'none';
            // alert('Message sent successfully!');
        }
    });
</script>

</body>
</html>
