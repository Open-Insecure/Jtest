--创建种子列表
CREATE TABLE torrents(
   flag varchar(30),
	 type varchar(10),
	 title varchar(200),
   url varchar(100),
	 size varchar(100),
	 torrentUrl varchar(100),
	 time varchar(30),
	 updatetime varchar(30),
	 picUrl  varchar(100),
	 message varchar(3000),
	 temp varchar(500)
)default charset=utf8;
 --修改一个字段的类型
alter table torrents MODIFY flag VARCHAR(30);


--创建采集区域url表
CREATE TABLE urls(
	  type varchar(10),
	 	title varchar(200),
    url varchar(100),
	 	floderName varchar(100)

)default charset=utf8;


--种子
insert into urls(type,title,url,flodername)values('torrent','亚洲无码转帖','http://38.103.161.188/forum/forum-25-','bt_yzwmzt');
insert into urls(type,title,url,flodername)values('torrent','亚洲有码转帖','http://38.103.161.188/forum/forum-58-','bt_yzymzt');
insert into urls(type,title,url,flodername)values('torrent','欧美无码','http://38.103.161.188/forum/forum-77-','bt_omwm');
insert into urls(type,title,url,flodername)values('torrent','成人游戏卡通漫画转区','http://38.103.161.188/forum/forum-27-','bt_cryxktmhzq');
insert into urls(type,title,url,flodername)values('torrent','亚洲无码原创区','http://38.103.161.188/forum/forum-143-','bt_yzwmyc');
insert into urls(type,title,url,flodername)values('torrent','情色三级','http://38.103.161.188/forum/forum-426-','bt_3j');
--网盘或迅雷等链接下载
insert into urls(type,title,url,flodername)values('url','成人网盘','http://38.103.161.188/forum/forum-187-','url_wp');
insert into urls(type,title,url,flodername)values('url','电驴','http://38.103.161.188/forum/forum-270-','url_dl');
insert into urls(type,title,url,flodername)values('url','迅雷','http://38.103.161.188/forum/forum-212-','url_xl');
--图片外链
insert into urls(type,title,url,flodername)values('pic','熟女乱伦图片分享区','http://38.103.161.188/forum/forum-242-','pic_no_download_sn');
insert into urls(type,title,url,flodername)values('pic','西洋靓女骚妹','http://38.103.161.188/forum/forum-68-','pic_no_download_xfln');
insert into urls(type,title,url,flodername)values('pic','动漫卡通游戏贴图区','http://38.103.161.188/forum/forum-60-','pic_no_download_dmkt');
insert into urls(type,title,url,flodername)values('pic','东方靓女集中营','http://38.103.161.188/forum/forum-64-','pic_no_download_dfln');
insert into urls(type,title,url,flodername)values('pic','精品套图鉴赏区','http://38.103.161.188/forum/forum-184-','pic_no_download_jptt');
insert into urls(type,title,url,flodername)values('pic','高跟美足丝袜区','http://38.103.161.188/forum/forum-249-','pic_no_download_ggsw');
--小说txt
insert into urls(type,title,url,flodername)values('txt','原创人生区','http://38.103.161.188/forum/forum-383-','txt_download_ycrs');
insert into urls(type,title,url,flodername)values('txt','人妻意淫区','http://38.103.161.188/forum/forum-279-','txt_download_rqyy');
insert into urls(type,title,url,flodername)values('txt','乱伦迷情区','http://38.103.161.188/forum/forum-83-','txt_download_llqm');


-- insert into urls(type,title,url,flodername)values('torrent','亚洲有码原创区','http://38.103.161.188/forum/forum-230-','bt_yzymyc');
-- insert into urls(type,title,url,flodername)values('torrent','欧美无码原创区','http://38.103.161.188/forum/forum-229-','bt_omwmyc');
-- insert into urls(type,title,url,flodername)values('torrent','成人游戏动漫原创分享区','http://38.103.161.188/forum/forum-231-','bt_cryxdmyc');
-- insert into urls(type,title,url,flodername)values('torrent','新手会员原创BT发布区','http://38.103.161.188/forum/forum-406-','bt_xsbtfb');
-- insert into urls(type,title,url,flodername)values('torrent','BT自拍原创区','http://38.103.161.188/forum/forum-530-','bt_btzp');






