<!--start Header -->
<header>
    <div id="callout">
        <h3>&#9742;<?php 
        require_once 'database/connection.php';
        require_once('helpers.php'); echo $contact ?></</h3>
        <p>
        Coventry CV3 4FJ1</p>
    </div>
    <div id="logo">
    </div>
</header>
<!--End Header -->

<!--Start Navigation -->
<nav>
    <a href="#" id="menu-icon"></a>
    
    <ul >
        <li><a href="<?php echo url('/'); ?> ">Home</a></li>
        <?php
            $ite = 0;
            foreach(YOGA_TYPE as $type){
                $ite++;
                if ($ite == 4) {
                    break; // Stop script execution
                }
                echo '        <li><a href="'.url($type).'">'.$type.'</a></li>

';
            }

            echo '        <li><a href="'.url('contact').'">Contact</a></li>
'
        ?>
        <?php 
        if (isset($_SESSION["logged_in"])) {
            echo '<li><a href="'.url('dashboard').'" class="btn" target="_blank"><b>DASHBOARD</b></a></li>';
        }else{
            echo '<li><a href="login" class="btn"><b>Log in</b></a></li>';
        }
        ?>
    </ul>
    
</nav>
<!--End Navigation -->	

<!--Start Style -->
	<link rel="stylesheet" href="assets/customer/css/custom.css">