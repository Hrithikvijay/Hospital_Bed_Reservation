-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: bed_reservation
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `hospital_details`
--

DROP TABLE IF EXISTS `hospital_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_details` (
  `Hospital_Id` varchar(100) NOT NULL,
  `Hospital_Name` varchar(100) NOT NULL,
  `Address` varchar(250) NOT NULL,
  `District` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Contact` varchar(100) NOT NULL,
  PRIMARY KEY (`Hospital_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_details`
--

LOCK TABLES `hospital_details` WRITE;
/*!40000 ALTER TABLE `hospital_details` DISABLE KEYS */;
INSERT INTO `hospital_details` VALUES ('1000000000','Appolo hospital','25,GRT Road','Chennai','Tamil Nadu','9360295891'),('1000000001','Saraswathy Hospital','24,Ram nagar road','Chennai','Tamil Nadu','9874563210'),('1000000002','Kaveri Hospital','26,kk road,kk nagar','Chennai','Tamil Nadu','9360295891'),('1000000005','Appolo hospital','anna salai','Chennai','Tamil Nadu','9874563210'),('1000000006','Saraswathy hospital','12,MMP road','Chennai','Tamil Nadu','9360295891'),('1000000007','Sathyam Hospital','21,T nagar main road,T nagar','Chennai','Tamil Nadu','9360295891'),('1000000009','Latha Hospital ','12,Mk road ,velachery','Chennai','Tamil Nadu','9360295891');
/*!40000 ALTER TABLE `hospital_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_login`
--

DROP TABLE IF EXISTS `hospital_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_login` (
  `User_id` int NOT NULL AUTO_INCREMENT,
  `Hospital_Id` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`User_id`),
  KEY `hospital_login_ibfk_1` (`Hospital_Id`),
  CONSTRAINT `hospital_login_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_login`
--

LOCK TABLES `hospital_login` WRITE;
/*!40000 ALTER TABLE `hospital_login` DISABLE KEYS */;
INSERT INTO `hospital_login` VALUES (5,'1000000006','$Rxfsuz36'),(7,'1000000000','$Rxfsuz36'),(8,'1000000005','$Rxfsuz36'),(9,'1000000001','$Rxfsuz36'),(10,'1000000002','$Rxfsuz36'),(11,'1000000009','$Rxfsuz36'),(12,'1000000007','$Rxfsuz36');
/*!40000 ALTER TABLE `hospital_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `icu_beds`
--

DROP TABLE IF EXISTS `icu_beds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `icu_beds` (
  `Bed_Id` int NOT NULL AUTO_INCREMENT,
  `Hospital_Id` varchar(100) NOT NULL,
  `Vacant` int NOT NULL,
  `Occupied` int NOT NULL,
  `Total` int NOT NULL,
  PRIMARY KEY (`Bed_Id`),
  KEY `icu_beds_ibfk_1` (`Hospital_Id`),
  CONSTRAINT `icu_beds_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `icu_beds`
--

LOCK TABLES `icu_beds` WRITE;
/*!40000 ALTER TABLE `icu_beds` DISABLE KEYS */;
INSERT INTO `icu_beds` VALUES (3,'1000000006',1,22,23),(4,'1000000000',9,11,20),(5,'1000000005',10,10,20),(6,'1000000001',15,14,29),(7,'1000000002',15,16,31),(8,'1000000009',23,54,77),(9,'1000000007',11,22,33);
/*!40000 ALTER TABLE `icu_beds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `normal_beds`
--

DROP TABLE IF EXISTS `normal_beds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `normal_beds` (
  `Bed_Id` int NOT NULL AUTO_INCREMENT,
  `Hospital_Id` varchar(100) NOT NULL,
  `Vacant` int NOT NULL,
  `Occupied` int NOT NULL,
  `Total` int NOT NULL,
  PRIMARY KEY (`Bed_Id`),
  KEY `normal_beds_ibfk_1` (`Hospital_Id`),
  CONSTRAINT `normal_beds_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `normal_beds`
--

LOCK TABLES `normal_beds` WRITE;
/*!40000 ALTER TABLE `normal_beds` DISABLE KEYS */;
INSERT INTO `normal_beds` VALUES (3,'1000000006',54,46,100),(4,'1000000000',10,10,20),(5,'1000000005',10,10,20),(6,'1000000001',12,13,25),(7,'1000000002',12,13,25),(8,'1000000009',98,87,185),(9,'1000000007',43,33,76);
/*!40000 ALTER TABLE `normal_beds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oxygen_supported_beds`
--

DROP TABLE IF EXISTS `oxygen_supported_beds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oxygen_supported_beds` (
  `Bed_Id` int NOT NULL AUTO_INCREMENT,
  `Hospital_Id` varchar(100) NOT NULL,
  `Vacant` int NOT NULL,
  `Occupied` int NOT NULL,
  `Total` int NOT NULL,
  PRIMARY KEY (`Bed_Id`),
  KEY `oxygen_supported_beds_ibfk_1` (`Hospital_Id`),
  CONSTRAINT `oxygen_supported_beds_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oxygen_supported_beds`
--

LOCK TABLES `oxygen_supported_beds` WRITE;
/*!40000 ALTER TABLE `oxygen_supported_beds` DISABLE KEYS */;
INSERT INTO `oxygen_supported_beds` VALUES (3,'1000000006',225,55,280),(4,'1000000000',15,15,30),(5,'1000000005',10,10,20),(6,'1000000001',14,14,28),(7,'1000000002',13,14,27),(8,'1000000009',65,43,108),(9,'1000000007',22,33,55);
/*!40000 ALTER TABLE `oxygen_supported_beds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `Reservation_Id` int NOT NULL AUTO_INCREMENT,
  `Adhaar_id` varchar(100) NOT NULL,
  `hospital_Id` varchar(100) NOT NULL,
  `District` varchar(100) NOT NULL,
  `State` varchar(45) NOT NULL,
  `Contact` varchar(45) NOT NULL,
  `Bed_Type` varchar(100) NOT NULL,
  `Status` varchar(100) NOT NULL,
  `Date_and_Time` datetime DEFAULT NULL,
  PRIMARY KEY (`Reservation_Id`),
  KEY `reservation_ibfk_1` (`Adhaar_id`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`Adhaar_id`) REFERENCES `user_details` (`Adhaar_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (14,'100000000008','1000000000','Chennai','Tamil Nadu','9360295891','ventilators','Admitted','2021-06-10 05:19:59'),(15,'100000000003','1000000006','Chennai','Tamil Nadu','9360295891','normal_beds','Reserved','2021-06-10 05:33:57'),(16,'100000000011','1000000000','Chennai','Tamil Nadu','9360295891','icu_beds','Admitted','2021-06-10 20:23:43');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details` (
  `Adhaar_id` varchar(100) NOT NULL,
  `User_Name` varchar(100) NOT NULL,
  `Address` varchar(250) NOT NULL,
  `District` varchar(100) NOT NULL,
  `State` varchar(45) NOT NULL,
  `Age` varchar(100) NOT NULL,
  `Gender` varchar(100) NOT NULL,
  `Contact` varchar(100) NOT NULL,
  PRIMARY KEY (`Adhaar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details`
--

LOCK TABLES `user_details` WRITE;
/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` VALUES ('100000000000','Hrithik','MMr road,Madipakkam','Chennai','Tamil Nadu','20','M','9360295891'),('100000000001','Hrithik','Madipakkam','Chennai','Tamil Nadu','30','M','9874563210'),('100000000003','hrithik','madipakkam','Chennai','Tamil Nadu','20','M','9360295891'),('100000000004','hrithik','madipakkam','Chengalpattu','Tamil Nadu','20','M','9360295891'),('100000000006','Hrithik','25,MMR,Madipakkam ','Chennai','Tamil Nadu','20','M','9360295891'),('100000000008','Ajith','3,mmk road,mk nagar','Chennai','Tamil Nadu','20','M','9360295891'),('100000000011','vijay','18,Neelangarai road,Neelangarai','Chennai','Tamil Nadu','46','M','9360295891'),('100000000015','surya','23,MKP road, rk nagar','Chennai','Tamil Nadu','42','M','9360295891'),('123456789012','hrithik25','24,MMR Madipakkam','Chennai','Tamil Nadu','20','M','9874651230'),('123456789321','Hrithik','Madipakkam','Chennai','Tamil Nadu','20','M','9874562310'),('123456987789','Mouli','keelkatalai','chennai','Tamil Nadu','20','M','7894561230'),('987456321012','Hrithik','Madipakkam','Chennai','Tamil Nadu','20','M','9360295891');
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_login` (
  `User_id` int NOT NULL AUTO_INCREMENT,
  `Adhaar_id` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`User_id`),
  KEY `Adhaar_id` (`Adhaar_id`),
  CONSTRAINT `user_login_ibfk_1` FOREIGN KEY (`Adhaar_id`) REFERENCES `user_details` (`Adhaar_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES (4,'100000000001','$Rxfsuz36'),(5,'100000000003','$Rxfsuz36'),(6,'100000000004','$Rxfsuz36'),(7,'100000000006','$Rxfsuz36'),(9,'100000000000','$Rxfsuz36'),(14,'100000000008','$Rxfsuz36'),(15,'100000000011','$Rxfsuz36'),(16,'100000000015','$Rxfsuz36');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventilators`
--

DROP TABLE IF EXISTS `ventilators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventilators` (
  `Bed_Id` int NOT NULL AUTO_INCREMENT,
  `Hospital_Id` varchar(100) NOT NULL,
  `Vacant` int NOT NULL,
  `Occupied` int NOT NULL,
  `Total` int NOT NULL,
  PRIMARY KEY (`Bed_Id`),
  KEY `ventilators_ibfk_1` (`Hospital_Id`),
  CONSTRAINT `ventilators_ibfk_1` FOREIGN KEY (`Hospital_Id`) REFERENCES `hospital_details` (`Hospital_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventilators`
--

LOCK TABLES `ventilators` WRITE;
/*!40000 ALTER TABLE `ventilators` DISABLE KEYS */;
INSERT INTO `ventilators` VALUES (3,'1000000006',22,22,44),(4,'1000000000',9,11,20),(5,'1000000005',10,10,20),(6,'1000000001',13,12,25),(7,'1000000002',17,18,35),(8,'1000000009',65,21,86),(9,'1000000007',33,44,77);
/*!40000 ALTER TABLE `ventilators` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-11 20:08:42
