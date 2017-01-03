
LOAD DATA INFILE 'F:/member_data.txt' INTO TABLE member_member CHARACTER SET utf8 FIELDS TERMINATED BY '#';
LOAD DATA INFILE 'F:/goods_data.txt' INTO TABLE goods_goods CHARACTER SET utf8 FIELDS TERMINATED BY '#';
LOAD DATA INFILE 'F:/goods_pricing_data.txt' INTO TABLE sales_goods_pricing CHARACTER SET utf8 FIELDS TERMINATED BY '#';
LOAD DATA INFILE 'F:/sales_order_data.txt' INTO TABLE sales_sales_order CHARACTER SET utf8 FIELDS TERMINATED BY '#';
LOAD DATA INFILE 'F:/sales_order_item_data.txt' INTO TABLE sales_sales_order_item CHARACTER SET utf8 FIELDS TERMINATED BY '#';



delete from sales_sales_order_item where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00';
delete from sales_sales_order where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00';
update sales_sales_activity_item set SELL_COUNT = 0 where id = 1;

select * from sales_sales_order where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00' order by id desc limit 0, 120;
select * from sales_sales_order_item where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00' order by id desc limit 0, 120;
select * from sales_sales_activity_item where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00' order by id desc limit 0, 10;


select count(*) from sales_sales_order where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00';
select count(*) from sales_sales_order_item where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00';
select * from sales_sales_activity_item where CREATE_TIME BETWEEN '2017-01-02 00:00:00' and '2017-01-06 00:00:00' order by id desc limit 0, 10;

update sales_sales_activity_item set SECONDS_KILL_COUNT = 100000 where id = 1;


select * from goods_goods limit 0, 20;



-- Master
server-id=100
log-bin = G:/logbin/log-bin.log
binlog-do-db=easystock20150103


grant replication slave,reload,super on *.* to parkoroot@'192.168.0.7' identified by 'root';

show master status;

-- Slave
server-id=200
log-bin=mysql-bin
relay-log=relay-bin
relay-log-index=relay-bin-index


stop slave;
CHANGE MASTER TO MASTER_HOST='192.168.0.15', MASTER_USER='parkoroot', MASTER_PASSWORD='root', MASTER_LOG_FILE='log-bin.000001', MASTER_LOG_POS=107;
start slave;
show slave status;


mysql -h 192.168.1.46 -P 3306 -uroot -p
mysql -h 192.168.1.46 -P 3306 -uparkoroot -p

-----------------------------------------------------------------------------
CREATE TABLE user_thread_with_partition (
  id INT AUTO_INCREMENT NOT NULL,
  user_id INT NOT NULL,
  subject VARCHAR(50) NOT NULL,
  content varchar(256) NOT NULL,
  create_time datetime NOT NULL,
  PRIMARY KEY (id, user_id)
) engine=innodb PARTITION BY HASH(user_id) PARTITIONS 101;


CREATE TABLE user_thread_no_partition (
  id INT AUTO_INCREMENT NOT NULL,
  user_id INT NOT NULL,
  subject VARCHAR(50) NOT NULL,
  content varchar(256) NOT NULL,
  create_time datetime NOT NULL,
  PRIMARY KEY (id, user_id)
) engine=innodb;

LOAD DATA INFILE 'i:/bigdata.txt' INTO TABLE user_thread_with_partition CHARACTER SET utf8 FIELDS TERMINATED BY '#';

LOAD DATA INFILE 'i:/bigdata.txt' INTO TABLE user_thread_no_partition CHARACTER SET utf8 FIELDS TERMINATED BY '#';


explain partitions select count(*) from user_thread_no_partition;
explain partitions select count(*) from user_thread_with_partition;


select count(*) from user_thread_no_partition;
select count(*) from user_thread_with_partition;

explain partitions select count(*) from user_thread_no_partition where user_id = 35;
explain partitions select count(*) from user_thread_with_partition where user_id = 35;


select count(*) from user_thread_no_partition where user_id = 35;
select count(*) from user_thread_with_partition where user_id = 35;


explain partitions select count(*) from user_thread_no_partition where user_id = 88 and create_time >= '2016-03-01 00:00:00' and create_time <= '2016-03-31 23:59:59';
explain partitions select count(*) from user_thread_with_partition where user_id = 88 and create_time >= '2016-03-01 00:00:00' and create_time <= '2016-03-31 23:59:59';


select count(*) from user_thread_no_partition where user_id = 2 and create_time >= '2016-03-01 00:00:00' and create_time <= '2016-03-31 23:59:59';
select count(*) from user_thread_with_partition where user_id = 1 and create_time >= '2016-03-01 00:00:00' and create_time <= '2016-03-31 23:59:59';





#------------------------------------100000000--------------------------------------------


