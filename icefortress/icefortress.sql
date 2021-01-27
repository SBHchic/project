
create table member(
  userID varchar(50) not null primary key,
  userPassword varchar(16) not null,
  grade int default 0,
  reg_date datetime not null
);

alter table member modify userPassword varchar(60) not null;

alter table member modify userID varchar(20) not null;
