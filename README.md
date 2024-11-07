
                                            Banking Microservices Application
A secure RESTful microservices application built with Spring Boot. This project includes token-based authentication, 
an API gateway, Eureka service discovery, and modular endpoints for account and authentication services.

Table of Contents
 Prerequisites
 Installation
 Running the Project
 Database Setup
 Endpoints
 Authentication Endpoints
 Account Endpoints
 Example Requests and Responses

Prerequisites
  Java 17 or above
  Maven 3.6 or above
  MySQL 8.0 or a compatible database
  Postman or a REST client for testing
  Git for version control

1. Installation
 
Clone the repository:

git clone https://github.com/tanzil206/bank_project.git

cd bank_project

2. Configure Database credential in the Application Yaml

Update Database username and password at application.yaml in the Account, Auth services.

Example configurations in application.yaml of Account

  datasource:
    url: jdbc:mysql://localhost:3306/bankdb
    username: root
    password: admin1234
   
3.Install Dependencies:
   

Running the Project (Confirm DB server started)

Start the Eureka Server (Service Discovery):

Navigate to the Eureka Server directory:

cd Service-Registry
mvn spring-boot:run

Run the API Gateway:

cd ../API-Gateway
mvn spring-boot:run


Run the Authentication Service:

cd ../Auth
mvn spring-boot:run


Run the Account Service:

cd ../Account
mvn spring-boot:run

The services should now be running on the following ports:

Eureka Server: http://localhost:8761
API Gateway: http://localhost:8060
Authentication Service: http://localhost:8082
Account Service: http://localhost:8083



4. Database Setup

Create the MySQL Database:


CREATE DATABASE bank_db;


Use the Database Script to create necessary tables:


CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_number` varchar(255) DEFAULT NULL,
  `account_status` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)


Insert Sample Data


INSERT INTO ACCOUNT (account_number, customer_name, balance, account_status,account_type)
VALUES
    ('ACC123456', 'Alice Smith', 1500.00, 'ACTIVE','Current'),
    ('ACC234567', 'Bob Johnson', 250.50, 'INACTIVE','Saving'),
    ('ACC345678', 'Charlie Davis', 3000.00, 'FROZEN','Current'),
    ('ACC456789', 'Diana Evans', 500.75, 'CLOSED','Saving'),
    ('ACC567890', 'Evan Wright', 4500.00, 'ACTIVE','Saving');


5.Authentication Endpoints
   Login: POST http://localhost:8060/auth/login
   SignUp: POST http://localhost:8060/auth/signup
 Account Endpoints
   Get Account Details: GET http://localhost:8060/accounts/customer/{accountNumber}
   
Example Requests and Responses

a. Authentication Endpoints

SignUp

POST http://localhost:8060/auth/signup

Request

{
    "email": "tan@gmail.com",
    "userName": "tanzil2",
    "fullName": "Tanzir Ahmed",
    "password": "123"
}

Response

"message": "User registered successfully"

Login

POST http://localhost:8060/auth/login

Request

{
    "userName": "tanzil2",
    "password": "123"
}

Response

{
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0YW56aWwyIiwiaWF0IjoxNzMxMDEwMjE5LCJleHAiOjE3MzEwMTM4MTl9.srDs4WhvhFN7tIkiw5N8o0NaMWUskM2ja5mNZkbKYnySz56GqfF8s4Ww9IZqek8h",
    "expiresIn": 3600000
}


b. Account Endpoints

Fetch Customer Account Details

Request:

GET http://localhost:8060/accounts/customer/ACC123456

Header -> 
Authorization = Bearer {{token}}

Response:

{
    "id": 1,
    "customerName": "Alice Smith",
    "accountNumber": "ACC123456",
    "accountType": "Current",
    "balance": 1500.00,
    "accountStatus": "ACTIVE"
}


