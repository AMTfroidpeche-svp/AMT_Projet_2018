drop database if exists amt_project;
create database amt_project;

use amt_project;

SET FOREIGN_KEY_CHECKS=0;
create table users(
	email varchar(60), 
    hashPass varchar(64) NOT NULL, 
    firstName varchar(60) NOT NULL,
    lastName varchar(60) NOT NULL,
    permissionLevel INT NOT NULL,
    IDQuestion int,
    responseQuestion varchar(60), 
    TOKEN varchar(64),
    tokenDate varchar(20),
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

create table Questions(
	ID int auto_increment,
    question varchar(100),
    primary key (ID)
);

insert into Questions(question) values ("Q1"),("Q2"),("Q3"),("Q4"),("Q5"),("Q6"),("Q7"),("Q8"),("Q9"),("Q10"),("Q11");

insert into users(email, hashPass, firstName, lastName, permissionLevel, IDQuestion, responseQuestion, TOKEN, tokenDate) 
	values('admin@admin.com', '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918', 'admin', 'admin', 1, 1, 'allo', 'fiweererergweofjweoif', NULL);

delete from users where email='email1@email.com';
select * from users;
select * from applications;
select * from questions;

SELECT appOwner, appName, description, APIToken FROM applications WHERE appOwner = ? ORDER BY appName LIMIT 10 OFFSET 10;


SET FOREIGN_KEY_CHECKS=1;