
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', '33', '张老师', '123', 'zzz@itcast.cn', '男 ');
INSERT INTO `user` VALUES ('2', '31', '刘老师', '123', 'lll@itcast.cn', '女');
INSERT INTO `user` VALUES ('3', '17', '赵工', '213', 'zg@itcast.cn', '女');
INSERT INTO `user` VALUES ('4', '40', '高管', '213', 'gg@itcast.cn', 'female');
INSERT INTO `user` VALUES ('5', '28', '李总', '312', 'lz@jxjt.com', 'male');
INSERT INTO `user` VALUES ('6', '34', '王董', '312', 'wd@jxjt.com', 'male');
INSERT INTO `user` VALUES ('7', '55', '孙老板', '4321', 'slb@xzjt.com', '男');
INSERT INTO `user` VALUES ('8', '19', '陈秘书', '4321', 'cms@xzjt.com', '女');
