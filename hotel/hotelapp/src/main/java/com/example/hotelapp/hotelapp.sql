-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 03, 2025 at 11:41 AM
-- Server version: 8.0.31
-- PHP Version: 8.1.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `password`, `role`, `username`, `first_name`, `last_name`, `profile_picture`) VALUES
(1, 'admin', 'ADMIN', 'admin', 'Eden', 'Hazard', '1739742003000_download.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `room_id` bigint NOT NULL,
  `tourist_id` bigint NOT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`),
  KEY `FKru6tbcvksnyc5iex7n9wdlo48` (`tourist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `check_in_date`, `check_out_date`, `status`, `room_id`, `tourist_id`, `total_price`) VALUES
(5, '2025-02-01', '2025-02-02', 'CONFIRMED', 5, 18, 100);

-- --------------------------------------------------------

--
-- Table structure for table `booking_experience`
--

DROP TABLE IF EXISTS `booking_experience`;
CREATE TABLE IF NOT EXISTS `booking_experience` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `experience_id` bigint NOT NULL,
  `tourist_id` bigint NOT NULL,
  `experience_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4suq3c4x4r8cexqyrlarsbcrv` (`experience_id`),
  KEY `FKcqc74prew8o1v21cdmadjovto` (`tourist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `booking_experience`
--

INSERT INTO `booking_experience` (`id`, `status`, `experience_id`, `tourist_id`, `experience_date`) VALUES
(1, 'CONFIRMED', 2, 18, '2025-02-20');

-- --------------------------------------------------------

--
-- Table structure for table `contact_form`
--

DROP TABLE IF EXISTS `contact_form`;
CREATE TABLE IF NOT EXISTS `contact_form` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `contact_form`
--

INSERT INTO `contact_form` (`id`, `created_at`, `email`, `message`, `name`, `subject`) VALUES
(1, '2025-02-26 02:41:28.407627', 'mn@gmail.com', 'test', 'mero naam', 'kei haina');

-- --------------------------------------------------------

--
-- Table structure for table `experience`
--

DROP TABLE IF EXISTS `experience`;
CREATE TABLE IF NOT EXISTS `experience` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `duration` varchar(255) NOT NULL,
  `experience_name` varchar(255) NOT NULL,
  `is_local` bit(1) NOT NULL,
  `price` double NOT NULL,
  `hotel_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk0lt4gjartpq212ttqa5qlg1n` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `experience`
--

INSERT INTO `experience` (`id`, `description`, `duration`, `experience_name`, `is_local`, `price`, `hotel_id`) VALUES
(2, 'Fanana Fananana Ghumam hai guyz', '4 hours', 'City tour', b'1', 100, 1);

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE IF NOT EXISTS `hotel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`id`, `password`, `role`, `username`, `address`, `contact_number`, `hotel_name`) VALUES
(1, 'hotel', 'HOTEL', 'hotel', 'Whipps Cross Road', '1234456789', 'Annapurna Hotel');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `feedback` varchar(1000) DEFAULT NULL,
  `rating` int NOT NULL,
  `experience_id` bigint NOT NULL,
  `hotel_id` bigint NOT NULL,
  `tourist_id` bigint NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FK4s56155v4j9k3iseo6qd1p51y` (`hotel_id`),
  KEY `FKmbprkr7cubj2vkdmlu5jcgyv` (`tourist_id`),
  KEY `FKi9c6ma5c7mjc3yt4p4cne94fh` (`experience_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`review_id`, `created_at`, `feedback`, `rating`, `experience_id`, `hotel_id`, `tourist_id`) VALUES
(2, '2025-03-03 11:23:44.668571', 'All Good', 5, 2, 1, 18);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_id` bigint NOT NULL,
  `available` bit(1) NOT NULL,
  `room_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `price`, `room_type`, `hotel_id`, `available`, `room_picture`) VALUES
(5, 100, 'Single', 1, b'1', '1740221105333_deluxe.jpg'),
(7, 200, 'Executive', 1, b'1', '1740219215171_banner.jpg'),
(10, 150, 'Deluxe', 1, b'1', '1740301624861_deluxe.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tourist`
--

DROP TABLE IF EXISTS `tourist`;
CREATE TABLE IF NOT EXISTS `tourist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `tourist_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tourist`
--

INSERT INTO `tourist` (`id`, `password`, `role`, `username`, `date_of_birth`, `first_name`, `gender`, `last_name`, `tourist_picture`) VALUES
(18, 'tourist', 'TOURIST', 'tourist', '2025-02-01', 'Thorgan', 'Male', 'Hazard', '1739818557450_banner.jpg'),
(29, 'nayat', 'TOURIST', 'nayat', '1990-01-02', 'Naya', 'Male', 'Tourist', '1739809607635_banner.jpg'),
(31, 'atourist', 'TOURIST', 'atourist', '2025-02-11', 'Admin', 'Male', 'Bata Hai', '1740417810207_download.jpg');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  ADD CONSTRAINT `FKru6tbcvksnyc5iex7n9wdlo48` FOREIGN KEY (`tourist_id`) REFERENCES `tourist` (`id`);

--
-- Constraints for table `booking_experience`
--
ALTER TABLE `booking_experience`
  ADD CONSTRAINT `FK4suq3c4x4r8cexqyrlarsbcrv` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`),
  ADD CONSTRAINT `FKcqc74prew8o1v21cdmadjovto` FOREIGN KEY (`tourist_id`) REFERENCES `tourist` (`id`);

--
-- Constraints for table `experience`
--
ALTER TABLE `experience`
  ADD CONSTRAINT `FKk0lt4gjartpq212ttqa5qlg1n` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `FK4s56155v4j9k3iseo6qd1p51y` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`),
  ADD CONSTRAINT `FKi9c6ma5c7mjc3yt4p4cne94fh` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`),
  ADD CONSTRAINT `FKmbprkr7cubj2vkdmlu5jcgyv` FOREIGN KEY (`tourist_id`) REFERENCES `tourist` (`id`),
  ADD CONSTRAINT `FKt6gj7yde2mbp38w5lpi5vmm9t` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
