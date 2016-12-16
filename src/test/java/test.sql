LOAD DATA INFILE 'E:/bigdata_20161216_005309.txt' INTO TABLE USER CHARACTER SET utf8 FIELDS TERMINATED BY '#';

select count(*) from user;

select count(*) from user where ID > 1000000 and ID < 2000000;

select count(*) from user2 where ID > 1000000 and ID < 2000000;

select count(*) from user3 where ID > 1000000 and ID < 2000000;

select count(*) from user4 where ID > 1000000 and ID < 2000000;

EXPLAIN PARTITIONS select * from user where ID < 100 or (ID > 10000 and ID < 10100);
EXPLAIN PARTITIONS select * from user where (ID > 999999 and ID <= 1000000);


EXPLAIN PARTITIONS select * from user2;
EXPLAIN PARTITIONS select * from user2 where (ID > 999999 and ID <= 1000000) OR (ID > 10000001 and ID <= 10000002);

EXPLAIN PARTITIONS select * from user2 where ID < 1000000;
EXPLAIN PARTITIONS select * from user2 where ID >= 1000000 and ID < 2000000 ;

EXPLAIN PARTITIONS select * from user3 where ID >= 1000000 and ID < 2000000 ;

EXPLAIN PARTITIONS select * from user4 where ID > 1000000 and ID < 2000000;

select 
  partition_name part,  
  partition_expression expr,  
  partition_description descr,  
  table_rows  
from information_schema.partitions  where 
  table_schema = schema()  
  and table_name='user';  


select count(ID) from SECURITY_USER;

select max(create_time) from SECURITY_USER union all select min(create_time) from SECURITY_USER;

CREATE TABLE sales(
	order_date DATETIME NOT NULL
) ENGINE=INNODB PARTITION BY RANGE(YEAR(order_date)) (
	PARTITION p_2015 VALUES LESS THAN (2015),
	PARTITION p_2016 VALUES LESS THAN (2016),
	PARTITION p_catchall VALUES LESS THAN MAXVALUE);


CREATE TABLE user(
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `LAST_UPDATE_TIME` datetime NOT NULL,
  `LAST_UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB PARTITION BY HASH(ID div 1000);


CREATE TABLE user2(
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `LAST_UPDATE_TIME` datetime NOT NULL,
  `LAST_UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB PARTITION BY HASH(ID DIV 1000000) PARTITIONS 100;


CREATE TABLE user3(
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `LAST_UPDATE_TIME` datetime NOT NULL,
  `LAST_UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB;

CREATE TABLE user4(
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `LAST_UPDATE_TIME` datetime NOT NULL,
  `LAST_UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB PARTITION BY HASH(ID DIV 1000000) PARTITIONS 100;
