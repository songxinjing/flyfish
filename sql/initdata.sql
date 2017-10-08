-- 初始化国家信息
delete from Country;
insert into Country(id,name,orderNum) values(1,'美国',1);
insert into Country(id,name,orderNum) values(2,'法国',2);
insert into Country(id,name,orderNum) values(3,'德国',3);
insert into Country(id,name,orderNum) values(4,'瑞典',4);
insert into Country(id,name,orderNum) values(5,'加拿大',5);
insert into Country(id,name,orderNum) values(6,'澳大利亚',6);
insert into Country(id,name,orderNum) values(7,'英国',7);
insert into Country(id,name,orderNum) values(8,'巴西',8);
insert into Country(id,name,orderNum) values(9,'瑞士',9);
insert into Country(id,name,orderNum) values(10,'挪威',10);
insert into Country(id,name,orderNum) values(11,'丹麦',11);
insert into Country(id,name,orderNum) values(12,'意大利',12);
insert into Country(id,name,orderNum) values(13,'荷兰',13);
insert into Country(id,name,orderNum) values(14,'墨西哥',14);
insert into Country(id,name,orderNum) values(15,'西班牙',15);
insert into Country(id,name,orderNum) values(16,'智利',16);
insert into Country(id,name,orderNum) values(17,'捷克',17);
insert into Country(id,name,orderNum) values(18,'匈牙利',18);
insert into Country(id,name,orderNum) values(19,'波兰',19);
insert into Country(id,name,orderNum) values(20,'其他国家',20);

-- 初始化平台信息
delete from Platform;
insert into Platform(id,name,orderNum,rate) values(1,'Ebay',1,0);
insert into Platform(id,name,orderNum,rate) values(2,'Amazon',2,0);
insert into Platform(id,name,orderNum,rate) values(3,'AliExpress',3,0);
insert into Platform(id,name,orderNum,rate) values(4,'Wish',4,0);
insert into Platform(id,name,orderNum,rate) values(5,'Joom',5,0);

-- 初始化物流产品信息
delete from LogisProd;
insert into LogisProd(id,name,orderNum) values(1,'中邮小包',1);
insert into LogisProd(id,name,orderNum) values(2,'中邮挂号',2);
insert into LogisProd(id,name,orderNum) values(3,'DHL经济小包',3);
insert into LogisProd(id,name,orderNum) values(4,'德国平邮',4);
insert into LogisProd(id,name,orderNum) values(5,'德邮挂号',5);
insert into LogisProd(id,name,orderNum) values(6,'土邮小包',6);
insert into LogisProd(id,name,orderNum) values(7,'土邮挂号',7);
insert into LogisProd(id,name,orderNum) values(8,'EUB',8);
insert into LogisProd(id,name,orderNum) values(9,'英国微包',9);
insert into LogisProd(id,name,orderNum) values(10,'欧速通',10);
insert into LogisProd(id,name,orderNum) values(11,'yodel小包',11);

-- 初始化域名信息
delete from Domain;
insert into Domain(name,orderNum) values('www.bentain.site',1);
insert into Domain(name,orderNum) values('www.bentain.vip',2);
insert into Domain(name,orderNum) values('www.bentain.top',3);
insert into Domain(name,orderNum) values('www.bentain.ltd',4);
insert into Domain(name,orderNum) values('www.bentain.cn',5);

-- 管理员
insert into `User`(userId,email,name,password,phone,state) values('admin','','管理员','admin12345','',0);