CREATE TABLE part_tab_2 ( c1 int default NULL, c2 varchar(30) default NULL, c3 date default NULL) engine=innodb   
PARTITION BY RANGE (year(c3)) (PARTITION p0 VALUES LESS THAN (1995),  
PARTITION p1 VALUES LESS THAN (1996) , PARTITION p2 VALUES LESS THAN (1997) ,  
PARTITION p3 VALUES LESS THAN (1998) , PARTITION p4 VALUES LESS THAN (1999) ,  
PARTITION p5 VALUES LESS THAN (2000) , PARTITION p6 VALUES LESS THAN (2001) ,  
PARTITION p7 VALUES LESS THAN (2002) , PARTITION p8 VALUES LESS THAN (2003) ,  
PARTITION p9 VALUES LESS THAN (2004) , PARTITION p10 VALUES LESS THAN (2005),  
PARTITION p11 VALUES LESS THAN MAXVALUE);

create table no_part_tab_2 (c1 int(11) default NULL,c2 varchar(30) default NULL,c3 date default NULL) engine=innodb;









#------------------------------------100000000--------------------------------------------


select adddate('1995-01-01',(rand(3)*36520) mod 3652) from dual;
select rand(3) from dual;

CREATE TABLE part_tab_2 ( c1 int default NULL, c2 varchar(30) default NULL, c3 date default NULL) engine=myisam   
PARTITION BY RANGE (year(c3)) (PARTITION p0 VALUES LESS THAN (1995),  
PARTITION p1 VALUES LESS THAN (1996) , PARTITION p2 VALUES LESS THAN (1997) ,  
PARTITION p3 VALUES LESS THAN (1998) , PARTITION p4 VALUES LESS THAN (1999) ,  
PARTITION p5 VALUES LESS THAN (2000) , PARTITION p6 VALUES LESS THAN (2001) ,  
PARTITION p7 VALUES LESS THAN (2002) , PARTITION p8 VALUES LESS THAN (2003) ,  
PARTITION p9 VALUES LESS THAN (2004) , PARTITION p10 VALUES LESS THAN (2005),  
PARTITION p11 VALUES LESS THAN MAXVALUE);

create table no_part_tab_2 (c1 int(11) default NULL,c2 varchar(30) default NULL,c3 date default NULL) engine=myisam;

create index index_c3 on part_tab_2(c3);
create index index_c3 on no_part_tab_2(c3);

select count(*) from part_tab_2 where c3 > date '1995-01-01' and c3 < date '1995-12-31';
select count(*) from no_part_tab_2 where c3 > date '1995-01-01' and c3 < date '1995-12-31';

select count(*) from part_tab_2 where c3 > date '1995-01-01' and c3 < date '1997-12-31';
select count(*) from no_part_tab_2 where c3 > date '1995-01-01' and c3 < date '1997-12-31';


select count(*) from part_tab_2 where c3 > date '1995-01-01' and c3 < date '1997-12-31' and c2 = 'hello'; 
select count(*) from no_part_tab_2 where c3 > date '1995-01-01' and c3 < date '1997-12-31' and c2 = 'hello';


#------------------------------------8000000--------------------------------------------

CREATE TABLE part_tab ( c1 int default NULL, c2 varchar(30) default NULL, c3 date default NULL) engine=myisam   
PARTITION BY RANGE (year(c3)) (PARTITION p0 VALUES LESS THAN (1995),  
PARTITION p1 VALUES LESS THAN (1996) , PARTITION p2 VALUES LESS THAN (1997) ,  
PARTITION p3 VALUES LESS THAN (1998) , PARTITION p4 VALUES LESS THAN (1999) ,  
PARTITION p5 VALUES LESS THAN (2000) , PARTITION p6 VALUES LESS THAN (2001) ,  
PARTITION p7 VALUES LESS THAN (2002) , PARTITION p8 VALUES LESS THAN (2003) ,  
PARTITION p9 VALUES LESS THAN (2004) , PARTITION p10 VALUES LESS THAN (2005),  
PARTITION p11 VALUES LESS THAN MAXVALUE);

create table no_part_tab (c1 int(11) default NULL,c2 varchar(30) default NULL,c3 date default NULL) engine=myisam;

create index index_c3 on part_tab(c3);
create index index_c3 on no_part_tab(c3);

select count(*) from part_tab where c3 > date '1995-01-01' and c3 < date '1995-12-31';
select count(*) from no_part_tab where c3 > date '1995-01-01' and c3 < date '1995-12-31';

select count(*) from part_tab where c3 > date '1995-01-01' and c3 < date '1997-12-31';
select count(*) from no_part_tab where c3 > date '1995-01-01' and c3 < date '1997-12-31';


select count(*) from part_tab where c3 > date '1995-01-01' and c3 < date '1996-12-31' and c2='hello'; 
select count(*) from no_part_tab where c3 > date '1995-01-01' and c3 < date '1996-12-31' and c2='hello'; 


#---------------------------------------------------------------
select (2000001 div 1000000) from dual;

CREATE TABLE user2(
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `LAST_UPDATE_TIME` datetime NOT NULL,
  `LAST_UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB PARTITION BY HASH(ID DIV 1000000) PARTITIONS 100;





EXPLAIN PARTITIONS select * from user2 where ID <= 1000000;
EXPLAIN PARTITIONS select * from user2 where ID <= 1;
EXPLAIN select * from user2 where ID <= 1;



LOAD DATA INFILE 'C:/bigdata_2.txt' INTO TABLE NO_PART_TAB_2 CHARACTER SET utf8 FIELDS TERMINATED BY '#';

