# Campus Placement Tracking Portal

A full-stack web application for managing campus placements, built as a college DevOps project demonstrating a complete CI/CD pipeline, containerization, and automated configuration management.

---

## Problem Statement

Campus placement tracking is typically managed using spreadsheets and manual processes, causing data inconsistency, limited visibility, and difficult reporting. This portal centralizes student, company, placement drive, and application data with a clean web interface accessible to placement coordinators.

---

## Objectives

- Provide a centralized portal for placement coordinators to manage student profiles, companies, drives, and placement applications.
- Implement a complete DevOps workflow: Git branching, Jenkins CI/CD, Docker containerization, Selenium testing, and Ansible configuration management.
- Demonstrate automated provisioning, health checking, and recovery.

---

## Features

- **Dashboard** — Real-time summary cards for students, companies, drives, and placements with navigation links; alerts for unplaced students, upcoming drives, and pending interviews.
- **Students** — Create, list, search, update, and delete student profiles (name, department, CGPA, placement status).
- **Companies** — Manage company records (name, industry, package offered).
- **Placement Drives** — Manage recruitment drives linked to companies.
- **Applications** — Track student placement applications per drive with status (Applied / Interview Scheduled / Selected / Rejected).
- **Delete All** — Safe delete-all with confirmation to reset individual entities.

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.4 |
| Frontend | Thymeleaf + Bootstrap 5.3 |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL 15 (production), H2 (tests) |
| Build Tool | Apache Maven 3.9 |
| Testing | JUnit 5, Spring Boot Test, Selenium WebDriver 4.19 |
| CI/CD | Jenkins (Pipeline as Code) |
| Containerization | Docker / Docker Compose |
| Config Mgmt | Ansible 2.10.8 (WSL Ubuntu) |
| Version Control | Git / GitHub |

---

## Architecture Overview

```
Browser
  ↓  HTTP
Thymeleaf (Server-Side Templates)
  ↓
Spring MVC Controllers
  ↓
Service Layer (Business Logic)
  ↓
Spring Data JPA Repositories
  ↓
PostgreSQL (Docker container, port 5433 on host)
```

**Docker network:** `placement_net` connects `app` and `db` services.
**App connects to DB** via `db:5432` inside Docker (NOT `localhost:5433`).

---

## Prerequisites

- Java 17+
- Apache Maven 3.9+
- Docker Desktop (Windows)
- WSL2 with Ubuntu (for Ansible)
- Jenkins (running on port 8081)
- Google Chrome + ChromeDriver (for Selenium)

---

## Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/AARTI756/CampusPlacementPortal.git
cd CampusPlacementPortal
```

### 2. Local development (without Docker)

Ensure PostgreSQL is running locally on port 5433, with database `placement_db`, user `placement_user`, password `placement_password`.

```bash
mvn spring-boot:run
```

App available at: `http://localhost:8080`

### 3. Run with Docker Compose

```bash
docker compose -p campusplacementportal up -d
```

App available at: `http://localhost:8083`

PostgreSQL exposed on host port: `5433`

### 4. Stop containers (preserves database volume)

```bash
docker compose -p campusplacementportal down
```

> **Never use** `docker compose down -v` — it deletes the persistent database volume.

---

## Port Mapping

| Service | Host Port | Notes |
|---|---|---|
| App (local dev) | 8080 | `mvn spring-boot:run` |
| Jenkins | 8081 | CI/CD server |
| App (Task 8 JAR) | 8082 | Jenkins pipeline deployment |
| App (Docker) | 8083 | Docker Compose deployment |
| PostgreSQL | 5433 | Docker host port → container 5432 |

---

## Database Configuration

The application uses environment variable overrides with sensible defaults:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://127.0.0.1:5433/placement_db}
spring.datasource.username=${DB_USERNAME:placement_user}
spring.datasource.password=${DB_PASSWORD:placement_password}
```

Test profile (`application-test.properties`) uses H2 in-memory database.

---

## Running Unit Tests

```bash
mvn clean test -DskipSelenium=false -DbaseUrl=http://localhost:8083
```

Test results: **7 tests** (2 unit + 5 Selenium), **0 failures**.

JUnit XML reports generated in: `target/surefire-reports/`

---

## Running Selenium Tests

Selenium tests target a running application instance. Set the base URL:

```bash
mvn test -DbaseUrl=http://localhost:8083
```

Tests cover:
- Dashboard loads (`DashboardSeleniumTest`)
- Student CRUD (`StudentSeleniumTest`)
- Application status (`ApplicationSeleniumTest`)

---

## Jenkins Pipelines

| Job | Jenkinsfile | Purpose |
|---|---|---|
| `CampusPlacementPortal-CI` | (freestyle) | Basic Maven build & test |
| `CampusPlacementPortal-Pipeline` | `Jenkinsfile` | Full pipeline → deploys to port 8082 |
| `CampusPlacementPortal-Continuous-Testing` | `Jenkinsfile-Continuous-Testing` | Maven + Selenium quality gate |
| `CampusPlacementPortal-Docker-CD` | `Jenkinsfile-Docker-CD` | Docker build → deploy to port 8083 |

Jenkins URL: `http://localhost:8081`

---

## Ansible Provisioning & Reliability

**Prerequisites:** Ansible 2.10.8 in WSL Ubuntu (`wsl ansible --version`)

### Configuration management (Task 13)

```bash
wsl ansible-playbook -i ansible/inventory.ini ansible/site.yml
```

### Automated provisioning (Task 14)

```bash
wsl ansible-playbook -i ansible/inventory.ini ansible/provision.yml
```

### Reliability validation (Task 14)

```bash
wsl ansible-playbook -i ansible/inventory.ini ansible/reliability.yml
```

The reliability playbook:
- Verifies Docker CLI
- Checks both containers are running
- Performs HTTP health checks on all endpoints
- Auto-recovers the application container if stopped (without touching the DB)
- Fails clearly if PostgreSQL is unavailable

---

## Application URLs

| URL | Description |
|---|---|
| `http://localhost:8083/` | Dashboard |
| `http://localhost:8083/students` | Student management |
| `http://localhost:8083/companies` | Company management |
| `http://localhost:8083/drives` | Placement drives |
| `http://localhost:8083/applications` | Application tracking |

---

## Troubleshooting

See [`docs/TROUBLESHOOTING.md`](docs/TROUBLESHOOTING.md) for common issues.

---

## Known Limitations

- Selenium tests require the application to be running on port 8083 (`-DbaseUrl=http://localhost:8083`). If run without this flag, they will try `localhost:8080`.
- Docker registry push (Docker Hub) was not configured; images are local only.
- Ansible uses WSL with `cmd.exe` passthrough to call Docker; requires Docker Desktop to be running.
- No authentication/authorization (single-user system).
- No pagination on large data sets.

---

## Future Enhancements

- Role-based authentication (admin / placement officer / student view)
- Email notifications for interview scheduling
- Report generation (PDF/Excel export)
- REST API with Swagger documentation
- Docker Hub registry integration for image publishing
- Kubernetes deployment manifests
- Database migration with Flyway or Liquibase
