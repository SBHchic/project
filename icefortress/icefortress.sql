show databases;

show tables;

create table member(
  userID varchar(50) not null primary key,
  userPassword varchar(16) not null,
  grade int default 0,
  reg_date datetime not null
);

alter table member modify userPassword varchar(60) not null;

alter table member modify userID varchar(20) not null;

select * from member;

desc member;

create table normalStarforce(
	numberOfItems bigint,
	level int,
	fromStarforce int,
	starforce int,
	ignoreDestroy boolean,
	discountPCRoom boolean,
	succededCatch tinyint,
	mapleEvent tinyint,
	discountMVPGrade tinyint,
	toad boolean,
	toadToStarforce int,
	toadIgnoreDestroy boolean,
	minSumUpgradePrice bigint,
	maxSumUpgradePrice bigint,
	averageSumUpgradePrice bigint,
	minUpgradeCount int,
	maxUpgradeCount int,
	averageUpgradeCount bigint,
	minDestroyCount int,
	maxDestroyCount int,
	averageDestroyCount bigint,
	minChanceTimeCount int,
	maxChanceTimeCount int,
	averageChanceTimeCount bigint
);

desc normalStarforce;

select * from normalStarforce;

create table superiorStarforce(
	numberOfItems bigint,
	level int,
	fromStarforce int,
	starforce int,
	succededCatch tinyint,
	minSumUpgradePrice bigint,
	maxSumUpgradePrice bigint,
	averageSumUpgradePrice bigint,
	minUpgradeCount int,
	maxUpgradeCount int,
	averageUpgradeCount bigint,
	minDestroyCount int,
	maxDestroyCount int,
	averageDestroyCount bigint,
	minChanceTimeCount int,
	maxChanceTimeCount int,
	averageChanceTimeCount bigint
);

desc superiorStarforce;

select * from superiorStarforce;


