CREATE DATABASE  IF NOT EXISTS `jpa_onetomany`;
USE `jpa_onetomany`;

--
-- Table structure for table `book_detail`
--

DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`book_category_id` int(11) unsigned DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fk_book_bookcategoryid_idx` (`book_category_id`),
CONSTRAINT `fk_book_bookcategoryid` FOREIGN KEY (`book_category_id`) REFERENCES `book_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;