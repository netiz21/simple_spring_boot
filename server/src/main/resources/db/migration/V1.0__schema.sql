CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  `sex` varchar(6) NOT NULL DEFAULT '',
  `descp` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
