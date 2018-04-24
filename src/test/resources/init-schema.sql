-- DROP TABLE IF EXISTS question;
-- CREATE TABLE question (
--   id INT NOT NULL AUTO_INCREMENT,
--   title VARCHAR(255) NOT NULL,
--   content TEXT NULL,
--   user_id INT NOT NULL,
--   created_date DATETIME NOT NULL,
--   comment_count INT NOT NULL,
--   PRIMARY KEY (id),
--   INDEX date_index (created_date ASC));

--   DROP TABLE IF EXISTS user;
--   CREATE TABLE `user` (
--     `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
--     `name` VARCHAR(64) NOT NULL,
--     `password` VARCHAR(128) NOT NULL,
--     `salt` VARCHAR(32) NULL,
--     `head_url` VARCHAR(256) NULL,
--     `gender` VARCHAR(32) NULL,
--     `phone` VARCHAR(64) NOT NULL,
--     `grade` INT NULL,
--     PRIMARY KEY (`id`),
--     UNIQUE INDEX `name_UNIQUE` (`name` ASC),
--     UNIQUE INDEX `phone_UNIQUE` (`phone` ASC))
--   ENGINE=InnoDB DEFAULT CHARSET=utf8;

  DROP TABLE IF EXISTS commodity_info;
  CREATE TABLE `commodity_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `picture` VARCHAR(128) NOT NULL,
  `type` VARCHAR(45) NULL,
  `recommend` TEXT NULL,
  `region` VARCHAR(45) NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB DEFAULT CHARSET=utf8;
