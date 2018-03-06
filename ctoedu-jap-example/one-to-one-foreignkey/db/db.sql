CREATE DATABASE  IF NOT EXISTS `jpa_onetoone_foreignkey`;
USE `jpa_onetoone_foreignkey`;

--
-- Table structure for table `book_detail`
--

DROP TABLE IF EXISTS `book_detail`;
CREATE TABLE `book_detail` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`number_of_pages` int(11) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`book_detail_id` int(11) unsigned DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fk_book_bookdetail` (`book_detail_id`),
CONSTRAINT `fk_book_bookdetail` FOREIGN KEY (`book_detail_id`) REFERENCES `book_detail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;