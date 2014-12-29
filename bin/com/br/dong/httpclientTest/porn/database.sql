--代理列表
CREATE TABLE proxy(
	ip varchar(20),
    port int,
	type varchar(6),
	updatetime varchar(20)
);

--视频资料表
CREATE TABLE vedio(
	 title varchar(120),
   preImgSrc varchar(100),
	vedioUrl varchar(100),
	infotime varchar(20),
	 	videoId varchar(100),
	updatetime varchar(30),
	flag int,
	type varchar(10)
)default charset=utf8;
//--增加字段
alter table vedio add type varchar(10)


//
insert into vedio(title,preImgSrc,vedioUrl,infotime,flag) values('中文','aa','aa','aa',0);