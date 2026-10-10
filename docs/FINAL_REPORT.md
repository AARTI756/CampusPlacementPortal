# Campus Placement Tracking Portal — Final Project Report

**Course:** DevOps-Enabled Software Engineering  
**Project:** Campus Placement Tracking Portal  
**Repository:** https://github.com/AARTI756/CampusPlacementPortal  
**Version:** 1.0.0  

---

## 1. Abstract

This report documents the design, implementation, and DevOps workflow of the Campus Placement Tracking Portal — a college project demonstrating a complete software delivery pipeline from requirements through containerized deployment and automated configuration management. The portal provides placement coordinators with a web interface to manage students, companies, placement drives, and applications. The project implements 15 tasks covering Git-based collaboration, Jenkins CI/CD, Selenium-based automated testing, Docker containerization, and Ansible provisioning.

---

## 2. Problem Statement

Campus placement coordination involves tracking hundreds of students across multiple companies and recruitment drives. Manual processes using spreadsheets cause:

- Data inconsistency between departments
- No real-time view of placement status
- Difficulty generating placement statistics
- High administrative overhead

**Solution:** A centralized Spring Boot web portal with full CRUD operations, a summary dashboard, and an automated DevOps pipeline ensuring quality, repeatability, and reliability of deployments.

---

## 3. Objectives and Scope

**In scope:**
- Student, company, placement drive, and application management
- Real-time dashboard with placement statistics
- Git branching strategy and pull request workflow
- Jenkins CI, pipeline-as-code, and continuous testing
- Selenium WebDriver automated UI tests
- Docker containerization with persistent PostgreSQL storage
- Ansible configuration management, provisioning, and reliability validation
- Complete documentation and viva preparation

**Out of scope:**
- Student self-service portal (login/auth)
- Email notification system
- PDF report generation
- Cloud deployment (AWS/GCP/Azure)

---

## 4. Stakeholders and Requirements

| Stakeholder | Need |
|---|---|
| Placement Coordinator | Manage students, companies, drives, applications |
| College Administration | View placement statistics and reports |
| Developer | Maintainable, testable, deployable codebase |
| DevOps Engineer | Automated pipeline, containerized deployment |

**Functional Requirements:**
- CRUD for students, companies, drives, applications
- Dashboard with live counts and alerts
- Search and filter for students
- Application status tracking (Applied → Interview → Selected/Rejected)
- Delete-all with confirmation

**Non-Functional Requirements:**
- Application must start within 30 seconds
- All pages must respond within 3 seconds
- Tests must pass before deployment
- Zero data loss during container restart
- Idempotent provisioning

---

## 5. Architecture

```
┌─────────────────────────────────────────────────────┐
│                    Browser (User)                     │
└────────────────────────┬────────────────────────────┘
                         │ HTTP :8083
┌────────────────────────▼────────────────────────────┐
│        Docker Container: campusplacementportal-app-cd │
│  ┌──────────────────────────────────────────────┐    │
│  │         Spring Boot Application              │    │
│  │  ┌─────────────┐   ┌──────────────────────┐  │    │
│  │  │  Thymeleaf  │   │  Spring MVC          │  │    │
│  │  │  Templates  │   │  Controllers         │  │    │
│  │  └─────────────┘   └──────────┬───────────┘  │    │
│  │                               │              │    │
│  │                    ┌──────────▼───────────┐  │    │
│  │                    │  Service Layer       │  │    │
│  │                    └──────────┬───────────┘  │    │
│  │                               │              │    │
│  │                    ┌──────────▼───────────┐  │    │
│  │                    │  Spring Data JPA     │  │    │
│  └────────────────────┼──────────────────────┘  │    │
│                       │ :5432 (internal)         │    │
└───────────────────────┼─────────────────────────┘    │
                        │  Docker Network: placement_net │
┌───────────────────────▼─────────────────────────────┐
│      Docker Container: campusplacementportal-db-1     │
│                    PostgreSQL 15                      │
│              Volume: postgres_data                   │
└─────────────────────────────────────────────────────┘
```

---

## 6. Data Model

```
students (id, name, department, cgpa, email, is_placed)
    │
    │ 1:many
    ▼
placement_applications (id, student_id, drive_id, status, application_date)
    │
    │ many:1
    ▼
placement_drives (id, company_id, drive_date, location, description)
    │
    │ many:1
    ▼
companies (id, name, industry, package_offered)
```

---

## 7. Technology Selection

