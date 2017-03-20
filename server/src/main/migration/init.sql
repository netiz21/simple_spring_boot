CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT 'name',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'sex, 0 woman, 1 man',
  `descp` varchar(100) NOT NULL DEFAULT '' COMMENT 'description',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='user table'

CREATE TABLE `operation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `target_class` varchar(60) NOT NULL DEFAULT '',
  `target_method` varchar(40) NOT NULL DEFAULT '',
  `method_param` varchar(200) NOT NULL DEFAULT '',
  `method_return` varchar(200) NOT NULL DEFAULT '',
  `method_throw` varchar(60) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='operation log';
