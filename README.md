# Clinic-Centre-System

## Introduction
Web based application made for patients and Clinic Centre. Made for a course at Faculty of technical sciences in Novi Sad.

### Technologies used:
- **Backend:** Java + SpringBoot + PostgreSQL
- **Frontend:** JQuery + HTML + CSS


### Authors
- @Milanovic-dev
- @milanatucakov
- @MKnezevic96

### Quick start
#### You will need:

###### For running
- Java 8 + SpringBoot
- Maven 3.6 or higher
- PostgreSQL 10 or higher

###### For testing
- JUnit
- Chrome 80 or higher

#### Creating the database
- In order for application to run, it will need a database. Default name is set as postgres, running on port `5432`. 
-  Postgres tutorial: https://www.postgresql.org/docs/10/tutorial-createdb.html

### Building the project

- Position to root folder

 `cd MedicalSystemApplication`

- Install and wait for Tests to finish **or** install without tests

 `mvnw clean install` **or** `mvnw clean install -DskipTests`

- Run on localhost

 `java -jar ./target/MedicalSystemApplication-0.1.jar`

 -  Site should now be accessible at http://localhost:8282/
