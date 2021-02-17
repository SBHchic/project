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

alter table member add server varchar(20);

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

create table boardQnA(
	boardQnA_ID int,
	boardQnA_ReplyID int,
	boardQnA_CommentID int,
	boardQnA_CommentID_Re int,
	boardQnA_Title varchar(50),
	userID varchar(20) not null,
	boardQnA_Reg_Date datetime,
	boardQnA_Content varchar(2048) not null,
	available tinyint default 1,
	boardQnA_DeleteReg_Date datetime,
	notice tinyint default 0
);

desc boardQnA;

select * from boardQnA;

create table guildBoard(
	writtenID int,
	replyID int,
	commentID int,
	commentID_Re int,
	title varchar(50),
	userID varchar(20) not null,
	reg_Date datetime,
	content varchar(2048) not null,
	available tinyint default 1,
	deleteReg_Date datetime,
	notice tinyint default 0
);

desc guildBoard;

select * from guildBoard;

create table freeBoard(
	writtenID int,
	replyID int,
	commentID int,
	commentID_Re int,
	title varchar(50),
	userID varchar(20) not null,
	reg_Date datetime,
	content varchar(2048) not null,
	available tinyint default 1,
	deleteReg_Date datetime,
	notice tinyint default 0
);

desc freeBoard;

select * from freeBoard;

create table guildBoard_noticeAndMeetingLog(
	writtenID int,
	title varchar(50),
	userID varchar(20) not null,
	reg_Date datetime,
	content varchar(2048) not null,
	available tinyint default 1,
	location tinyint 
);

desc guildBoard_noticeAndMeetingLog;

select * from guildBoard_noticeAndMeetingLog;
