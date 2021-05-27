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
  `ward` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'mai123@gmail.com','$2a$12$w7T6HiSc/BsFKlpoCi0cUuBjm.LNEiaJEStmG2d1R0K/CvLuPZzQG','Trần Thị Mai','0976654867','số 64 ngõ 10 Phường Phú Diễn Quận Bắc Từ Liêm Hà Nội',NULL,'2021-05-27 14:27:52',1,1,238),(2,'nguyenthinhuquynh@gmail.com','$2a$12$mC6Jlm1kV39A9ofgCp5d/efa0rG6QfV5Dfbd0EauImiP1QzvgxeiO','Nguyễn Thị Như Quỳnh','0983536364','Phường Hàng Buồm Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:44',1,2,18),(3,'lehoangquan@gmail.com','$2a$12$0OdpdyP2QPMMqf4UBzMgWu1JK9dWBTfU5dneLJ1a1.m93KA.kmvLi','Lê Hoàng Quân','0981263326','Phường Văn Miếu Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:44',1,2,106),(4,'inhvanphuong@gmail.com','$2a$12$C54yzpBb68AiXSwsGhx6Y.HWI1LBY.jaHcWAVXpUKhZ1GkUBOSZPq','Đinh Văn Phượng','0986563670','Phường Dịch Vọng Hậu Quận Cầu Giấy Hà Nội',NULL,'2021-05-27 14:28:45',1,2,101),(5,'nguyenxuansang@gmail.com','$2a$12$H8OQIrnqElETvOrxcIcREOZ8Uw3KRo/XdjnTWZpamJUolPKEsdizu','Nguyễn Xuân Sang','0986594588','Phường Ngã Tư Sở Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:45',1,2,44),(6,'lephuquy@gmail.com','$2a$12$i4tLPOAUJs8lBPMxuVVmWupo3xqhy04c3xb.h4RWIQaTPqNdnoV5e','Lê Phú Quý','0988822093','Phường Yên Phụ Quận Tây Hồ Hà Nội',NULL,'2021-05-27 14:28:45',1,2,38),(7,'lyquocquyen@gmail.com','$2a$12$8vcuauMYup4lpoWPf3rFbucT8IeAu0ovblyDzKhC0kMcWG0bJnw2m','Lý Quốc Quyền','0984825421','Phường Thành Công Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:45',1,2,14),(8,'buiminhquan@gmail.com','$2a$12$Q/LiuHGD45UptrXYnA92Oewb0EOSPg2Xa0vuMLP6iq4Ys5NE8h8GS','Bùi Minh Quân','0984368307','Phường Thanh Nhàn Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:46',1,2,56),(9,'nguyenngocson@gmail.com','$2a$12$TuOeGSfGKkMdvCRlEwFazudTxE3VGRVMtob5DAhxs8NIdWR1vBIx6','Nguyễn Ngọc Sơn','0981287043','Phường Trần Hưng Đạo Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:46',1,2,30),(10,'buiduyquy@gmail.com','$2a$12$APzm5SPz.aBazCyxgjQodutIRzqlpICzkc.ScASaXjGBo.5MjGjxa','Bùi Duy Qúy','0989178861','Phường Láng Thượng Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:46',1,2,108),(11,'vohoangphuong@gmail.com','$2a$12$M1TQ02VxITC9quX2fbUZu.twD0PtbZsVwl6kBJ.vO6APRFVOGYO6m','Võ Hoàng Phương','0988540463','Phường Hàng Trống Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:47',1,2,26),(12,'tranminhphuong@gmail.com','$2a$12$0MeXgDm8DQx/SmRZQw3IxeEnTJjlDJBUcA.8l.xOvsSJaoeqZgBSa','Trần Minh Phương','0983307776','Phường Láng Thượng Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:47',1,2,108),(13,'phanduyquoc@gmail.com','$2a$12$QGQN8XSXznel5klb7khgcOIkzpVU0Zq4JCfo1KUowyHx.GBXFbx8O','Phan Duy Quốc','0981056638','Phường Hàng Bông Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:47',1,2,28),(14,'nguyenvungocquyen@gmail.com','$2a$12$UNiOBRjpQKODK.F6zh402.x0Bz5lPmAYPpXgl7rZEJGv3WS/.ZqYe','Nguyễn Vũ Ngọc Quyên','0980083460','Phường Thượng Thanh Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:48',1,2,83),(15,'truongtrongquan@gmail.com','$2a$12$Jxhmya9C/ensx3x4kOWx4uj1JM0KqXz6PtjYVfgj2YFID22dHCYlW','Trương Trọng Quân','0987149205','Phường Hàng Trống Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:48',1,2,26),(16,'phamminhquan@gmail.com','$2a$12$7F.AGDIfA2FgCuCEIx6bgO5K8IBSOP15QACiFCEyhBF2p4yVSXMUq','Phạm Minh Quân','0982974438','Phường Khương Thượng Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:48',1,2,45),(17,'vothanhsanh@gmail.com','$2a$12$Ja36JbkuzRRlivJKa7mCsukeGaVkpS9G5VOakGWeNYGmmqfwhRzw.','Võ Thanh Sanh','0982364902','Phường Quảng An Quận Tây Hồ Hà Nội',NULL,'2021-05-27 14:28:49',1,2,36),(18,'nguyenminhphuong@gmail.com','$2a$12$6M24AaUIeSwDo7Ks0i1w8O2CGJJVIfglgfTObyxs2WiOabGuJ1FMG','Nguyễn Minh Phương','0980985992','Phường Phương Liên Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:49',1,2,119),(19,'nguyenvietson@gmail.com','$2a$12$zCcOtNbsB735/SINr3443O7RA5mQxzxJ.I38JvLTrPfT66rQxwvbm','Nguyễn Viết Sơn','0984795783','Phường Sài Đồng Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:49',1,2,92),(20,'vuhoanghaison@gmail.com','$2a$12$ta1bFaWCx1NnZ29nOZ.NUeExckmMnvNeTb33iOjcbuD8k7m7wm1vC','Vũ Hoàng Hải Sơn','0985951779','Phường Đức Giang Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:50',1,2,86),(21,'nguyenhaison@gmail.com','$2a$12$RlTud9NZfa4NbKqDot9kg.N.c8E.0JVC5SGBo16gCfl7nkSIuL6Da','Nguyễn Hải Sơn','0984891999','Phường Thành Công Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:50',1,2,14),(22,'nguyenvutruongson@gmail.com','$2a$12$rg0l35rd6tvJUgFs6uhRLO6vneW90QI2a0xBcA.hl5k1KHVz95NdS','Nguyễn Vũ Trường Sơn','0986999388','Phường Quốc Tử Giám Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:50',1,2,107),(23,'tranminhsang@gmail.com','$2a$12$M3eehv0jZpbLyaFMCdqapO6489hr.5KTYOXJLkd9ejh80TpfDyofe','Trần Minh Sang','0982871784','Phường Phạm Đình Hổ Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:50',1,2,48),(24,'nguyentrungson@gmail.com','$2a$12$9CiZcaSIcAUAaMc3Cpx27OLwXFFIfD2P..jz98bSY5/TvlOCRD5Ze','Nguyễn Trung Sơn','0984868740','Phường Phúc Lợi Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:51',1,2,90),(25,'lequang@gmail.com','$2a$12$I1TNHI5qdUHz159DSp0uT.FNU7HQky1HO6w77osxYD.YDSpzIJ1hu','Lê Quang','0989625598','Phường Thanh Nhàn Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:51',1,2,56),(26,'vuthiphuong@gmail.com','$2a$12$kjdZfjRZXDFcxloRyOXghOnQdPlt0zTBd507hdNog.ilzkAWPSJKe','Vũ Thị Phương','0984060440','Phường Trần Hưng Đạo Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:51',1,2,30),(27,'nguyenvanson@gmail.com','$2a$12$j9V8683kVdRYVZ9qmhNLhOJD5mswmKVA7U2cE8MJ8GS/4IT0FOlFO','Nguyễn Văn Sơn','0980097948','Phường Quang Trung Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:52',1,2,117),(28,'leanhson@gmail.com','$2a$12$HOfstxmri4z27oNZRSc/DuhfoMKthdIkeQsXubJkxXTSIFXuHflQS','Lê Anh Sơn','0989968252','Phường Nghĩa Tân Quận Cầu Giấy Hà Nội',NULL,'2021-05-27 14:28:52',1,2,98),(29,'votiensi@gmail.com','$2a$12$NbeBQapBH4E9k8ydyYdKguaHItvnXFCi8yppWUQHMb.xjPM0pMuVu','Võ Tiến Sĩ','0981225357','Phường Nguyễn Trung Trực Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:52',1,2,6),(30,'phanucson@gmail.com','$2a$12$hi83d6wBxSh.HJ0geWg4tO5Ym5IbFsRSyLm6cQib2SnF5txWXcdRm','Phan Đức Sơn','0987903828','Phường Ngọc Khánh Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:53',1,2,11),(31,'trantheson@gmail.com','$2a$12$wDESRVc0Nd8iwW8Jgdw2e.3PfRMRKcjm6XK5DvIFdl6.5w3vxXGQC','Trần Thế Sơn','0983829546','Phường Phạm Đình Hổ Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:54',1,2,48),(32,'vuhongtam@gmail.com','$2a$12$noP5SiWD649Wml2kF8lts.mPOAGsq2yXxoWT8j6rO6fGpLeY5UwA.','Vũ Hồng Tâm','0983160543','Phường Bồ Đề Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:54',1,2,91),(33,'nguyenminhtan@gmail.com','$2a$12$5SfmFInFHczSz6j5nCDjM.m1YhQv8/6mFMjPTqkj6XQMuhgkTaR2i','Nguyễn Minh Tân','0984761805','Phường Nghĩa Tân Quận Cầu Giấy Hà Nội',NULL,'2021-05-27 14:28:54',1,2,98),(34,'lehaxuanthai@gmail.com','$2a$12$j73ptZrrls77brYAcvJu5.xjUuG0bcNzfASGBqT2Hu/AxHLL/jxhq','Lê Hà Xuân Thái','0982695115','Phường Đồng Tâm Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:54',1,2,59),(35,'nguyenngocphuong@gmail.com','$2a$12$Lcs3KNfhRUd91HwW0CBxkej6Xg/hgCUoVHa0nuZsuABG5iCvLenAu','Nguyễn Ngọc Phương','0985724554','Phường Bách Khoa Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:55',1,2,58),(36,'buianhtai@gmail.com','$2a$12$oMkkVa7ddFQqugzSQQIhcuEB3GwM7XpgoIpP.EsjUNusb/8DLAn2W','Bùi Anh Tài','0980826978','Phường Hàng Bạc Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:55',1,2,23),(37,'duongchitam@gmail.com','$2a$12$S7n//FZ1xX4AOOM3pB4AO.kiSvpdo6MD0lB.LK3AxjMHJFfOIYzH2','Dương Chí Tâm','0980152359','Phường Tứ Liên Quận Tây Hồ Hà Nội',NULL,'2021-05-27 14:28:55',1,2,35),(38,'phamthanhquoc@gmail.com','$2a$12$1r3vzE9Ehp8cqUUGXISQ4.eYpEucUfoCdWB.xrvmchj.4WPDVgB9S','Phạm Thanh Quốc','0983072794','Phường Trúc Bạch Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:56',1,2,2),(39,'letruongsa@gmail.com','$2a$12$dUosM14wBxSlUUfRDwSPj.oAnBjF8M9BVvCYTtMIj0uOyMyRLN27K','Lê Trường Sa','0980629917','Phường Láng Hạ Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:56',1,2,112),(40,'luunguyenquocson@gmail.com','$2a$12$WNumRoum7Eypxrf5sPTk5uwTp7LBXfDfgAghOweaPFUQSsd4k37Qa','Lưu Nguyễn Quốc Sơn','0983327246','Phường Hàng Mã Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:56',1,2,17),(41,'ngovusang@gmail.com','$2a$12$uSYj57Zl03.v8zlgVJWvSeibj3UDRtnU7DHJQZGk5mwQ9fwIkPEmK','Ngô Vũ Sang','0988323506','Phường Bưởi Quận Tây Hồ Hà Nội',NULL,'2021-05-27 14:28:57',1,2,39),(42,'lecongtai@gmail.com','$2a$12$hjUfMmJN4NWaeem3HNdXeewDAVKC1LA9UPSvFvIMpgJuo1AGam4i.','Lê Công Tài','0988777322','Phường Quốc Tử Giám Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:57',1,2,107),(43,'ominhtam@gmail.com','$2a$12$wba0QjE3SnoldFzc5NUQbuIrS/pJLIlhDelxlYrIbljskJsr65s32','Đỗ Minh Tâm','0989984127','Phường Ngọc Khánh Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:57',1,2,11),(44,'tranthiminhsuong@gmail.com','$2a$12$hjTmn4UJAtLV51ZkTiuVLu0/sb/luLxKWyV3WNSVNEzEBu0eJlFCW','Trần Thị Minh Sương','0982715285','Phường Trung Liệt Quận Đống Đa Hà Nội',NULL,'2021-05-27 14:28:58',1,2,118),(45,'tranvantai@gmail.com','$2a$12$0e6Fit.RwCVf2v5TT1RXPOL6PWbnijOFH/vfllUJvdwy9pm7KXySO','Trần Văn Tài','0984041523','Phường Nguyễn Du Quận Hai Bà Trưng Hà Nội',NULL,'2021-05-27 14:28:58',1,2,46),(46,'levantai@gmail.com','$2a$12$i5lf/2NDZna3AfZ.W8NhHebL.zwBfnksip2dn7xkRpFQKTj10omA6','Lê Văn Tài','0982505412','  sô 11 Phường Giang Biên Quận Long Biên Hà Nội',NULL,'2021-05-27 14:28:58',1,2,43),(47,'vuthihoaison@gmail.com','$2a$12$Fcf2uCXYR1Gl00AX/4pLOeAgD2lI1iENdkisEkN4TKDZTGecWT1bu','Vũ Thị Hoài Sơn','0982999800','Phường Thành Công Quận Ba Đình Hà Nội',NULL,'2021-05-27 14:28:59',1,2,14),(48,'duongthanhtam@gmail.com','$2a$12$EZw/EsdA.4pp1YlWgt.Qk.e8xO2KPyyO0zM553VHxi54mIeH7t9vu','Dương Thanh Tâm','0987411132','Phường Lý Thái Tổ Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:59',1,2,22),(49,'hoquoctai@gmail.com','$2a$12$ewH0zyugbyNXZ8FWKXnkT.Cir0oKobjQIRpAA93Vp7EsLBI3pMxFa','Hồ Quốc Tài','0987400219','Phường Đồng Xuân Quận Hoàn Kiếm Hà Nội',NULL,'2021-05-27 14:28:59',1,2,16);
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

-- Dump completed on 2021-05-27 16:30:55
