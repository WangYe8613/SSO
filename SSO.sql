--
-- Table structure for table `UserEO`
--

DROP TABLE IF EXISTS `UserEO`;

CREATE TABLE `UserEO` (
  `uuid` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `passwd` varchar(32) NOT NULL,
  `securityKey` varchar(32) NOT NULL DEFAULT '',
  `token` varchar(32) NOT NULL DEFAULT '',
  `createDate` timestamp NOT NULL DEFAULT '2019-12-31 16:00:00',
  `lastOpDate` varchar(32) NOT NULL DEFAULT '2020-01-01 00:00:00',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
