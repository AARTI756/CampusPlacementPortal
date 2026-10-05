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
- **To Do:** Tasks 12-15 (Jenkins-Docker CD, Ansible, Provisioning, Final Release)
- **In Progress:** None
- **Done:** Tasks 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 (MVP Development, Scope, Architecture, Git init, Branching, MVP Completion, Jenkins CI, Pipeline as Code, Selenium Design, Continuous Testing, Docker Lifecycle)

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


## 9. Task 8 — Pipeline as Code and Server Deployment

### Jenkins Pipeline Job: CampusPlacementPortal-Pipeline
- **Job Type:** Pipeline (from SCM)
- **Job Name:** `CampusPlacementPortal-Pipeline`
- **Jenkins URL:** http://localhost:8081
- **Pipeline Definition:** `Jenkinsfile` (stored in repository root)
- **SCM Repository:** `https://github.com/AARTI756/CampusPlacementPortal.git`
- **Branch:** `*/develop`
- **Script Path:** `Jenkinsfile`

### Pipeline Stages
| Stage | Description | Result |
|-------|-------------|--------|
| Checkout | Git clone of `develop` branch | SUCCESS |
| Build & Test | `mvn clean test` — all JUnit tests | SUCCESS |
| Package | `mvn package -DskipTests` — produces executable JAR | SUCCESS |
| Deploy | Copies JAR to `C:\CampusPlacementPortal\deploy`, starts on port 8082 | SUCCESS |
| Verify | HTTP health check `http://localhost:8082/` — expects HTTP 200 | SUCCESS |

### Deployment Details
- **Deployment Method:** Local Spring Boot JAR deployment (no Docker — reserved for Task 11)
- **Deploy Directory:** `C:\CampusPlacementPortal\deploy\`
- **JAR File:** `placement-portal-0.0.1-SNAPSHOT.jar`
- **Deployment Port:** `8082` (avoids conflict with dev app on 8080 and Jenkins on 8081)
- **JVM Args:** `-Duser.timezone=Asia/Kolkata`
- **Deployment Verification:** `Invoke-WebRequest http://localhost:8082/` — HTTP 200 OK

### Build Results
- **Build #2 Result:** SUCCESS
- **Duration:** ~81 seconds
- **Console confirmation:** `HTTP Status: 200` / `Deployment verified successfully!`


## Task 9 — Selenium Test Design and Local Execution
- **Frameworks Used:** Selenium WebDriver 4.x, JUnit 5, Chrome headless (for stability/background execution).
- **Test Scenarios Implemented:**
  1. `DashboardSeleniumTest.testDashboardLoads` - Dashboard and Navigation validation.
  2. `StudentSeleniumTest.testCreateStudent` - E2E Form Submission for Student Creation.
  3. `StudentSeleniumTest.testStudentSearch` - Search and Result Table validation.
  4. `ApplicationSeleniumTest.testApplicationStatusWorkflow` - Filtering table values by Select Option.
  5. `StudentSeleniumTest.testDeleteStudent` - Deletion and javascript confirmation alert handling.
- **Execution Strategy:** Tests rely on `baseUrl` property (defaulting to `http://localhost:8080`) to interact with the active Spring Boot environment without wiping data globally. Test isolation achieved using unique UUIDs for mock data. Wait states are handled securely via `WebDriverWait` explicit conditions.
- **Result:** Successfully built and executed via `mvn test -Dtest="*SeleniumTest"`, resulting in a passing test suite on the `feature/selenium-tests` branch.

## 10. Task 10 - Continuous Testing in Jenkins

### Objective
Configure Jenkins so that the existing Selenium/UI tests and required Maven tests can be automatically executed as part of Jenkins continuous testing.

### Jenkins Continuous Testing Job
- **Job Type:** Pipeline (from SCM)
- **Job Name:** CampusPlacementPortal-Continuous-Testing
- **Jenkins URL:** http://localhost:8081
- **SCM Repository:** https://github.com/AARTI756/CampusPlacementPortal.git
- **Branch:** */feature/continuous-testing
- **Pipeline Definition:** Jenkinsfile-Continuous-Testing

### Pipeline Workflow
1. **Checkout:** Pulls the latest code from feature/continuous-testing.
2. **Maven Unit Tests:** Executes mvn clean test -Dtest="PlacementPortalApplicationTests" to ensure backend logic and database mappings are correct.
3. **Build & Package:** Executes mvn package -DskipTests to build the application JAR.
4. **Start Application:** Starts the Spring Boot application as a background process on port 8080. Waits and verifies the application is HTTP 200 UP before proceeding.
5. **Selenium UI Tests:** Executes the Selenium suite mvn test -Dtest="*SeleniumTest" "-DbaseUrl=http://localhost:8080" against the live application.
6. **Cleanup:** Forcefully stops the background application process on port 8080 using PowerShell to avoid port binding conflicts in subsequent builds.
7. **Publish Test Results:** Uses the JUnit plugin to publish Surefire XML reports (target/surefire-reports/*.xml) to the Jenkins dashboard.

### Expected Success / Results
- **Reporting:** Test results (Pass/Fail metrics, durations) are visible inside the Jenkins build interface.
- **Resilience:** The pipeline successfully starts the application, tests the application via headless Chrome, and correctly cleans up the Spring Boot process upon both success and failure. A failed test properly fails the Jenkins build.




## 11. Task 11 - Docker Image and Container Lifecycle

### Objective
Containerize the Campus Placement Tracking Portal and demonstrate the complete Docker image/container lifecycle using a production-appropriate multi-stage Dockerfile and Docker Compose setup.

### Docker Architecture
- **Application Container:** `campus-placement-portal:latest` (built via multi-stage Dockerfile)
- **Database Container:** `postgres:15` (existing image)
- **Network:** Shared bridge network `placement_net` for internal resolution.
- **Port Mapping:** Host port `8083` maps to Container port `8080`.
- **Database Connection:** Application connects internally via `jdbc:postgresql://db:5432/placement_db`. Local port `5433` is preserved for direct host access.
- **Volumes:** PostgreSQL uses `postgres_data` volume to persist placement data across application/database restarts.

### Image & Container Details
- **Dockerfile:** Multi-stage build (Stage 1: Maven build, Stage 2: JRE 17 Alpine runtime)
- **Image Names:** `campus-placement-portal:v1`, `campus-placement-portal:latest`
- **Container Name:** `campusplacementportal-app-1`
- **Verification URL:** `http://localhost:8083/`

### Lifecycle Commands Demonstrated
- **Build Image:** `docker build -t campus-placement-portal:v1 -t campus-placement-portal:latest .`
- **List Images:** `docker images campus-placement-portal`
- **Inspect Image:** `docker image inspect campus-placement-portal:v1`
- **Start Compose Stack:** `docker compose up -d`
- **List Containers:** `docker ps` and `docker ps -a`
- **Inspect Container:** `docker inspect campusplacementportal-app-1`
- **Logs:** `docker logs campusplacementportal-app-1`
- **Stop:** `docker stop campusplacementportal-app-1`
- **Start:** `docker start campusplacementportal-app-1`
- **Restart:** `docker restart campusplacementportal-app-1`
- **Remove Container:** `docker rm -f campusplacementportal-app-1`

### Cleanup Procedure
To gracefully stop the environment without destroying the database:
`docker compose stop`

To completely remove the containers and network (but preserve the volume data):
`docker compose down`

To remove everything including the database volume (destructive):
`docker compose down -v`
