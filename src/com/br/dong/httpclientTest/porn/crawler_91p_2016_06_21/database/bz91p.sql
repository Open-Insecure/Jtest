--数据库 mysql5.5
--数据库名 bz91p
--视频列表采集
CREATE TABLE video(
 	videoId varchar(100),
  title varchar(120),
  preImgSrc varchar(100),
	vedioUrl varchar(100),
	infotime varchar(20),
	updatetime varchar(30),
	flag int,
	type varchar(10)
)default charset=utf8;