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
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `product_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`),
  KEY `product_detail_idx` (`product_detail_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `product_detail` FOREIGN KEY (`product_detail_id`) REFERENCES `product_detail` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,0,42),(2,1,0,43),(3,1,0,44),(4,2,2,39),(5,2,2,40),(6,3,0,33),(7,3,0,34),(8,3,0,35),(9,4,1,45),(10,6,1,15),(11,7,2,42),(12,7,2,43),(13,8,3,51),(14,8,3,52),(15,8,3,53),(16,9,3,15),(17,9,3,16),(18,9,3,17),(19,10,3,39),(20,10,3,40),(21,10,3,41),(22,11,2,9),(23,11,2,10),(24,12,2,9),(25,12,2,10),(26,13,3,42),(27,13,3,43),(28,13,3,44),(29,14,2,33),(30,14,2,34),(31,15,2,33),(32,15,2,34),(33,16,3,36),(34,16,3,37),(35,16,3,38),(36,17,3,1),(37,17,3,2),(38,17,3,3),(39,18,3,39),(40,18,3,40),(41,18,3,41),(42,19,2,30),(43,19,2,31),(44,20,2,27),(45,20,2,28),(46,21,1,30),(47,22,1,30),(48,23,1,39),(49,24,2,51),(50,24,2,52),(51,25,2,51),(52,25,2,52),(53,26,1,48),(54,27,3,9),(55,27,3,10),(56,27,3,11),(57,28,2,45),(58,28,2,46),(59,29,3,48),(60,29,3,49),(61,29,3,50),(62,30,3,12),(63,30,3,13),(64,30,3,14),(65,31,3,9),(66,31,3,10),(67,31,3,11),(68,32,1,27),(69,33,1,36),(70,34,1,15),(71,35,1,6),(72,36,2,39),(73,36,2,40),(74,37,1,45),(75,38,1,45),(76,39,2,6),(77,39,2,7),(78,40,3,24),(79,40,3,25),(80,40,3,26),(81,41,2,21),(82,41,2,22),(83,42,1,33),(84,43,2,4),(85,43,2,5),(86,44,1,1),(87,45,1,4),(88,46,2,33),(89,46,2,34),(90,47,3,36),(91,47,3,37),(92,47,3,38),(93,48,3,36),(94,48,3,37),(95,48,3,38),(96,49,2,1),(97,49,2,2),(98,50,3,1),(99,50,3,2),(100,50,3,3),(101,51,2,21),(102,51,2,22),(103,52,2,18),(104,52,2,19),(105,53,2,6),(106,53,2,7),(107,54,1,45),(108,55,3,15),(109,55,3,16),(110,55,3,17),(111,56,1,39),(112,57,2,48),(113,57,2,49),(114,58,3,18),(115,58,3,19),(116,58,3,20),(117,59,2,21),(118,59,2,22),(119,60,1,27),(120,61,2,4),(121,61,2,5),(122,62,3,9),(123,62,3,10),(124,62,3,11),(125,63,3,36),(126,63,3,37),(127,63,3,38),(128,64,2,39),(129,64,2,40),(130,65,1,4),(131,66,2,4),(132,66,2,5),(133,67,1,12),(134,68,2,1),(135,68,2,2),(136,69,2,48),(137,69,2,49),(138,70,3,27),(139,70,3,28),(140,70,3,29),(141,71,2,18),(142,71,2,19),(143,72,2,48),(144,72,2,49),(145,73,2,21),(146,73,2,22),(147,74,3,18),(148,74,3,19),(149,74,3,20),(150,75,3,39),(151,75,3,40),(152,75,3,41),(153,76,2,30),(154,76,2,31),(155,77,1,27),(156,78,3,9),(157,78,3,10),(158,78,3,11),(159,79,2,27),(160,79,2,28),(161,80,3,42),(162,80,3,43),(163,80,3,44),(164,81,3,12),(165,81,3,13),(166,81,3,14),(167,82,1,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-27 16:30:55