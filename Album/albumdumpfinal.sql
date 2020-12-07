-- MariaDB dump 10.17  Distrib 10.5.6-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: album
-- ------------------------------------------------------
-- Server version	10.5.6-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `album`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `album` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `album`;

--
-- Table structure for table `fans`
--

DROP TABLE IF EXISTS `fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fans` (
  `singer` varchar(30) NOT NULL,
  `user` varchar(30) NOT NULL,
  PRIMARY KEY (`singer`,`user`),
  KEY `user` (`user`),
  CONSTRAINT `fans_ibfk_1` FOREIGN KEY (`singer`) REFERENCES `singer` (`SINGER_ID`),
  CONSTRAINT `fans_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fans`
--

LOCK TABLES `fans` WRITE;
/*!40000 ALTER TABLE `fans` DISABLE KEYS */;
/*!40000 ALTER TABLE `fans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest` (
  `USER` varchar(30) NOT NULL,
  `MUSIC` int(11) NOT NULL,
  `INTEREST` int(11) DEFAULT NULL,
  `STREAMING` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`USER`,`MUSIC`),
  KEY `MUSIC` (`MUSIC`),
  CONSTRAINT `interest_ibfk_1` FOREIGN KEY (`USER`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `interest_ibfk_2` FOREIGN KEY (`MUSIC`) REFERENCES `music` (`MUSIC_NUM`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES ('user2',3,50,0),('user2',4,64,0);
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `MANAGER_ID` varchar(30) NOT NULL,
  `MANAGER_PW` varchar(20) NOT NULL,
  `MANAGER_NAME` varchar(20) NOT NULL,
  `MANAGER_BD` date DEFAULT NULL,
  `MANAGER_ADDR` varchar(100) DEFAULT NULL,
  `MANAGER_PN` varchar(14) NOT NULL,
  `MANAGES_NUMBER` int(11) DEFAULT NULL,
  PRIMARY KEY (`MANAGER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('admin1','admin1','Manager','1987-06-12','Jeju','010-9876-5432',NULL),('admin2','admin2','Manager2','1997-05-05','Jeonju','010-1234-5678',NULL),('sunghun0727','tjdgns0727','Sunghun','2000-07-27','Soeul','010-5242-877',NULL);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `MUSIC_NUM` int(11) NOT NULL AUTO_INCREMENT,
  `MUSIC_TITLE` varchar(50) NOT NULL,
  `MUSIC_GENRE` varchar(20) DEFAULT NULL,
  `MUSIC_COMP` varchar(20) DEFAULT NULL,
  `MUSIC_LYRICIST` varchar(20) DEFAULT NULL,
  `MUSIC_COMPANY` varchar(30) DEFAULT NULL,
  `MUSIC_MANAGER` varchar(30) DEFAULT NULL,
  `MUSIC_SINGER` varchar(30) NOT NULL,
  PRIMARY KEY (`MUSIC_NUM`),
  KEY `MUSIC_SINGER` (`MUSIC_SINGER`),
  KEY `MUSIC_MANAGER` (`MUSIC_MANAGER`),
  CONSTRAINT `music_ibfk_1` FOREIGN KEY (`MUSIC_SINGER`) REFERENCES `singer` (`SINGER_ID`),
  CONSTRAINT `music_ibfk_2` FOREIGN KEY (`MUSIC_MANAGER`) REFERENCES `manager` (`MANAGER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (3,'Night_Letter','Ballade','IU','IU','EDAM','admin1','IU'),(4,'1','1','1','1','1','admin1','BTS');
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_in_playlist`
--

DROP TABLE IF EXISTS `music_in_playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music_in_playlist` (
  `PLAYLIST_NUM` int(11) NOT NULL,
  `IN_MUSIC` int(11) NOT NULL,
  PRIMARY KEY (`PLAYLIST_NUM`,`IN_MUSIC`),
  KEY `IN_MUSIC` (`IN_MUSIC`),
  CONSTRAINT `music_in_playlist_ibfk_1` FOREIGN KEY (`PLAYLIST_NUM`) REFERENCES `playlist` (`PLAYLIST_NUM`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `music_in_playlist_ibfk_2` FOREIGN KEY (`IN_MUSIC`) REFERENCES `music` (`MUSIC_NUM`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_in_playlist`
--

LOCK TABLES `music_in_playlist` WRITE;
/*!40000 ALTER TABLE `music_in_playlist` DISABLE KEYS */;
INSERT INTO `music_in_playlist` VALUES (8,4);
/*!40000 ALTER TABLE `music_in_playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `PLAYLIST_USER` varchar(30) NOT NULL,
  `PLAYLIST_NUM` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`PLAYLIST_NUM`),
  KEY `PLAYLIST_USER` (`PLAYLIST_USER`),
  CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`PLAYLIST_USER`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES ('user2',7),('user2',8);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singer`
--

DROP TABLE IF EXISTS `singer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `singer` (
  `SINGER_ID` varchar(30) NOT NULL,
  `SINGER_PW` varchar(20) NOT NULL,
  `SINGER_NAME` varchar(20) NOT NULL,
  `SINGER_BD` date DEFAULT NULL,
  PRIMARY KEY (`SINGER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singer`
--

LOCK TABLES `singer` WRITE;
/*!40000 ALTER TABLE `singer` DISABLE KEYS */;
INSERT INTO `singer` VALUES ('BTS','BTS','BTS','1897-12-04'),('IU','IU','IU','1993-05-16');
/*!40000 ALTER TABLE `singer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `USER_ID` varchar(30) NOT NULL,
  `USER_PW` varchar(20) NOT NULL,
  `USER_NAME` varchar(20) NOT NULL,
  `USER_BD` date DEFAULT NULL,
  `USER_ADDR` varchar(100) DEFAULT NULL,
  `USER_PN` varchar(14) NOT NULL,
  `REGISTER_DATE` date NOT NULL,
  `USER_MANAGER` varchar(30) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `USER_MANAGER` (`USER_MANAGER`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`USER_MANAGER`) REFERENCES `manager` (`MANAGER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('sunghunchoi','tjdgns0727','Sunghun','2000-07-27','Seoul','010-5242-8770','2020-12-07','admin1'),('user2','user2','User2','1978-05-21','Jeju','010-5623-8956','2020-12-07','admin1');
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

-- Dump completed on 2020-12-07 19:20:44
