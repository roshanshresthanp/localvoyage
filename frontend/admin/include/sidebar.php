<?php require_once 'config.php'; ?>
<div class="sidebar">
    <h2>YOGA</h2>
    <a href="<?php echo url('/') ; ?>"  target="_blank">Home</a>
    <a href="<?php echo url('dashboard') ; ?>" class="">Dashboard</a>
    <a href="<?php echo url('admin/category/show') ; ?>">Yoga Course</a>
    <a href="<?php echo url('admin/message') ; ?>">Customer Enquiry</a>
    <a href="<?php echo url('logout') ; ?>">Logout</a>
</div>