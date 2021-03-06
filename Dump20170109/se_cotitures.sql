-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: se
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `cotitures`
--

DROP TABLE IF EXISTS `cotitures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cotitures` (
  `Unique_cotiture_id` int(11) NOT NULL AUTO_INCREMENT,
  `Latitudine_GPS` double NOT NULL,
  `Longitudine_GPS` double NOT NULL,
  PRIMARY KEY (`Unique_cotiture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cotitures`
--

LOCK TABLES `cotitures` WRITE;
/*!40000 ALTER TABLE `cotitures` DISABLE KEYS */;
INSERT INTO `cotitures` VALUES (1,46.764357,23.564094),(2,46.772006,23.583764),(3,46.768956,23.585393),(4,46.769989,23.574248),(5,46.764563,23.575723),(6,46.765191,23.576434),(7,46.766866,23.581101),(8,46.765732,23.581877),(9,46.76756,23.575863),(10,46.768434,23.57937),(11,46.772116,23.582599),(12,46.7736,23.588027),(13,46.763246,23.572853),(14,46.766674,23.58498),(15,46.76908,23.571239),(16,46.767017,23.568922),(17,46.76605,23.566555),(18,46.769154,23.581149),(19,46.769529,23.583036),(20,46.770101,23.583142);
/*!40000 ALTER TABLE `cotitures` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-09 19:36:37
