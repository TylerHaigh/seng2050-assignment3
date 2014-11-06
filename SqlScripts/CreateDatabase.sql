/* Drop old Tables */

DROP TABLE IF EXISTS RGMS_DB.Sessions;
DROP TABLE IF EXISTS RGMS_DB.Notifications;
DROP TABLE IF EXISTS RGMS_DB.AccessRecords;
DROP TABLE IF EXISTS RGMS_DB.DiscussionPosts;
DROP TABLE IF EXISTS RGMS_DB.Documents;
DROP TABLE IF EXISTS RGMS_DB.DiscussionThreads;
DROP TABLE IF EXISTS RGMS_DB.DiscussionTypes;
DROP TABLE IF EXISTS RGMS_DB.Meetings;
DROP TABLE IF EXISTS RGMS_DB.GroupUserMaps;
DROP TABLE IF EXISTS RGMS_DB.Coordinators;
DROP TABLE IF EXISTS RGMS_DB.Users;
DROP TABLE IF EXISTS RGMS_DB.Groups;

/* Re-create Database */

DROP DATABASE IF EXISTS RGMS_DB;
CREATE DATABASE RGMS_DB;

/* Create new Tables */

CREATE TABLE RGMS_DB.Groups (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	GroupName VARCHAR(100) NOT NULL,
	Description VARCHAR(500),
	CoordinatorId INT REFERENCES RGMS_DB.Users(Id)
);

CREATE TABLE RGMS_DB.Users (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	FirstName VARCHAR(64),
	LastName VARCHAR(64),
	Username VARCHAR(64) NOT NULL UNIQUE,
	StudentId VARCHAR(64) NOT NULL UNIQUE,
	Passphrase VARCHAR(64)NOT NULL,
	ImageReference VARCHAR(512),
	IsAdmin BOOLEAN DEFAULT 0,
	IsActive BOOLEAN DEFAULT 0,
	Description VARCHAR(512)
);

CREATE TABLE RGMS_DB.Coordinators (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id)
);

CREATE TABLE RGMS_DB.GroupUserMaps (
	GroupId INT NOT NULL REFERENCES RGMS_DB.Groups(Id),
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id)
);

CREATE TABLE RGMS_DB.Meetings (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	Description VARCHAR(128),
	CreatedByUserId INT NOT NULL REFERENCES RGMS_DB.Users(Id),
	DateCreated DATETIME DEFAULT NOW(),
	DateDue DATETIME NULL DEFAULT NOW(), /* NOW() + 1 */
	GroupId INT REFERENCES RGMS_DB.Groups(Id)
);

CREATE TABLE RGMS_DB.DiscussionThreads (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	GroupId INT NOT NULL REFERENCES RGMS_DB.Groups(Id),
	ThreadName VARCHAR(100)
);

CREATE TABLE RGMS_DB.Documents (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	DocumentPath VARCHAR(128) NOT NULL, /* Root Folder */
	DocumentName VARCHAR(64) NOT NULL,
	VersionNumber INT NOT NULL,
	UploadDate DATETIME NOT NULL DEFAULT NOW() ,
	ThreadId INT NOT NULL REFERENCES RGMS_DB.DiscussionThreads(Id),
  GroupId INT NOT NULL REFERENCES RGMS_DB.Groups(Id)
);

CREATE TABLE RGMS_DB.DiscussionPosts (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	ThreadId INT NOT NULL REFERENCES RGMS_DB.DiscussionThread(Id),
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id),
	Message VARCHAR(512)
);

CREATE TABLE RGMS_DB.AccessRecords (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id),
	DateAccessed DATETIME NOT NULL DEFAULT NOW(),
	DocumentId INT NOT NULL REFERENCES RGMS_DB.Documents(Id)
);

CREATE TABLE RGMS_DB.Notifications (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id), /* Receiving User */
	GroupId INT REFERENCES RGMS_DB.Groups(Id), /* Receiving Group */
	Description VARCHAR(128),
	Link VARCHAR(512)
);

CREATE TABLE RGMS_DB.Sessions (
	Id INT PRIMARY KEY NOT NULL auto_increment,
	DateAccessed DATETIME NOT NULL DEFAULT NOW(),
	Persistent BOOLEAN NOT NULL DEFAULT false,
	UserId INT NOT NULL REFERENCES RGMS_DB.Users(Id)
);


/* Create Database User */

GRANT USAGE ON *.* TO 'rgms'@'localhost';
DROP USER 'rgms'@'localhost';

CREATE USER 'rgms'@'localhost' IDENTIFIED BY 'seng2050';
GRANT ALL PRIVILEGES ON *.* TO 'rgms'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

/* Insert Data */

INSERT INTO RGMS_DB.Groups (GroupName) VALUES ('Aggregates');

INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, StudentId,  Passphrase, IsAdmin, IsActive)
	VALUES('Admin', 'Admin', 'admin@rgms.com', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', true, true);

INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (1,1);