-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2024 at 10:07 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blog_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `email` varchar(320) NOT NULL,
  `body` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `post_id`, `email`, `body`) VALUES
(1, 3, 'dsihle875@gmail.com', 'This is the body for the blog comment which was created'),
(2, 3, 'themba@gmail.com', 'This is the body for the blog comment which wwas created by user 4'),
(4, 3, 'andisiwe@gmail.com', 'this is another comment'),
(29, 3, 'dsihle875@gmail.com', 'This is the body for the blog comment which was created'),
(30, 3, 'healthy@reader.com', 'Great insights on the benefits of a healthy diet!'),
(32, 14, 'hiker@explorer.net', 'I found this guide on hiking trails very helpful. Thanks!'),
(33, 15, 'business@strategist.com', 'The strategies mentioned here are very effective. Great article!'),
(35, 7, 'culture@enthusiast.co', 'I loved reading about the Swazi ceremonies. Very insightful!'),
(36, 8, 'python@coder.io', 'Python programming has never been this easy to understand. Great guide!'),
(37, 9, 'fashion@trendy.com', 'Fashion trends are always changing. This article is very helpful.'),
(39, 11, 'worklife@balance.org', 'Balancing work and life is crucial. Great tips in this article!'),
(41, 3, 'yuri@gmail.com', 'This is a comment for comment'),
(42, 3, 'dalubuhle@gmail.com', 'This is the body for the blog comment which was created 8/6/2024');

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `body` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`id`, `user_id`, `title`, `body`) VALUES
(3, 3, 'Test 2', 'This is the body for the blog post which wwas created by user 123'),
(7, 12, 'Exploring Eswatini', 'Exploring the beautiful landscapes of Eswatini and its rich cultural heritage.'),
(8, 3, 'Mastering Java', 'Tips and tricks for mastering Java programming for beginners.'),
(9, 11, 'Hiking Trails Guide', 'A comprehensive guide to hiking trails around Mbabane.'),
(11, 3, 'Tech in Education', 'The impact of technology on modern education systems.'),
(14, 3, 'Healthy Living', 'Exploring the benefits of a healthy diet and regular exercise.'),
(15, 11, 'Swazi Ceremonies', 'Understanding the cultural significance of traditional Swazi ceremonies.'),
(17, 13, 'Fashion Trends', 'The latest trends in fashion and how to incorporate them into your wardrobe.'),
(19, 12, 'Tech and Communication', 'The role of technology in enhancing communication and collaboration.'),
(20, 13, 'Work-Life Balance', 'Tips for balancing work and personal life to achieve better well-being.');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(320) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `email`, `address`, `phone`) VALUES
(3, 'Themba Gugulethu Zwane', 'Thebza', 'themba@gmail.com', 'Madonsa Manzini', '+268 7855 3578'),
(7, 'Andisiwe Msibi', 'Andy', 'andisiwe@mva.org', 'Mbangweni Mbabane', '+268 7604 9373'),
(8, 'Sibusiso Dlamini', 'Sbu', 'sibusiso@company.org', 'Somhlolo Avenue, Mbabane', '+268 7612 1234'),
(9, 'Ntokozo Simelane', 'Ntokozo', 'ntokozo@business.com', 'Central Park, Manzini', '+268 7623 4567'),
(10, 'Phindile Magagula', 'Phindile', 'phindile@services.co', 'Gables, Ezulwini', '+268 7634 7890'),
(11, 'Mfundo Mhlanga', 'Mfundo', 'mfundo@tech.io', 'Mountain View, Mbabane', '+268 7645 1122'),
(12, 'Nomcebo Hlophe', 'Nomcebo', 'nomcebo@logistics.net', 'Matsapha Industrial Area', '+268 7656 3344'),
(13, 'Mpho Maseko', 'Mpho', 'mpho@finance.com', 'Mbuluzi, Simunye', '+268 7667 5566'),
(14, 'Lungile Khumalo', 'Lungile', 'lungile@consultancy.org', 'Malagwane, Mbabane', '+268 7678 7788'),
(15, 'Sipho Nxumalo', 'Sipho', 'sipho@developer.io', 'Nhlangano Main Road', '+268 7689 9900'),
(16, 'Jabu Ndlovu', 'Jabu', 'jabu@enterprise.com', 'Ocean Drive, Durban', '+27 82 123 4567'),
(17, 'Thandi Motsa', 'Thandi', 'thandi@global.org', 'Victoria Street, Manzini', '+268 7612 2345'),
(18, 'Carlos Mendez', 'Carlos', 'carlos@services.mz', 'Maputo Road, Maputo', '+258 84 567 8901'),
(19, 'Nomvula Shongwe', 'Nomvula', 'nomvula@innovation.net', 'Kingsway Road, Mbabane', '+268 7645 6789'),
(20, 'Dineo Moeketsi', 'Dineo', 'dineo@consulting.org', 'Long Street, Cape Town', '+27 71 234 5678'),
(21, 'Tendai Chipo', 'Tendai', 'tendai@business.na', 'Independence Avenue, Windhoek', '+264 81 345 6789'),
(22, 'Kagiso Pheko', 'Kagiso', 'kagiso@enterprise.bw', 'Mandela Street, Gaborone', '+267 74 456 7890'),
(23, 'Chola Mwamba', 'Chola', 'chola@finance.zm', 'Lusaka Street, Lusaka', '+260 97 123 4567'),
(24, 'Tinashe Nyathi', 'Tinashe', 'tinashe@tech.co.zw', 'Nelson Mandela Avenue, Harare', '+263 71 234 5678'),
(25, 'Mbabazi Kato', 'Mbabazi', 'mbabazi@global.co.ug', 'Kampala Road, Kampala', '+256 77 345 6789'),
(27, 'Nyosi Manyatsi', 'Nyosi', 'nyosi.manyatsi@gmail.com', 'Mbabane Mbangweni', '+268 7814 1896'),
(28, 'Nyosi Manyatsi', 'Nyosi', 'nyosi.manyatsi@gmail.com', 'Mbabane Mbangweni', '+268 7814 1896'),
(29, 'Nyosi Manyatsi', 'Nyosi', 'nyosi.manyatsi@gmail.com', 'Mbabane Mbangweni', '+268 7814 1896');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `postId` (`post_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

--
-- Constraints for table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
