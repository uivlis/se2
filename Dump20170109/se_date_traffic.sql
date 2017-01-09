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
-- Table structure for table `date_traffic`
--

DROP TABLE IF EXISTS `date_traffic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `date_traffic` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Id_strada` int(11) NOT NULL,
  `Data_ora` datetime(6) NOT NULL,
  `Id_cond_meteo` int(11) NOT NULL,
  `Id_perioada_zi` int(11) NOT NULL,
  `Nr_mediu_autovehicule` double NOT NULL,
  `Viteza_medie_autovehicule` double NOT NULL,
  `Id_starea_drumului` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_to_cond_meteo_idx` (`Id_cond_meteo`),
  KEY `FK_to_perioada_zi_idx` (`Id_perioada_zi`),
  KEY `FK_to_starea_drumului_idx` (`Id_starea_drumului`),
  KEY `FK_to_strazi_idx` (`Id_strada`),
  CONSTRAINT `FK_to_cond_meteo` FOREIGN KEY (`Id_cond_meteo`) REFERENCES `cond_meteo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_to_perioada_zi` FOREIGN KEY (`Id_perioada_zi`) REFERENCES `perioada_zi` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_to_starea_drumului` FOREIGN KEY (`Id_starea_drumului`) REFERENCES `starea_drumului` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_to_strazi` FOREIGN KEY (`Id_strada`) REFERENCES `strazi` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date_traffic`
--

LOCK TABLES `date_traffic` WRITE;
/*!40000 ALTER TABLE `date_traffic` DISABLE KEYS */;
INSERT INTO `date_traffic` VALUES (1,1,'2016-11-12 17:15:00.000000',2,17,20,40,1),(2,3,'2016-11-03 12:34:00.000000',2,12,12,37,1),(3,4,'2016-11-09 04:09:00.000000',3,4,10,50,2),(7,1,'2017-01-04 12:56:37.261000',6,52,10,30,1),(8,1,'2017-01-04 14:41:07.700000',8,70,10,40,1),(9,3,'2017-01-04 14:41:57.399000',4,70,20,30,1);
/*!40000 ALTER TABLE `date_traffic` ENABLE KEYS */;
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
