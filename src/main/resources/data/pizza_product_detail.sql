-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pizza
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product_detail`
--

DROP TABLE IF EXISTS `product_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_detail` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `size_id` int NOT NULL,
  `product_id` int NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `product_id` (`product_id`),
  KEY `size_id` (`size_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`ID`),
  CONSTRAINT `size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_detail`
--

LOCK TABLES `product_detail` WRITE;
/*!40000 ALTER TABLE `product_detail` DISABLE KEYS */;
INSERT INTO `product_detail` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,1),(4,1,2,1),(5,2,2,1),(6,1,3,1),(7,2,3,1),(8,3,3,1),(9,1,4,1),(10,2,4,2),(11,3,4,1),(12,1,5,2),(13,2,5,1),(14,3,5,1),(15,1,6,1),(16,2,6,1),(17,3,6,1),(18,1,7,1),(19,2,7,1),(20,3,7,2),(21,1,8,1),(22,2,8,2),(23,3,8,1),(24,1,9,1),(25,2,9,1),(26,3,9,1),(27,1,10,1),(28,2,10,1),(29,3,10,1),(30,1,11,1),(31,2,11,1),(32,3,11,1),(33,1,12,1),(34,2,12,1),(35,3,12,1),(36,1,13,1),(37,2,13,1),(38,3,13,1),(39,1,14,1),(40,2,14,1),(41,3,14,1),(42,1,15,1),(43,2,15,1),(44,3,15,1),(45,1,16,1),(46,2,16,1),(47,3,16,1),(48,1,17,1),(49,2,17,1),(50,3,17,1),(51,1,18,1),(52,2,18,2),(53,3,18,2);
/*!40000 ALTER TABLE `product_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-27 16:30:53
