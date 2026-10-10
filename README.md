# NextShow Backend

## Project Overview
NextShow is an event-booking application backend developed using Java and Spring Boot.

## Technologies
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Maven

## Main Features
- User registration and login
- Event and show management
- Seat availability and booking
- Coupon and rewards management
- Ticket booking operations

## Database
The application uses MySQL. Configure database connection details through environment variables or a local configuration file.

## Running Locally
1. Install Java and MySQL.
2. Create the `nextshow` database.
3. Configure the database connection.
4. Run the application using the Maven wrapper:
   `.\mvnw.cmd spring-boot:run`

## Deployment
The backend is deployed as a web service and exposes REST APIs consumed by the NextShow frontend.