| Technology | Rationale |
|---|---|
| Spring Boot | Rapid Java web development; embedded Tomcat; strong JPA/testing support |
| Thymeleaf | Server-side rendering; natural HTML templates; no separate frontend build |
| PostgreSQL | Robust RDBMS; excellent Docker support; persistent volumes |
| H2 | In-memory DB for tests; no external service required for unit testing |
| Maven | Industry-standard Java build; lifecycle integration with Jenkins |
| JUnit 5 | Modern Java test framework; parametrized tests; Spring Boot integration |
| Selenium | Browser automation; real end-to-end UI validation |
| Jenkins | Self-hosted CI; Pipeline as Code via Jenkinsfile; widely used in industry |
| Docker | Reproducible environment; eliminates "works on my machine" problem |
| Ansible | Agentless configuration management; YAML playbooks; idempotency |
| Git/GitHub | Distributed version control; branch-based collaboration; pull requests |

---

## 8. All 15 Tasks Summary

| # | Task | Branch | Key Deliverable | Status |
|---|---|---|---|---|
| 1 | MVP Feature Development | main/develop | Spring Boot app with all CRUD | ✅ Done |
| 2 | Project Scope & Planning | docs | Requirements doc in DEVOPS_WORKFLOW.md | ✅ Done |
| 3 | Software Architecture | docs | Architecture documented | ✅ Done |
| 4 | Git Repository & Branching | main | GitHub repo, issue templates, branch policy | ✅ Done |
| 5 | Feature Branch & PR | feature/* | Pull requests merged, branch protection | ✅ Done |
| 6 | MVP Completion | develop | All features integrated | ✅ Done |
| 7 | Jenkins CI | main | CampusPlacementPortal-CI freestyle job | ✅ Done |
| 8 | Jenkins Pipeline as Code | Jenkinsfile | CampusPlacementPortal-Pipeline; deploys to 8082 | ✅ Done |
| 9 | Selenium Test Design | feature/selenium-tests | 5 Selenium tests + test plan | ✅ Done |
| 10 | Continuous Testing | feature/continuous-testing | CampusPlacementPortal-Continuous-Testing; quality gate | ✅ Done |
| 11 | Docker Containerization | feature/docker-containerization | Dockerfile, .dockerignore, docker-compose.yml | ✅ Done |
| 12 | Jenkins-Docker CD | feature/jenkins-docker-cd | CampusPlacementPortal-Docker-CD; image v2 | ✅ Done |
| 13 | Ansible Config Management | feature/ansible-config-management | ansible/site.yml; idempotent verification | ✅ Done |
| 14 | Ansible Provisioning | feature/provisioning-reliability | ansible/provision.yml + reliability.yml | ✅ Done |
| 15 | Final Release | release/final-v1.0.0 | v3 image; full docs; release tag v1.0.0 | ✅ Done |

---

## 9. Git Collaboration and Branching Strategy

**Branching model:** GitHub Flow with feature branches

```
main ←────────────────────────────── stable releases
  │
develop ←─────────────────────────── integration branch
  │
  ├── feature/student-search
  ├── feature/application-status-dashboard
  ├── feature/data-management-delete
  ├── feature/selenium-tests
  ├── feature/continuous-testing
  ├── feature/docker-containerization
  ├── feature/jenkins-docker-cd
  ├── feature/ansible-config-management
  ├── feature/provisioning-reliability
  └── release/final-v1.0.0
```

**Commit conventions:** `feat:`, `fix:`, `docs:`, `test:`, `chore:`

---

## 10. Jenkins CI/CD Workflow

```
Code Push → GitHub
     ↓
Jenkins Checkout (SCM polling / manual trigger)
     ↓
Maven Unit Tests (PlacementPortalApplicationTests)
     ↓ FAIL → build fails, no deploy
     ↓ PASS
Maven Package (produces JAR)
     ↓
Docker Build (multi-stage: Maven builder → Eclipse Temurin JRE)
     ↓
Docker Stop/Remove old container
     ↓
Docker Run new container (port 8083)
     ↓
Health Check (HTTP 200 on /)
     ↓
Publish JUnit Test Results
```

**Jenkins jobs:**
- `CampusPlacementPortal-CI` — Freestyle; Task 7
- `CampusPlacementPortal-Pipeline` — Scripted pipeline; deploys JAR to port 8082
- `CampusPlacementPortal-Continuous-Testing` — Pipeline with quality gate; Selenium tests
- `CampusPlacementPortal-Docker-CD` — Docker build & deploy pipeline

---

## 11. Selenium Test Strategy

**Framework:** JUnit 5 + Selenium WebDriver 4.19.1 + ChromeDriver (headless)

**Test classes:**
| Class | Tests | Coverage |
|---|---|---|
| `DashboardSeleniumTest` | 1 | Dashboard page loads with correct title |
| `ApplicationSeleniumTest` | 1 | Applications page accessible |
| `StudentSeleniumTest` | 3 | Student create, search, delete workflows |
| `PlacementPortalApplicationTests` | 2 | Unit: student CRUD via service layer |

**Total: 7 tests — 0 failures, 0 skipped**

**Base URL configuration:** Tests read `baseUrl` system property (default: `http://localhost:8080`).  
For Docker: `mvn test -DbaseUrl=http://localhost:8083`

---

## 12. Docker Containerization

**Dockerfile:** Multi-stage build
- Stage 1: Maven + JDK 17 → compiles and packages JAR
- Stage 2: Eclipse Temurin JRE 17 Alpine → runs JAR with minimal image size

**Images:**
| Tag | Notes |
|---|---|
| `campus-placement-portal:v1` | Task 11 initial image |
| `campus-placement-portal:v2` | Task 12 Jenkins-CD image |
| `campus-placement-portal:v3` | Task 15 final release image (with UI improvements) |
| `campus-placement-portal:latest` | Points to v3 |

**Persistence:** Named volume `postgres_data` — survives container replacement.

**Environment variables in container:**
```
DB_URL=jdbc:postgresql://db:5432/placement_db
DB_USERNAME=placement_user
DB_PASSWORD=placement_password
```

---

## 13. Ansible Configuration Management

**Ansible version:** 2.10.8 (Python 3.10.12, WSL Ubuntu 22.04)  
**Connection mode:** `ansible_connection=local` (WSL local; Docker called via `cmd.exe`)

**Playbooks:**
| Playbook | Purpose |
|---|---|
| `ansible/site.yml` | Task 13: verify environment, containers, and endpoints |
| `ansible/provision.yml` | Task 14: ensure Docker Compose stack is running |
| `ansible/reliability.yml` | Task 14: health check + auto-recovery |

**Idempotency results:**
- `site.yml`: `ok=19 changed=0` on both runs
- `provision.yml` (idle): `ok=5 changed=0` on both runs  
- `reliability.yml` (idle): `ok=9 changed=0` on both runs
- `reliability.yml` (recovery): `ok=10 changed=1` (container restarted)

---

## 14. Provisioning, Idempotency and Recovery

**Recovery procedure demonstrated:**
1. Stopped app container: `docker stop campusplacementportal-app-cd`
2. Ran `provision.yml` → `changed=1` (app restarted via `docker compose up -d`)
3. Ran `reliability.yml` → all HTTP 200, `changed=0`
4. Stopped app again, ran `reliability.yml` alone → detected stopped container, issued `docker start`, retried HTTP check → `changed=1`, PASSED

**PostgreSQL persistence:** Volume `postgres_data` preserved throughout all stop/start/recovery cycles. `docker compose down -v` never used.

---

## 15. Actual Validation Results

### Maven Tests (release/final-v1.0.0)
```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS — Total time: 42.212 s
```

### Docker
```
Image: campus-placement-portal:v3  (final release)
Container: campusplacementportal-app-cd  Up
Database: campusplacementportal-db-1  Up (healthy)
http://localhost:8083/            → HTTP 200
http://localhost:8083/students    → HTTP 200
http://localhost:8083/companies   → HTTP 200
http://localhost:8083/drives      → HTTP 200
http://localhost:8083/applications→ HTTP 200
```

### Ansible (site.yml)
```
localhost: ok=19  changed=0  unreachable=0  failed=0  skipped=2
```

### Ansible (provision.yml — idle)
```
localhost: ok=5   changed=0  unreachable=0  failed=0  skipped=0
```

### Ansible (reliability.yml — healthy)
```
localhost: ok=9   changed=0  unreachable=0  failed=0  skipped=2
```

### Ansible (reliability.yml — recovery)
```
localhost: ok=10  changed=1  unreachable=0  failed=0  skipped=1
```

---

## 16. Limitations and Future Enhancements

**Limitations:**
- Docker registry (Docker Hub) not configured; images are local only
- Selenium tests depend on `localhost:8083` being accessible; CI tests on port 8080 fail unless app running locally
- No authentication — single-user system
- No pagination on list pages
- Ansible uses WSL CMD passthrough; requires Docker Desktop running on Windows host

**Future enhancements:**
- Role-based access control
- REST API with Swagger/OpenAPI
- Docker Hub image publishing in Jenkins pipeline
- Kubernetes (Helm chart) deployment
- Database migrations with Flyway
- Email notifications for drive scheduling
- PDF/Excel placement report export
- Automated rollback on deployment failure

---

## 17. Conclusion

The Campus Placement Tracking Portal successfully demonstrates a production-quality DevOps workflow on a college-scale project. All 15 tasks have been implemented and verified:

- The application is functionally correct and UI-polished
- The Jenkins pipeline enforces a quality gate: no deployment without passing tests
- Docker provides environment isolation and data persistence
- Ansible provides idempotent configuration management and automated recovery
- The release is tagged `v1.0.0` on GitHub with complete documentation

The project is ready for viva demonstration and final submission.
