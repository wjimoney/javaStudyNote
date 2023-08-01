

DROP TABLE IF EXISTS `mark`;

CREATE TABLE `mark` (
  `code` varchar(11) DEFAULT NULL,
  `sub` varchar(11) DEFAULT NULL,
  `mark` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mark` */

insert  into `mark`(`code`,`sub`,`mark`) values 
('001','数学','66'),
('002','数学','58'),
('003','数学','14'),
('004','数学','90'),
('005','数学','58'),
('006','数学','77'),
('007','数学','85'),
('001','语文','55'),
('002','语文','34'),
('003','语文','31'),
('004','语文','99'),
('005','语文','80'),
('006','语文','72'),
('007','语文','67');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `class` varchar(11) DEFAULT NULL,
  `code` varchar(11) DEFAULT NULL,
  `name` varchar(11) DEFAULT NULL,
  'age' int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`class`,`code`,`name`,`age`) values 
('一班','001','张三',12),
('一班','002','李四',34),
('一班','003','王五',25),
('一班','004','赵六',11),
('二班','005','孙七',67),
('二班','006','张三',12),
('二班','007','吴八',41),
('三班','008','吴九',24),
('三班','009','吴十',44),
('三班','010','王十',14),
('三班','011','王五',25),
('三班','012','赵六',11),
('三班','013','张三',14),
('三班','014','张三',12),
('三班','015','李四',34);
