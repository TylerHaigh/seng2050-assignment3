Research Group Management System
================================

Assignment for UoN SENG2050. Implement a system similar to Blackboard

# Developer Notes #

## Clean Install ##

* Download [Gradle](https://services.gradle.org/distributions/gradle-2.1-all.zip "Gradle 2.1 Build")
* Download [MySql Server](http://dev.mysql.com/downloads/mysql/ "MySQL Server")
* Download [MySQL Workbench](http://dev.mysql.com/downloads/workbench/ "MySQL Workbench")
* Clone the repository using Github
* Install the Database
	* Create Database using <CreateDatabase.sql>
	* Create RGMS User using <CreateDatabaseUser.sql>
	* Insert Initial Data using <InsertData.sql>
* Build Gradle project using:
	* *gradle build*
	* *gradle eclipseWpt*


## Gradle Dependencies ##

21/10/2014 - Since Gradle will install all dependencies to the user's local drive (and not the repo), all developers will need to run " *gradle eclipseWpt* " to set the dependencies back to the user's local.


[![Build Status](https://travis-ci.org/TylerHaigh/seng2050-assignment3.svg?branch=master)](https://travis-ci.org/TylerHaigh/seng2050-assignment3)