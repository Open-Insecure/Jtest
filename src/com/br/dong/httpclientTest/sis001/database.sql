--创建种子列表
CREATE TABLE torrents(
   flag varchar(10),
	 type varchar(10),
	 title varchar(200),
   url varchar(100),
	 size varchar(20),
	 torrentUrl varchar(100),
	 time varchar(30),
	 picUrl  varchar(100)
)default charset=utf8;

--创建采集区域url表
CREATE TABLE urls(
	  type varchar(10),
	 	title varchar(200),
    url varchar(100),
	 	floderName varchar(100)

)default charset=utf8;

insert into urls(type,title,url,flodername)values('torrent','亚洲无码转帖','http://sis001.com/forum/forum-25-','yzwmzt');
insert into urls(type,title,url,flodername)values('torrent','亚洲有码转帖','http://sis001.com/forum/forum-58-','yzymzt');
insert into urls(type,title,url,flodername)values('torrent','欧美无码','http://sis001.com/forum/forum-77-','omwm');
insert into urls(type,title,url,flodername)values('torrent','成人游戏卡通漫画转区','http://sis001.com/forum/forum-27-','cryxktmhzq');
insert into urls(type,title,url,flodername)values('torrent','亚洲无码原创区','http://sis001.com/forum/forum-143-','yzwmyc');
insert into urls(type,title,url,flodername)values('torrent','亚洲有码原创区','http://sis001.com/forum/forum-230-','yzymyc');
insert into urls(type,title,url,flodername)values('torrent','欧美无码原创区','http://sis001.com/forum/forum-229-','omwmyc');
insert into urls(type,title,url,flodername)values('torrent','成人游戏动漫原创分享区','http://sis001.com/forum/forum-231-','cryxdmyc');
insert into urls(type,title,url,flodername)values('torrent','新手会员原创BT发布区','http://sis001.com/forum/forum-406-','xsbtfb');
insert into urls(type,title,url,flodername)values('torrent','BT自拍原创区','http://sis001.com/forum/forum-530-','btzp');





