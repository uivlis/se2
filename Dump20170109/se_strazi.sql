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
-- Table structure for table `strazi`
--

DROP TABLE IF EXISTS `strazi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `strazi` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Localitatea` longtext NOT NULL,
  `Strada` longtext NOT NULL,
  `Tara` longtext NOT NULL,
  `Nr_benzi_circulatie` int(11) NOT NULL,
  `Start_Latitudine_GPS` double DEFAULT NULL,
  `Start_Longitudine_GPS` double DEFAULT NULL,
  `Stop_Latitudine_GPS` double DEFAULT NULL,
  `Stop_Longitudine_GPS` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strazi`
--

LOCK TABLES `strazi` WRITE;
/*!40000 ALTER TABLE `strazi` DISABLE KEYS */;
INSERT INTO `strazi` VALUES (0,'Cluj-Napoca','endOfWorld','Romania',0,0,0,0,0),(1,'Cluj-Napoca','Emil Isac','Romania',1,46.769059,23.585388,46.772006,23.583764),(2,'Cluj-Napoca','Splaiul Independentei','Romania',1,46.764357,23.564094,46.772116,23.582599),(3,'Cluj-Napoca','George Baritiu','Romania',2,46.7736,23.588027,46.771803,23.584526),(4,'Cluj-Napoca','Motilor','Romania',2,46.768956,23.585393,46.763246,23.572853),(5,'Cluj-Napoca','Clinicilor','Romania',1,46.766674,23.58498,46.764563,23.575723),(6,'Cluj-Napoca','George Cosbuc','Romania',2,46.765191,23.576434,46.769989,23.574248),(7,'Cluj-Napoca','Iuliu Hossu','Romania',1,46.771454,23.584186,46.76756,23.575863),(8,'Cluj-Napoca','Mihai Eminescu','Romania',1,46.766866,23.581101,46.768434,23.57937),(9,'Cluj-Napoca','Iuliu Hatieganu','Romania',1,46.76676,23.581255,46.765732,23.581877);
/*!40000 ALTER TABLE `strazi` ENABLE KEYS */;
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
