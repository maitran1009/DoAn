-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pizza
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_pass` varchar(150) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `address` varchar(150) NOT NULL,
  `forget_code` varchar(45) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `status` int NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'mai123@gmail.com','$2a$10$gKZkmsw1lAOHiGSLHXVU.OFe6VwCJtVJSuBreQEoWzR2gRXmHxM8S','maimai','0868257328','hồ chí minh',NULL,'2021-02-25 21:44:07',1,1),(4,'mai1234@gmail.com','$2a$10$gKZkmsw1lAOHiGSLHXVU.OFe6VwCJtVJSuBreQEoWzR2gRXmHxM8S','maimai','0868257328','hồ chí minh',NULL,'2021-02-27 12:41:59',1,2),(5,'maisibun.nhs1009@gmail.com','$2a$12$ZE28tD7BAVuTAZEdCNn.iO9XAuh.7T.YbTaH8iVqcAT7H87YS7hpO','maimai','0868257328','hồ chí minh',NULL,'2021-02-27 17:32:08',1,2),(6,'giang123@gmail.com','$2a$12$deBxb8h9U1uWtW0ymf3zjOMrQEsJGjEqjrlnMKZ//LyD74le8rPIW','Giang','0868257328','hồ chí minh',NULL,'2021-02-28 21:08:30',1,2),(7,'maisibun.nhs109@gmail.com','$2a$12$hOFX2OEojbFaM43wvM6kte6GpSINjcv82M6LFO2FPfDhcHUR97WMO','maimai','0868257328','hồ chí minh',NULL,'2021-02-28 21:31:04',1,2),(8,'gggsa@gmail.com','$2a$12$7VGH2wmdXfldUh4I5mmy0e0aA3RWr3/qW9X.SUN8ETOR/4sshzhM6','maimai','0868257328','hồ chí minh',NULL,'2021-03-14 21:47:48',1,2),(9,'hanoi123@gmail.com','$2a$12$C7KQMc6m4JWAdAu19GXw.OA1FQAYiTJlTtisKVtrCUxLi9DADGsRu','maimai','0868257328','hồ chí minh',NULL,'2021-03-19 22:01:45',1,2),(10,'gggsa1@gmail.com','$2a$12$Cw3x7QebLV0rf1.YkD5Ase3tuMv95NHYxMxPTKTyBxuqkxm41TTFa','maimai','0868257328','hồ chí minh',NULL,'2021-03-26 15:48:54',1,2),(12,'tranmai123@gmail.com','$2a$12$RE3S6zi3JkC1MhODalObyOHX1UTgklAR1kjA.AVz9X/eHYBSrUUu2','maimai','0868257328','Hà Nội',NULL,'2021-03-26 18:14:41',1,2),(13,'xuan123@gmail.com','$2a$12$XU3bKAcR/BA1eiV2AadCSeNwVtTy/3TBN71DWkJLArSrsL.7eR1Oy','maimai','0868257328','hồ chí minh',NULL,'2021-03-27 10:25:18',1,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-15 23:29:01
