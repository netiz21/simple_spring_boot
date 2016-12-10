CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT 'name',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'sex, 0 woman, 1 man',
  `descp` varchar(100) NOT NULL DEFAULT '' COMMENT 'description',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='user table'
