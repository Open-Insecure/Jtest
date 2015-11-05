 --dabase test 用来给自动注册提供用户名和密码
 CREATE TABLE signuser(
  id int NOT NULL AUTO_INCREMENT primary key,
  username varchar(30),
  password varchar(30),
  email  varchar(30)
)default charset=utf8;
--usedproxy 已经用来
CREATE  TABLE usedproxy(
  id int NOT NULL AUTO_INCREMENT primary key,
  ip varchar(30),
  port varchar(30)
)DEFAULT charset=utf8;