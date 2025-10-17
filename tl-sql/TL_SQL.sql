CREATE DATABASE IF NOT EXISTS TL
  DEFAULT CHARACTER SET utf8mb4    -- UTF-8 + 이모지 지원 (메일 아이디 고려함)
  DEFAULT COLLATE utf8mb4_0900_ai_ci; -- 대소문자/악센트 무시

USE TL;

CREATE TABLE IF NOT EXISTS `member` (
  `memberId` varchar(30) NOT NULL,
  `memberPw` varchar(100) NOT NULL,
  `memberName` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`memberId`)
);

CREATE TABLE IF NOT EXISTS `post` (
  `no` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `memberId` varchar(30) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `media` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`no`),
  CONSTRAINT `fav_post_member` FOREIGN KEY (`memberId`)
    REFERENCES `member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `performance` (
  `per_num` BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `per_id` VARCHAR(200) NOT NULL,
  `per_genreC` VARCHAR(50) DEFAULT NULL,
  `per_regionC` VARCHAR(50) DEFAULT NULL,
  `per_requestT` VARCHAR(50) DEFAULT NULL,
  `per_title` VARCHAR(200) NOT NULL,
  `per_startD` DATE NOT NULL,
  `per_endD` DATE NOT NULL,
  `per_place` VARCHAR(500) NOT NULL,
  `per_runT` VARCHAR(50),
  `per_sche` TEXT,
  `per_price` VARCHAR(500),
  `per_genre` VARCHAR(50),
  `per_poster` VARCHAR(1000),
  `per_address` VARCHAR(255),
  `per_latitude` DECIMAL(11,8),
  `per_longitude` DECIMAL(11,8),
  `per_region` VARCHAR(50),
  `per_rank` INT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `reply` (
  `no` bigint unsigned NOT NULL AUTO_INCREMENT,
  `originNo` bigint unsigned NOT NULL,
  `memberId` varchar(30) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`no`),
  CONSTRAINT `fk_reply_member`
    FOREIGN KEY (`memberId`)
    REFERENCES `member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_reply_post`
    FOREIGN KEY (`originNo`)
    REFERENCES `post` (`no`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `liked` (
  `memberId` varchar(30) NOT NULL,
  `postNo` bigint unsigned NOT NULL,
  `liked` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`memberId`, `postNo`),
  CONSTRAINT `fk_like_post`
    FOREIGN KEY (`postNo`)
    REFERENCES `post` (`no`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_like_memberId`
    FOREIGN KEY (`memberId`)
    REFERENCES `member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `favorite` (
  `fav_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `memberId` varchar(30) NOT NULL,
  `per_num` bigint unsigned NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `liked` tinyint DEFAULT NULL,
  PRIMARY KEY (`fav_id`),
  UNIQUE KEY `uniq_member_performance` (`memberId`, `per_num`),
  CONSTRAINT `fk_fav_memberId`
    FOREIGN KEY (`memberId`)
    REFERENCES `member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_fav_per_id`
    FOREIGN KEY (`per_num`)
    REFERENCES `performance` (`per_num`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `performance_ticket` (
  `ticket_num` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `per_num` BIGINT UNSIGNED NOT NULL,
  `ticket_name` VARCHAR(200),
  `ticket_url` VARCHAR(1000),
  FOREIGN KEY (`per_num`)
    REFERENCES `performance` (`per_num`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `refresh_token` (
  `memberId` varchar(50) NOT NULL,
  `token` text NOT NULL,
  `expiration` datetime NOT NULL,
  PRIMARY KEY (`memberId`),
  CONSTRAINT `fk_refresh_token_memberId`
    FOREIGN KEY (`memberId`)
    REFERENCES `member` (`memberId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);