

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `cno` varchar(11) NOT NULL COMMENT '主键',
  `cname` varchar(11) DEFAULT NULL,
  `cstate` int(11) DEFAULT NULL COMMENT '1:必修，2:选修',
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`cno`,`cname`,`cstate`) values 
('1001','语文',1),
('1002','英语',1),
('1003','数学',1),
('1004','音乐',2);

/*Table structure for table `sc` */

DROP TABLE IF EXISTS `sc`;

CREATE TABLE `sc` (
  `sno` varchar(11) NOT NULL COMMENT '\n        联合主键',
  `cno` varchar(11) NOT NULL COMMENT '\n        联合主键',
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`sno`,`cno`) COMMENT '联合主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sc` */

insert  into `sc`(`sno`,`cno`,`score`) values 
('1','1001',89),
('1','1002',99),
('1','1003',90),
('1','1004',45),
('2','1001',79),
('2','1002',77),
('2','1003',88),
('3','1001',80),
('3','1002',97),
('3','1003',78);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `sno` varchar(11) NOT NULL COMMENT '主键',
  `sname` varchar(11) DEFAULT NULL,
  `ssex` varchar(11) DEFAULT NULL COMMENT '男，女',
  `sage` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT 'yyyy-MM-dd',
  `sstate` int(11) DEFAULT NULL COMMENT '1:在校，2:离校',
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`sno`,`sname`,`ssex`,`sage`,`birthday`,`sstate`) values 
('1','张三','男',16,'2020-02-03',1),
('2','李四','男',17,'2020-02-03',1),
('3','王五','男',18,'2020-02-03',2),
('4','小花','女',19,'2020-02-03',1),
('5','小明','男',20,'2020-02-03',2),
('6','张飞','男',26,'2021-04-26',2);
