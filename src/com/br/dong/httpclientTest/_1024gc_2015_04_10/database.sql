--部署在skydrive数据库中的采集表
CREATE TABLE collectlogs(
  id int NOT NULL AUTO_INCREMENT primary key,
  resourceSite varchar(30),
  type varchar(10),
  name varchar(300),
  content  varchar(65535),
  resourceUrl  varchar(100),
  origDownloadUrl  varchar(100),
  downloadUrl  varchar(100),
  imgUrls varchar(200) ,
  updatetime varchar(30),
  section  varchar(30)
)default charset=utf8;

--2015-05-25 修改字段长度
alter table  collectlogs modify  column imgUrls varchar(2000) ;
--2015-06-01 新增字段section 用来储存采集的种子的板块
alter table collectlogs add section varchar(30);

