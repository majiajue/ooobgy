CREATE DATABASE  IF NOT EXISTS `hrsys` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hrsys`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: hrsys
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES (1,'Engineer'),(2,'Skilled Engineer'),(3,'Seller'),(4,'Skilled Seller'),(5,'Market Hunter');
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT NULL,
  `staff` int(11) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin`
--

LOCK TABLES `checkin` WRITE;
/*!40000 ALTER TABLE `checkin` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `staff_login`
--

DROP TABLE IF EXISTS `staff_login`;
/*!50001 DROP VIEW IF EXISTS `staff_login`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `staff_login` (
  `id` int(11),
  `name` text,
  `login` varchar(45),
  `psw` varchar(45)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Manager'),(2,'Staff'),(3,'Intern');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domainl1`
--

DROP TABLE IF EXISTS `domainl1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domainl1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `leader` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domainl1`
--

LOCK TABLES `domainl1` WRITE;
/*!40000 ALTER TABLE `domainl1` DISABLE KEYS */;
INSERT INTO `domainl1` VALUES (1,'Main Inc. Company',1),(2,'Beijing',2),(3,'Shanghai',3);
/*!40000 ALTER TABLE `domainl1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domainl2`
--

DROP TABLE IF EXISTS `domainl2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domainl2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `name` text,
  `leader` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domainl2`
--

LOCK TABLES `domainl2` WRITE;
/*!40000 ALTER TABLE `domainl2` DISABLE KEYS */;
INSERT INTO `domainl2` VALUES (1,1,'CEO Office',1),(2,1,'BI Office',1),(3,2,'IT',3),(4,2,'Market',4);
/*!40000 ALTER TABLE `domainl2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domainl3`
--

DROP TABLE IF EXISTS `domainl3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domainl3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `name` text,
  `leader` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domainl3`
--

LOCK TABLES `domainl3` WRITE;
/*!40000 ALTER TABLE `domainl3` DISABLE KEYS */;
INSERT INTO `domainl3` VALUES (1,4,'Phone Market',5),(2,4,'Computer Market',6),(3,4,'Printer Market',7);
/*!40000 ALTER TABLE `domainl3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT NULL,
  `staff` int(11) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `staff_detail`
--

DROP TABLE IF EXISTS `staff_detail`;
/*!50001 DROP VIEW IF EXISTS `staff_detail`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `staff_detail` (
  `id` int(11),
  `name` text,
  `login` varchar(45),
  `psw` varchar(45),
  `domainl1` int(11),
  `domainl1_name` text,
  `domainl2` int(11),
  `domainl2_name` text,
  `domainl3` int(11),
  `domainl3_name` text,
  `email` text,
  `role` int(11),
  `role_name` text,
  `level` int(11),
  `level_name` text,
  `salary` decimal(10,0),
  `location` text,
  `phone` varchar(45),
  `gender` tinyint(1),
  `age` int(11),
  `checkin` datetime
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `login` varchar(45) DEFAULT NULL,
  `psw` varchar(45) DEFAULT NULL,
  `domainl1` int(11) DEFAULT NULL,
  `domainl2` int(11) DEFAULT NULL,
  `domainl3` int(11) DEFAULT NULL,
  `email` text,
  `role` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `salary` decimal(10,0) DEFAULT NULL,
  `location` text,
  `phone` varchar(45) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `checkin` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'魅月痕','ooobgy','ooo',1,NULL,NULL,'ooobgy@hr.com',1,1,120000,'Beijing','123456',1,23,NULL),(2,'Nori','nori','ooo',1,1,NULL,'nori@nav.com',2,1,6000,'Beijing','123457',0,21,NULL),(3,'Xizho','xizho','ooo',1,2,NULL,'xizho@nav.com',1,2,12000,'Shanghai','124568',1,20,NULL),(4,'Mokey','mok','ooo',1,2,NULL,'xizho@nav.com',1,2,12000,'Shanghai','124568',1,20,'2012-04-23 10:54:02'),(5,'李小四','lee4','ooo',2,3,NULL,'lio@nav.com',2,2,2000,'Shanghai','124568',1,31,'2012-04-23 10:54:02'),(6,'张明','zmmm','ooo',2,4,NULL,'zm@nav.com',3,3,2000,'Shanghai','124568',0,20,'2012-04-23 10:54:02'),(7,'王红','whong','ooo',2,4,1,'whong@nav.com',1,5,12000,'Beijing','124568',0,28,'2012-04-23 10:54:02'),(8,'Stacky','sta','ooo',2,4,2,'sta@nav.com',2,4,8795,'Shanghai','124568',1,21,'2012-04-23 10:54:02'),(9,'张阳','zyy','ooo',2,4,3,'zyy@nav.com',2,5,12000,'Shanghai','15474',1,28,'2012-04-23 10:54:02');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `staff_login`
--

/*!50001 DROP TABLE IF EXISTS `staff_login`*/;
/*!50001 DROP VIEW IF EXISTS `staff_login`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `staff_login` AS select `t1`.`id` AS `id`,`t1`.`name` AS `name`,`t1`.`login` AS `login`,`t1`.`psw` AS `psw` from `staff` `t1` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `staff_detail`
--

/*!50001 DROP TABLE IF EXISTS `staff_detail`*/;
/*!50001 DROP VIEW IF EXISTS `staff_detail`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `staff_detail` AS select `t1`.`id` AS `id`,`t1`.`name` AS `name`,`t1`.`login` AS `login`,`t1`.`psw` AS `psw`,`t1`.`domainl1` AS `domainl1`,(select `domainl1`.`name` from `domainl1` where (`domainl1`.`id` = `t1`.`domainl1`)) AS `domainl1_name`,`t1`.`domainl2` AS `domainl2`,(select `domainl2`.`name` from `domainl2` where (`domainl2`.`id` = `t1`.`domainl2`)) AS `domainl2_name`,`t1`.`domainl3` AS `domainl3`,(select `domainl3`.`name` from `domainl3` where (`domainl3`.`id` = `t1`.`domainl3`)) AS `domainl3_name`,`t1`.`email` AS `email`,`t1`.`role` AS `role`,(select `role`.`name` from `role` where (`role`.`id` = `t1`.`role`)) AS `role_name`,`t1`.`level` AS `level`,(select `level`.`name` from `level` where (`level`.`id` = `t1`.`level`)) AS `level_name`,`t1`.`salary` AS `salary`,`t1`.`location` AS `location`,`t1`.`phone` AS `phone`,`t1`.`gender` AS `gender`,`t1`.`age` AS `age`,`t1`.`checkin` AS `checkin` from `staff` `t1` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-04-23 10:57:52
