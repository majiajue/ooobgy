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

-- Dump completed on 2012-04-22 21:14:41
