-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2023 at 05:15 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'sugar', '123qwerty'),
(2, 'deegii', '8767');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `position` varchar(200) DEFAULT NULL,
  `age` varchar(200) DEFAULT NULL,
  `salary` varchar(200) DEFAULT NULL,
  `idWorking` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `position`, `age`, `salary`, `idWorking`) VALUES
(9, 'sugar', 'security', '22', '20000', 1),
(10, 'bob', 'chef', '34', '2000000', 0),
(11, 'sugardelger', 'tsewerlegch', '21', '100000000', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `comment` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`id`, `comment`) VALUES
(1, 'kayejgfluikegjfpuwi;er'),
(2, 'sain buudal baina'),
(3, '45676f');

-- --------------------------------------------------------

--
-- Table structure for table `guests`
--

CREATE TABLE `guests` (
  `id` int(11) NOT NULL,
  `FirstName` varchar(200) DEFAULT NULL,
  `LastName` varchar(200) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `Phone` int(11) DEFAULT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `Nationality` varchar(200) DEFAULT NULL,
  `PassportNumber` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guests`
--

INSERT INTO `guests` (`id`, `FirstName`, `LastName`, `Email`, `Phone`, `Address`, `Nationality`, `PassportNumber`) VALUES
(68, '', 'dgoijsodi', 'dgn', NULL, '', 'wqeuh', 'sdfoi'),
(84, '', '', '', 23, '', '', ''),
(86, '1', '', '', 1, '', '', ''),
(87, '2', '', '', 2, '', '', ''),
(88, '4', '4', '', 4, '', '', ''),
(89, '34', '', '', 33, '', '', ''),
(90, 'sjgasd,f', '', '', 64578, '', '', ''),
(91, 'g', '', '', 5, '', '', ''),
(92, 's', '', '', 9, '', '', ''),
(93, '', '', '', 88810914, '', '', ''),
(94, 'tuybiu', '', '', NULL, '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `guest_login`
--

CREATE TABLE `guest_login` (
  `id` int(11) NOT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `guest_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guest_login`
--

INSERT INTO `guest_login` (`id`, `username`, `password`, `guest_id`) VALUES
(6, '', '', 84),
(8, '1', '1', 86),
(9, '2', '2', 87),
(10, '4', '4', 88),
(11, '34', '5', 89),
(12, 'g', 'g', 91),
(13, 's', 's', 92),
(14, '', '', 93);

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `id` int(11) NOT NULL,
  `guest_id` int(200) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`id`, `guest_id`, `room_id`, `status`) VALUES
(3, 1, 22, 'declined'),
(4, 1, 20, 'declined'),
(5, 1, 34, 'declined'),
(6, 8, 22, 'declined'),
(7, 1, 22, 'approved'),
(9, 1, 22, 'declined'),
(10, 1, 34, 'declined'),
(11, 1, 34, 'declined'),
(12, 1, 22, 'approved'),
(13, 81, 34, 'approved'),
(14, 84, 23, 'declined'),
(15, 85, 23, 'declined'),
(16, 86, 26, 'declined'),
(17, 87, 24, 'approved'),
(18, 88, 35, 'approved'),
(19, 89, 29, 'declined'),
(20, 93, 20, 'approved');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `guestID` int(11) DEFAULT NULL,
  `roomID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id`, `guestID`, `roomID`) VALUES
(47, 68, 26),
(81, 90, 26),
(82, 87, 24),
(83, 88, 35),
(84, 93, 20),
(85, 94, 29);

-- --------------------------------------------------------

--
-- Table structure for table `reservation_login`
--

CREATE TABLE `reservation_login` (
  `id` int(11) NOT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation_login`
--

INSERT INTO `reservation_login` (`id`, `username`, `password`) VALUES
(1, 'sugar', '1');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `RoomNumber` int(11) DEFAULT NULL,
  `RoomType` varchar(200) DEFAULT NULL,
  `BedType` varchar(200) DEFAULT NULL,
  `RoomRate` varchar(200) DEFAULT NULL,
  `Availability` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`id`, `RoomNumber`, `RoomType`, `BedType`, `RoomRate`, `Availability`) VALUES
(20, 4, 'suite', '2', 'good', 0),
(22, 6, 'deluxe', '2', 'premium', 1),
(23, 7, 'normal', '1', 'good', 1),
(24, 8, 'normal', '2', 'excellent', 0),
(26, 10, 'suite', '2', 'good', 0),
(29, 13, 'normal', '1', 'good', 0),
(34, 18, 'deluxe', '2', 'premium', 1),
(35, 19, 'normal', '1', 'good', 0),
(38, 7890, 'uybin', 't7yi', '', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guests`
--
ALTER TABLE `guests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guest_login`
--
ALTER TABLE `guest_login`
  ADD PRIMARY KEY (`id`),
  ADD KEY `guest_id` (`guest_id`);

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `guestID` (`guestID`),
  ADD KEY `roomID` (`roomID`);

--
-- Indexes for table `reservation_login`
--
ALTER TABLE `reservation_login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `guests`
--
ALTER TABLE `guests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `guest_login`
--
ALTER TABLE `guest_login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT for table `reservation_login`
--
ALTER TABLE `reservation_login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `guest_login`
--
ALTER TABLE `guest_login`
  ADD CONSTRAINT `guest_login_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`guestID`) REFERENCES `guests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`roomID`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
