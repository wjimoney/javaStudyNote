

-- 导出  表t_checkwork 结构
CREATE TABLE IF NOT EXISTS `t_checkwork` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  t_checkwork 的数据：~6 rows (大约)
INSERT INTO `t_checkwork` (`id`, `name`, `date`, `time`) VALUES
	(1, 'zhangsan', '2022-05-24', '08:18:58'),
	(2, 'zhangsan', '2022-05-24', '09:19:10'),
	(3, 'lisi', '2022-05-24', '10:19:40'),
	(4, 'wangwu', '2022-05-24', '08:19:50'),
	(5, 'xiaohua', '2022-05-23', '11:21:01'),
	(6, 'xiaohua', '2022-05-22', '10:36:53');

