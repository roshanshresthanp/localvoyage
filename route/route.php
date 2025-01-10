<?php
require 'auth/auth.php';
require_once 'config.php';
require_once 'database/connection.php';
function handleRoute() {

    // Get the full URL path
$request = $_SERVER['REQUEST_URI'];

$request = str_replace(BASE_URL, "", $request);
// die($request);
    switch ($request) {
        case '/':
            require 'frontend/customer/index.php';
            break;
        case '/home':
            require 'frontend/customer/index.php';
            break;
        case '/login':
            require 'frontend/admin/login.php';
            break;
        case '/contact':
            require 'frontend/customer/contact.php';
            break;


        case '/dashboard':
            protectRoute();   // Protect this route  
            require 'frontend/admin/dashboard.php';
            break;
        
        case '/admin/category/create':
            protectRoute();   // Protect this route
            require 'frontend/admin/category/create.php';
            break;

        case '/admin/category/show':
            protectRoute();   // Protect this route
            require 'frontend/admin/category/index.php';
            break;
            
        case '/admin/message':
            protectRoute();   // Protect this route
            require 'frontend/admin/message/index.php';
            break;

         case (strpos($request,'/category/edit/') !== false):
            protectRoute();   // Protect this route
            $parts = explode('/', trim($request, '/'));
            $id = end($parts);
            if(!is_numeric($id)){
                echo 'The given id is not a number';
                exit;
            }
            require 'frontend/admin/category/edit.php';
            break;

        case '/admin/category/create':
            protectRoute();   // Protect this route
            require 'frontend/admin/category/create.php';
            break;

        case '/logout':
            logout();
            header('Location: login');
            exit();
        default:

          // Check if the request matches any type in YOGA_TYPE array
          if (in_array(str_replace('/', "", $request), YOGA_TYPE)) {
            require 'frontend/customer/page.php';
        } else {
            // die('failed successfully');
           
            http_response_code(404);
            require 'frontend/404.php';
        }

      
            break;
    }


}



handleRoute();
?>
