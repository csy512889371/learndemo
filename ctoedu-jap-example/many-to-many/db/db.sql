CREATE DATABASE  IF NOT EXISTS `jpa_manytomany`;
USE `jpa_manytomany`;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
--
-- Table structure for table `book_publisher`
--

DROP TABLE IF EXISTS `book_publisher`;
CREATE TABLE `book_publisher` (
`book_id` int(10) unsigned NOT NULL,
`publisher_id` int(10) unsigned NOT NULL,
PRIMARY KEY (`book_id`,`publisher_id`),
KEY `fk_bookpublisher_publisher_idx` (`publisher_id`),
CONSTRAINT `fk_bookpublisher_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `fk_bookpublisher_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

