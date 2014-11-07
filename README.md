Research Group Management System
================================

# Abstract #

This application was built as an assignment for SENG2050 - Web Engineering at the University of Newcastle 2014. It aims to implement a java servlet application for a Research Groups. Members are able to log into the application, upload research documents and comment on each other's work.

# Submission Notes #

* Run gradle build to generate WAR file
* WAR file can be found in build/libs
* Replace .class files with .java files in rgms package
* Add javax.servlet-api-3.0.1 to the WEB-INF/libs folder
* Package with Documentation (PDF versions) and SQL Scripts

# Developer Notes #

## Clean Install ##

* Download [Gradle](https://services.gradle.org/distributions/gradle-2.1-all.zip "Gradle 2.1 Build")
* Download [MySql Server](http://dev.mysql.com/downloads/mysql/ "MySQL Server")
* Download [MySQL Workbench](http://dev.mysql.com/downloads/workbench/ "MySQL Workbench")
* Clone the repository using Github
* Install the Database
	* Create Database using "CreateDatabase.sql"
* Build Gradle project using:
	* *gradle build*
	* *gradle eclipseWpt*


## Gradle Dependencies ##

21/10/2014 - Since Gradle will install all dependencies to the user's local drive (and not the repo), all developers will need to run " *gradle eclipseWpt* " to set the dependencies back to the user's local.


[![Build Status](https://travis-ci.org/TylerHaigh/seng2050-assignment3.svg?branch=master)](https://travis-ci.org/TylerHaigh/seng2050-assignment3)