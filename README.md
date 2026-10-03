# DevOps-Enabled Campus Placement Tracking Portal

This is an MVP application for a college DevOps project, demonstrating a complete DevOps lifecycle.

## Technology Stack
* Backend: Java 17, Spring Boot, Spring Data JPA
* Frontend: Server-rendered HTML with Thymeleaf and Bootstrap
* Database: PostgreSQL
* Build Tool: Maven

## Features
- **Dashboard:** See total students, companies, drives, and placement stats. Also includes simple alerts for unplaced students.
- **Students Management:** Add and view students.
- **Companies Management:** Add and view recruiting companies.
- **Placement Drives:** Create drives with specific eligibility criteria.
- **Application Tracking:** Apply students to drives and track their status (APPLIED, SHORTLISTED, INTERVIEW, SELECTED, REJECTED).

## Prerequisite
- Java 17
- Maven
- Docker (for PostgreSQL database)

## Setup Instructions

### 1. Start PostgreSQL Database
Using Docker Compose, start the database:
```bash
cd CampusPlacementPortal
docker-compose up -d
```
This will start a PostgreSQL instance with:
- DB Name: `placement_db`
- User: `placement_user`
- Password: `placement_password`
- Port: `5432`

### 2. Build the Application
Run the Maven build to compile and package the application:
```bash
mvn clean install
```

### 3. Run the Application
Run the Spring Boot application:
```bash
mvn spring-boot:run
```
Alternatively, run the generated `.jar` file:
```bash
java -jar target/placement-portal-0.0.1-SNAPSHOT.jar
```

### 4. Access the UI and APIs
- UI Application: [http://localhost:8080/](http://localhost:8080/)
- REST APIs: `http://localhost:8080/api/v1/`
  - `/api/v1/students`
  - `/api/v1/companies`
  - `/api/v1/drives`
  - `/api/v1/applications`

## DevOps Preparation
This project is structured for easy integration with:
- **Git & GitHub** (already initialized)
- **Jenkins CI/CD** (Spring Boot can be packaged easily)
- **Docker** (can be containerized using a `Dockerfile`)
- **Ansible** (configuration management)
- **Selenium** (reliable IDs/classes can be added to the UI for E2E testing)
