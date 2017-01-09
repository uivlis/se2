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
-- Table structure for table `perioada_zi`
--

DROP TABLE IF EXISTS `perioada_zi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perioada_zi` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descriere` longtext NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perioada_zi`
--

LOCK TABLES `perioada_zi` WRITE;
/*!40000 ALTER TABLE `perioada_zi` DISABLE KEYS */;
INSERT INTO `perioada_zi` VALUES (1,'0:00'),(2,'0:15'),(3,'0:30'),(4,'0:45'),(5,'1:00'),(6,'1:15'),(7,'1:30'),(8,'1:45'),(9,'2:00'),(10,'2:15'),(11,'2:30'),(12,'2:45'),(13,'3:00'),(14,'3:15'),(15,'3:30'),(16,'3:45'),(17,'4:00'),(18,'4:15'),(19,'4:30'),(20,'4:45'),(21,'5:00'),(22,'5:15'),(23,'5:30'),(24,'5:45'),(25,'6:00'),(26,'6:15'),(27,'6:30'),(28,'6:45'),(29,'7:00'),(30,'7:15'),(31,'7:30'),(32,'7:45'),(33,'8:00'),(34,'8:15'),(35,'8:30'),(36,'8:45'),(37,'9:00'),(38,'9:15'),(39,'9:30'),(40,'9:45'),(41,'10:00'),(42,'10:15'),(43,'10:30'),(44,'10:45'),(45,'11:00'),(46,'11:15'),(47,'11:30'),(48,'11:45'),(49,'12:00'),(50,'12:15'),(51,'12:30'),(52,'12:45'),(53,'13:00'),(54,'13:15'),(55,'13:30'),(56,'13:45'),(57,'14:00'),(58,'14:15'),(59,'14:30'),(60,'14:45'),(61,'15:00'),(62,'15:15'),(63,'15:30'),(64,'15:45'),(65,'16:00'),(66,'16:15'),(67,'16:30'),(68,'16:45'),(69,'17:00'),(70,'17:15'),(71,'17:30'),(72,'17:45'),(73,'18:00'),(74,'18:15'),(75,'18:30'),(76,'18:45'),(77,'19:00'),(78,'19:15'),(79,'19:30'),(80,'19:45'),(81,'20:00'),(82,'20:15'),(83,'20:30'),(84,'20:45'),(85,'21:00'),(86,'21:15'),(87,'21:30'),(88,'21:45'),(89,'22:00'),(90,'22:15'),(91,'22:30'),(92,'22:45'),(93,'23:00'),(94,'23:15'),(95,'23:30'),(96,'23:45');
/*!40000 ALTER TABLE `perioada_zi` ENABLE KEYS */;
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
