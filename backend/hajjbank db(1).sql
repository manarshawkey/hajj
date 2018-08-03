-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 03, 2018 at 04:40 AM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hajjbank`
--

-- --------------------------------------------------------

--
-- Table structure for table `atm`
--

DROP TABLE IF EXISTS `atm`;
CREATE TABLE IF NOT EXISTS `atm` (
  `id` int(11) NOT NULL,
  `longitude` float(9,6) DEFAULT NULL,
  `latitude` float(9,6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `fname` varchar(15) DEFAULT NULL,
  `lname` varchar(15) DEFAULT NULL,
  `buildingno` int(11) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL,
  `currency` varchar(15) DEFAULT NULL,
  `tellerid` int(15) DEFAULT NULL,
  `balance` int(7) DEFAULT '0',
  `phone` varchar(15) NOT NULL,
  `districtno` int(3) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`fname`, `lname`, `buildingno`, `city`, `id`, `email`, `currency`, `tellerid`, `balance`, `phone`, `districtno`, `password`) VALUES
('ali', 'osama', 213, 'mecca', 4, 'ali@email', 'EGP', NULL, 2, '012822', 2, '321'),
('ali', 'osama', 213, 'mecca', 3, 'osama@email', 'EGP', NULL, 789, '0123333', 2, '321');

-- --------------------------------------------------------

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
CREATE TABLE IF NOT EXISTS `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storename` varchar(25) DEFAULT NULL,
  `latitude` float(9,6) DEFAULT NULL,
  `longitude` float(9,6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `merchant`
--

INSERT INTO `merchant` (`id`, `storename`, `latitude`, `longitude`) VALUES
(1, 'zara', 1.009000, 2.099000),
(2, 'Mango', 2.101000, 4.098000),
(4, 'H&M', 4.092000, 4.300100),
(5, 'Seudi', 3.092100, 1.003000),
(6, 'Bandah', 3.321000, 1.203000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE IF NOT EXISTS `transaction` (
  `refno` int(11) NOT NULL AUTO_INCREMENT,
  `merchantid` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `atmNo` int(11) DEFAULT NULL,
  `clientid` int(11) DEFAULT NULL,
  `tr_time` timestamp NULL DEFAULT NULL,
  `client2phone` int(15) DEFAULT NULL,
  `storename` varchar(20) DEFAULT NULL,
  `client1phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`refno`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`refno`, `merchantid`, `amount`, `type`, `atmNo`, `clientid`, `tr_time`, `client2phone`, `storename`, `client1phone`) VALUES
(1, 1, 350, 'purchase', NULL, 3, '2018-08-03 04:19:52', NULL, 'zara', NULL),
(2, 2, 450, 'purchase', NULL, 3, '2018-08-03 04:20:38', NULL, 'Mango', NULL),
(3, NULL, 500, 'withdrawal', 1, 3, '2018-08-03 04:22:03', NULL, 'ATM', NULL),
(4, NULL, 2000, 'deposit', 1, 3, '2018-08-03 04:22:24', NULL, 'ATM', NULL),
(5, NULL, 500, 'transfer money', 1, 3, '2018-08-03 04:24:07', 12822, 'ATM', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
