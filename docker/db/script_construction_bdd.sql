drop database if exists amt_project;
create database amt_project;

use amt_project;

SET FOREIGN_KEY_CHECKS=0;

create table users(
	email varchar(60), 
    hashPass varchar(64) NOT NULL, 
    firstName varchar(60) NOT NULL,
    lastName varchar(60) NOT NULL,
    permissionLevel int NOT NULL,
    IDQuestion int,
    responseQuestion varchar(60), 
    TOKEN varchar(64),
    tokenDate varchar(20),
	isActive tinyint NOT NULL DEFAULT 1,
	hasToChangePassword tinyint NOT NULL DEFAULT 0,
    description varchar(300),
    imageUrl varchar(300),
    PRIMARY KEY (email),
    FOREIGN KEY (IDQuestion) REFERENCES questions(ID)
);

create table applications(
    appOwner varchar(60) NOT NULL,
    appName varchar(60) NOT NULL,
    description varchar(300),
	APIToken varchar(36) NOT NULL UNIQUE,
    APISecret varchar(36) NOT NULL UNIQUE,
    foreign key (appOwner) references users(email)
);

create table questions(
	ID int auto_increment,
    question varchar(100),
    primary key (ID)
);

insert into questions(question) values ("What Is your favorite book?"),("What is the name of the road you grew up on?"),("What is your mother’s maiden name?"),("What was the name of your first/current/favorite pet?"),("What was the first company that you worked for?"),("Where did you meet your spouse?"),("Where did you go to high school/college?"),("What is your favorite food?"),("What city were you born in?"),("Where is your favorite place to vacation?");
insert into users values('admin@admin.com', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', 'Admin', 'Admin', 1, 1, 'yes', null, null, 1, 0, null, 'https://i.imgur.com/vk4uQWN.jpg'); #password = admin

SET FOREIGN_KEY_CHECKS=1;
