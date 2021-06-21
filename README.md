# Banking Application

## Project Description

This is a mock banking application to demonstrate use of java servlets. Included is also a
custom Object-Relational-Mapping framework to abstract boilerplate JDBC logic.

## Technologies Used (brief overview)

* Java - version 8
* PostgreSQL - version 12
* Apache Maven
* Apache Tomcat
* Postman
* AWS RDS
* AWS CodeBuild
* AWS CodePipeline
* AWS S3
* JUnit
* Mockito
* Jackson Databind

## Features

* Users can create an account(s).
* Users can deposit and withdraw funds
* Users can transfer funds between accounts

To-do list:
* Implement joint accounts
* Implement date times (requires figuring out how Jackson handles dates)

## Getting Started

* Clone this repository using the following command: `git clone https://github.com/210426-java-react-enterprise/team-c-p1-webapp.git`
* Switch to the richard-dev branch using the command: `git checkout richard-dev`
* In src/main/resources, create an application.properties file using the following values(omit the angle brackets):
```
db_url=<your database goes here>
db_login=<your DB username>
db_password=<your DB password>
```
* Run the db_script.sql in your database to create the required tables
* Be sure to have an environment with Java 8, Apache Maven and Postman

## Usage

* Hit the servlet endpoints using postman with JSON representing the objects.

## Contributors

* Sean Taba
* Oswaldo Castillo
