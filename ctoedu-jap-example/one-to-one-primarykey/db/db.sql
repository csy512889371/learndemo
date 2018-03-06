CREATE DATABASE  IF NOT EXISTS `jpa_onetoone_primarykey`;
USE `jpa_onetoone_primarykey`;

--
-- Table structure for table `book_detail`
--
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book_detail`;
CREATE TABLE `book_detail` (
`book_id` int(11) unsigned NOT NULL,
`number_of_pages` int(11) DEFAULT NULL,
PRIMARY KEY (`book_id`),
CONSTRAINT `fk_bookdetail_bookid` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;