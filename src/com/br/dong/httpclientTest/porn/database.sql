--代理列表 
CREATE TABLE proxy(
	ip varchar(20),
    port varchar(20),
	type varchar(6),
	updatetime varchar(20)
);

--视频资料表       解决插入乱码问题
CREATE TABLE vedio(
	 title varchar(120),
   preImgSrc varchar(100),
	vedioUrl varchar(100),
	infotime varchar(20),
	flag int
)default charset=utf8;
     SET character_set_client = utf8 ;
     SET character_set_connection = utf8 ;
     SET character_set_database = utf8 ;
     SET character_set_results = utf8 ;
     SET character_set_server = utf8 ;
     SET collation_connection = utf8 ;
     SET collation_database = utf8 ;
     SET collation_server = utf8 ;
//
insert into vedio(title,preImgSrc,vedioUrl,infotime,flag) values('中文','aa','aa','aa',0);