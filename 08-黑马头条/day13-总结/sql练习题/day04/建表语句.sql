CREATE TABLE IF NOT EXISTS `t5_student` (
  `sno` int(11) DEFAULT NULL,
  `sname` varchar(50) DEFAULT NULL,
  `sage` int(11) DEFAULT NULL,
  `ssex` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t5_student` (`sno`, `sname`, `sage`, `ssex`) VALUES
	(101, '张三', 16, '男'),
	(102, '李四', 17, '男'),
	(103, '王五', 18, '男'),
	(104, '小花', 19, '女');



CREATE TABLE IF NOT EXISTS `t5_course` (
  `cno` int(11) DEFAULT NULL,
  `cname` varchar(50) DEFAULT NULL,
  `tno` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t5_course` (`cno`, `cname`, `tno`) VALUES
	(1, '语文', 1001),
	(2, '英语', 1002),
	(3, '数学', 1003);



CREATE TABLE IF NOT EXISTS `t5_sc` (
  `sno` int(11) DEFAULT NULL,
  `cno` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t5_sc` (`sno`, `cno`, `score`) VALUES
	(101, 1, 98),
	(101, 2, 89),
	(101, 3, 99),
	(102, 1, 66),
	(102, 2, 84),
	(102, 3, 48),
	(103, 2, 69),
	(103, 3, 78),
	(104, 3, 81);


CREATE TABLE IF NOT EXISTS `t5_teacher` (
  `tno` int(11) DEFAULT NULL,
  `tname` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t5_teacher` (`tno`, `tname`) VALUES
	(1001, '叶平'),
	(1002, '张老师'),
	(1003, '王老师');
	
	

CREATE TABLE `t2_stu` (
  `id` int(11) NOT NULL,
  `name` varchar(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(11) DEFAULT NULL,
  `cid` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `t2_stu`(`id`,`name`,`age`,`sex`,`cid`) values 
(1001,'张三',33,'男','C1001'),
(1002,'李四',32,'女','C1001'),
(1003,'王五',28,'男','C1002'),
(1004,'赵六',22,'男',NULL);



CREATE TABLE `t2_cls` (
  `cid` varchar(11) DEFAULT NULL,
  `cname` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `t2_cls`(`cid`,`cname`) values 
('C1001','一班'),
('C1002','二班'),
('C1003','三班'),
('C1004','四班');

