
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



LOAD DATA INFILE 'i:/bigdata_2.txt' INTO TABLE NO_PART_TAB CHARACTER SET utf8 FIELDS TERMINATED BY '#';

