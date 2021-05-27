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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `image` text NOT NULL,
  `price` int NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Pizza Bánh Xèo Nhật','/images/Hình+product+gắn+tag+NEW-01.jpg',89000,'Hương vị thơm ngon'),(2,'Pizza Gà Phô Mai Thịt Heo Xông Khói','/images/Cheesy-chicken-bacon.jpg',69000,'Ngon tuyệt'),(3,'Pizza Hải Sản Xốt Mayonnaise','/images/Ocean-mania.jpg',90000,'Ngon tuyệt'),(4,'Pizza Hải Sản Nhiệt Đới  Xốt Tiêu','/images/Pizzaminsea.jpg',80000,'Ngon tuyệt'),(5,'Pizza Bò & Tôm Nướng Kiểu Mỹ','/images/Surf-_-turf.jpg',85000,'Ngon tuyệt'),(6,'Half-Half','/images/Haft-haft.jpg',160000,'Ngon tuyệt'),(7,'Pizza 5 Loại Thịt Thượng Hạng','/images/Meat-lover.jpg',90000,'Ngon tuyệt'),(8,'Pizza Xúc Xích Ý Truyền Thống','/images/Pepperoni-feast-.jpg',90000,'Ngon tuyệt ngon tuyệt'),(9,'Pizza Gà Xốt Tương Kiểu Nhật','/images/Teriyaki-chicken.jpg',80000,'Ngon tuyệt'),(10,'Pizza Thập Cẩm Thượng Hạng','/images/Extravaganza.jpg',90000,'Ngon tuyệt'),(11,'Pizza Trứng Cút Xốt Phô Mai','/images/Kid-mania.jpg',60000,'Ngon tuyệt'),(12,'Pizza Gà Phô Mai Thịt Heo Xông Khói','/images/Cheesy-chicken-bacon.jpg',70000,'Ngon tuyệt'),(13,'Pizza Rau Củ Thập Cẩm','/images/Veggie-mania.jpg',69000,'Ngon tuyệt'),(14,'Pizza Dăm Bông Dứa Kiểu Hawaii','/images/Hawaiian.jpg',69000,'Ngon tuyệt'),(15,'Pizza Phô Mai Hảo Hạng','/images/Cheese-mania.jpg',69000,'Ngon tuyệt'),(16,'Pizza Bò Mê-Hi-Cô Thượng Hạng','/images/Prime-beef.jpg',230000,'Ngon tuyệt'),(17,'Pizza Hải Sản Kiểu Singapore','/images/Singapore-style-seafood.jpg',220000,'Ngon tuyệt'),(18,'Pizza 4 Vị','/images/4P.jpg',300000,'Ngon tuyệt');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-27 16:30:54
