# Agile Planning and DevOps Workflow

## 1. User Stories
1. **As a placement coordinator,** I want to view a dashboard with overall placement statistics so that I can quickly assess the progress of the placement season.
2. **As a placement coordinator,** I want to add new students and companies into the system so that I can manage their records centrally.
3. **As a placement coordinator,** I want to create a placement drive with specific criteria (like minimum CGPA) so that I can match eligible students to company roles.
4. **As a student,** I want my application status to be tracked through the portal so that I know where I stand in the hiring process (e.g., Shortlisted, Interview, Selected).
5. **As an administrator,** I want to search for specific students or companies quickly so that I don't have to scroll through large lists.

## 2. Acceptance Criteria
- **Dashboard:** Must display total counts for students, companies, and drives, as well as the number of students placed and unplaced.
- **Data Management:** The system must validate mandatory fields (e.g., Email, Name, CGPA) before saving student or company records.
- **Application Tracking:** Application statuses must be limited to standard stages: APPLIED, SHORTLISTED, INTERVIEW, SELECTED, REJECTED.
- **Status Sync:** When a student's application status changes to "SELECTED", their global `isPlaced` flag must automatically update to true.

## 3. Product Backlog
1. Set up Spring Boot project with Maven and PostgreSQL.
2. Implement backend database entities (Student, Company, PlacementDrive, Application).
3. Develop RESTful APIs for CRUD operations.
4. Create Thymeleaf-based UI templates (Dashboard, Forms, Data Tables).
5. Add search functionality for students and companies.
6. Containerize the database using Docker Compose.
7. Setup Jenkins CI/CD pipeline.
8. Implement Selenium E2E automated tests.
9. Write Ansible configuration management scripts.

## 4. Tasks Corresponding to the College Assignment (15 Tasks)
1. Problem Definition and Scope
2. Agile Planning and DevOps Workflow
3. Requirements, Architecture and Technology Setup
4. Git and GitHub Repository Initialization
5. Feature Development with Branching
6. MVP Completion and Git Collaboration
7. Jenkins Installation and Continuous Integration Job
8. Pipeline as Code and Server Deployment
9. Selenium Test Design and Local Execution
10. Continuous Testing in Jenkins
11. Docker Image and Container Lifecycle
12. Jenkins-Docker Continuous Deployment
13. Configuration Management Script
14. Automated Provisioning and Reliability Validation
15. Final End-to-End Release, Documentation and Viva

## 5. Kanban/Scrum Plan
- **To Do:** Tasks 8-15 (Pipeline as Code, Selenium, Docker, Ansible)
- **In Progress:** None
- **Done:** Tasks 1, 2, 3, 4, 5, 6, 7 (MVP Development, Scope, Architecture, Git init, Branching, MVP Completion, Jenkins CI)

## 6. Definition of Done (DoD)
- Code compiles without errors using `mvn clean package`.
- All unit tests pass (`mvn clean test`).
- Application runs locally and connects successfully to the database.
- Features meet the Acceptance Criteria of the related User Story.
- Code is committed and pushed to the Git repository.
- Documentation accurately reflects the current state of the application.

## 7. DevOps Lifecycle / Workflow
1. **Plan:** Agile planning, defining User Stories and Acceptance Criteria.
2. **Code:** Developing the Spring Boot MVP using Git for version control and branching.
3. **Build:** Using Maven to compile the code and resolve dependencies.
4. **Test:** Automated unit testing via JUnit (and later E2E via Selenium).
5. **Release & Deploy:** Using Jenkins CI/CD to package the application and deploy it as a Docker container.
6. **Operate & Monitor:** Using configuration management tools (Ansible) to ensure reliable provisioning and monitoring the application state.

## 8. Task 7 — Jenkins Installation and CI Job

### Jenkins Environment
- **Jenkins Version:** 2.568.2
- **Installed as:** Windows service (`Jenkins`)
- **Jenkins URL:** http://localhost:8081
- **Note:** Application (Campus Placement Portal) runs on port 8080; Jenkins runs on port 8081 to avoid conflicts.

### Jenkins CI Job: CampusPlacementPortal-CI
- **Job Type:** Freestyle project
- **Job Name:** `CampusPlacementPortal-CI`
- **Source Code Management:** Git
  - Repository URL: `https://github.com/AARTI756/CampusPlacementPortal.git`
  - Branch: `*/develop`
- **Build Tool:** Maven (`Maven-3.9.16`, located at `C:\DevTools\apache-maven-3.9.16`)
- **Build Command:** `mvn clean test package`
- **Build #1 Result:** SUCCESS (Duration: ~96 seconds)

### What the CI Job Validates
1. Clones the `develop` branch from GitHub.
2. Runs `mvn clean` to ensure a clean build state.
3. Executes `mvn test` — runs the Spring Boot integration test suite (H2 in-memory database, no external dependencies needed).
4. Runs `mvn package` — compiles all 13 source files and packages the application into a Spring Boot executable JAR (`placement-portal-0.0.1-SNAPSHOT.jar`).
5. Fails the build if any test fails or if Maven compilation errors occur.
6. Reports a clear SUCCESS or FAILURE result in the Jenkins dashboard.

