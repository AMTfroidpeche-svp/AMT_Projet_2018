drop database if exists amt_project;
create database amt_project;

use amt_project;

SET FOREIGN_KEY_CHECKS=0;
create table users(
	email varchar(60), 
    hashPass varbinary(256) NOT NULL, 
    firstName varchar(60) NOT NULL,
    lastName varchar(60) NOT NULL,
    permissonLevel INT NOT NULL,
    IDQuestion int,
    responseQuestion varchar(60), 
    TOKEN varbinary(256),
    tokenDate DateTime,
    PRIMARY KEY (email),
    FOREIGN KEY (IDQuestion) REFERENCES questions(ID)
);

create table applications(
    app_owner varchar(60) NOT NULL,
    app_name varchar(60) NOT NULL,
    description varchar(300),
	API_TOKEN varchar(36) NOT NULL UNIQUE,
    API_KEY varchar(36) NOT NULL UNIQUE,
    foreign key (app_owner) references users(email)
);

create table Questions(
	ID int auto_increment,
    question varchar(100),
    primary key (ID)
);


SET FOREIGN_KEY_CHECKS=1;