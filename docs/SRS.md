# Software Requirements Specification (SRS) Summary

## 1. Introduction
This document outlines the software requirements for the Minimum Viable Product (MVP) of the DevOps-Enabled Campus Placement Tracking Portal. It provides the functional and non-functional requirements that dictate the application's current capabilities.

## 2. Functional Requirements
- **FR1 (Dashboard):** The system shall display total students, companies, placement drives, students placed, and students in-process. It shall also display basic alerts (e.g., unplaced students count).
- **FR2 (Student Management):** The system shall allow users to add new students (Name, Email, Department, CGPA) and list all existing students.
- **FR3 (Company Management):** The system shall allow users to add new recruiting companies and list all existing companies.
- **FR4 (Placement Drives):** The system shall allow users to create a placement drive associated with a company, setting a job role, date, and minimum CGPA criteria.
- **FR5 (Application Tracking):** The system shall allow linking a student to a placement drive, creating an application.
- **FR6 (Status Updates):** The system shall allow users to update the status of an application (APPLIED, SHORTLISTED, INTERVIEW, SELECTED, REJECTED).
- **FR7 (Search):** The system shall allow searching for students (by name/department) and companies (by name).

## 3. Non-Functional Requirements
- **NFR1 (Performance):** The web pages should load quickly and handle concurrent local users gracefully.
- **NFR2 (Reliability):** The system must not crash on invalid data inputs (basic HTML5 and Hibernate validation is in place).
- **NFR3 (Maintainability):** The code must follow a standard Spring Boot MVC structure to allow easy integration with Jenkins and SonarQube in later phases.
- **NFR4 (Portability):** The application must be capable of running independently via a `.jar` file and inside a Docker container.
- **NFR5 (Database Integrity):** The database must ensure data constraints, such as unique email addresses for students and a unique student-drive application pairing.

## 4. API List
The application exposes the following REST API endpoints (accessible under `/api/v1`):
- `GET /api/v1/students` - Retrieves a list of all students.
- `POST /api/v1/students` - Creates a new student record.
- `GET /api/v1/companies` - Retrieves a list of all companies.
- `POST /api/v1/companies` - Creates a new company record.
- `GET /api/v1/drives` - Retrieves a list of all placement drives.
- `POST /api/v1/drives` - Creates a new placement drive.
- `GET /api/v1/applications` - Retrieves a list of all applications.

## 5. Local Development Setup
- **Java:** Version 17
- **Build Tool:** Maven
- **Database:** PostgreSQL (Configured via Docker Compose for easy local setup. An H2 in-memory DB is also configured for test profiles and CI pipelines).
- **Run Command:** `mvn spring-boot:run`

## 6. Technology Selection Justification
- **Java & Spring Boot:** The industry standard for robust, scalable enterprise backends. Perfect for teaching MVC, REST, and Dependency Injection. Highly compatible with Jenkins.
- **Thymeleaf:** Server-side rendering ensures the UI is lightweight, fast, and extremely easy to test later using Selenium WebDriver because DOM IDs and structures remain static.
- **PostgreSQL:** A powerful, open-source relational database that is standard in production environments, making it ideal for the Docker/Ansible DevOps stages.
- **Docker Compose:** Simplifies the setup of the PostgreSQL database without requiring a native local installation on every developer's machine.
