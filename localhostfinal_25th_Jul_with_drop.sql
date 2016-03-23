-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 25, 2011 at 07:49 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `jaydee`
--
DROP DATABASE `jaydee`;
CREATE DATABASE `jaydee` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `jaydee`;

-- --------------------------------------------------------

--
-- Table structure for table `bank`
--

CREATE TABLE IF NOT EXISTS `bank` (
  `id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `cur_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank`
--


-- --------------------------------------------------------

--
-- Table structure for table `cash`
--

CREATE TABLE IF NOT EXISTS `cash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `cur_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `cash`
--

INSERT INTO `cash` (`id`, `amount`, `cur_time`) VALUES
(1, 0, '2011-07-23 09:06:32'),
(2, 0, '2011-07-25 03:06:55'),
(3, 0, '2011-07-25 03:17:42'),
(4, 0, '2011-07-25 03:22:52'),
(5, 0, '2011-07-25 03:27:36'),
(6, 0, '2011-07-25 03:29:13'),
(7, 0, '2011-07-25 03:32:54'),
(8, 0, '2011-07-25 03:34:22'),
(9, 0, '2011-07-25 03:40:36');

-- --------------------------------------------------------

--
-- Table structure for table `constants`
--

CREATE TABLE IF NOT EXISTS `constants` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `last_updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `tax` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `constants`
--

INSERT INTO `constants` (`cid`, `last_updated`, `tax`, `discount`) VALUES
(1, '0000-00-00 00:00:00', 16, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `desc` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--


-- --------------------------------------------------------

--
-- Stand-in structure for view `drug_constants`
--
CREATE TABLE IF NOT EXISTS `drug_constants` (
`name` varchar(45)
,`code` varchar(20)
,`limit` int(11)
,`qty` decimal(33,0)
);
-- --------------------------------------------------------

--
-- Table structure for table `drugs`
--

CREATE TABLE IF NOT EXISTS `drugs` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `desc` varchar(600) DEFAULT 'No description',
  `code` varchar(20) DEFAULT NULL COMMENT 'This is also the item code',
  `price` double NOT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `drugs`
--

INSERT INTO `drugs` (`did`, `name`, `desc`, `code`, `price`) VALUES
(1, 'panadol', 'wjikasnjk', 'sanjkasnkja', 24),
(2, 'quinine', 'Syrup', 'h6863h', 32),
(3, 'sadfas', 'No description', 'dsfas', 32),
(4, 'sadfas', 'No description', 'dsfas', 32),
(5, 'drydr', 'No description', 'gfdhfghj', 1231),
(6, 'drydr', 'No description', 'gfdhfghj', 1231),
(7, 'uuueueueueueuwiu', 'No description', 'gfdhfghj', 1231),
(9, '5tyer', 'gfdsgsdfg', 'eregs', 800),
(10, 'dsfsd', 'fdsfsd', 'dsfsd', 43234),
(11, 'sadasd', 'asdasd', 'asdasd', 32432432432423),
(12, 'sdfg', 'dfgdsg', 'dfsghdsg', 566545),
(13, 'sdfg', 'dfgdsg', 'dfsghdsg', 566545);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `did` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `batch_number` int(11) NOT NULL,
  `cur_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `expiry_date` date NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`iid`, `did`, `sid`, `batch_number`, `cur_date`, `expiry_date`, `qty`) VALUES
(1, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 900),
(2, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 672),
(3, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 900),
(4, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 672),
(5, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 900),
(6, 9, 1, 1, '2011-07-07 17:38:42', '2011-07-07', 672),
(7, 9, 4, 2, '2011-07-23 09:33:47', '2011-07-23', 0);

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `value` varchar(10000) NOT NULL,
  `caller` varchar(45) DEFAULT '',
  `cur_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `log`
--


-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE IF NOT EXISTS `order` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `did` int(11) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `order`
--


-- --------------------------------------------------------

--
-- Table structure for table `pos`
--

CREATE TABLE IF NOT EXISTS `pos` (
  `pos_id` int(11) NOT NULL AUTO_INCREMENT,
  `did` int(11) NOT NULL,
  `cost_per_item` double NOT NULL,
  `qty` int(11) NOT NULL,
  `cur_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `discount` double DEFAULT '0',
  `tax` double DEFAULT '0',
  `amount` double NOT NULL,
  `sid` int(11) NOT NULL,
  PRIMARY KEY (`pos_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `pos`
--

INSERT INTO `pos` (`pos_id`, `did`, `cost_per_item`, `qty`, `cur_time`, `discount`, `tax`, `amount`, `sid`) VALUES
(1, 7, 1231, 12, '2011-07-25 03:17:43', 0, 0, 1231, 2),
(2, 7, 1231, 12, '2011-07-25 03:22:52', 0, 0, 1231, 3),
(3, 7, 1231, 12, '2011-07-25 03:27:36', 0, 0, 1231, 4),
(4, 9, 800, 12, '2011-07-25 03:29:13', 0, 0, 800, 5),
(5, 7, 1231, 12, '2011-07-25 03:32:54', 0, 0, 1231, 6),
(6, 7, 1231, 13, '2011-07-25 03:34:22', 0, 0, 1231, 7),
(7, 7, 1231, 128, '2011-07-25 03:40:36', 0, 0, 1231, 8);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE IF NOT EXISTS `sales` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `total` double NOT NULL,
  `cur_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL DEFAULT '1',
  `cust_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`sid`, `total`, `cur_time`, `user_id`, `cust_id`) VALUES
(1, 0, '2011-07-23 08:59:24', 1, NULL),
(2, 0, '2011-07-25 03:17:43', 1, NULL),
(3, 0, '2011-07-25 03:22:52', 1, NULL),
(4, 0, '2011-07-25 03:27:36', 1, NULL),
(5, 0, '2011-07-25 03:29:13', 1, NULL),
(6, 0, '2011-07-25 03:32:54', 1, NULL),
(7, 0, '2011-07-25 03:34:22', 1, NULL),
(8, 0, '2011-07-25 03:40:36', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `stid` int(11) NOT NULL AUTO_INCREMENT,
  `did` int(11) NOT NULL,
  `limit` int(11) NOT NULL,
  PRIMARY KEY (`stid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stid`, `did`, `limit`) VALUES
(1, 9, 80);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `phone_no` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `location` varchar(20) NOT NULL,
  `due_ksh` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`sid`, `name`, `phone_no`, `email`, `location`, `due_ksh`) VALUES
(1, 'JayDee Friendz', '02178327', 'asdsah@sajbdkas.com', '123 Street', 0),
(3, 'Momo', '0723904238', 'momo@girl.com', 'Nairobi', 0),
(4, 'Amos Pharm', '0735262621', 'gog@gui.com', 'Nakuru', 0);

-- --------------------------------------------------------

--
-- Table structure for table `supplier_stock`
--

CREATE TABLE IF NOT EXISTS `supplier_stock` (
  `ssid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `did` int(11) NOT NULL,
  `cost` double NOT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `supplier_stock`
--

INSERT INTO `supplier_stock` (`ssid`, `sid`, `did`, `cost`) VALUES
(1, 1, 9, 70),
(2, 1, 6, 60),
(3, 4, 9, 700);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `usertype` varchar(20) NOT NULL DEFAULT 'admin' COMMENT 'Could be admin or user',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `password`, `usertype`) VALUES
(1, 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin'),
(2, 'user', 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'user');

-- --------------------------------------------------------

--
-- Structure for view `drug_constants`
--
DROP TABLE IF EXISTS `drug_constants`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `drug_constants` AS select `d`.`name` AS `name`,`d`.`code` AS `code`,`s`.`limit` AS `limit`,(sum(`i`.`qty`) - sum(`p`.`qty`)) AS `qty` from (((`pos` `p` join `stock` `s`) join `inventory` `i`) join `drugs` `d`) where ((`d`.`did` = `s`.`did`) and (`p`.`did` = `i`.`did`) and (`i`.`did` = `d`.`did`) and (`p`.`did` = `d`.`did`)) order by `d`.`name`;
