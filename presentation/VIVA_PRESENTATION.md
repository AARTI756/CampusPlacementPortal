# Viva Presentation — Campus Placement Tracking Portal

**12 slides for the final college viva demonstration**

---

## Slide 1: Title

**Campus Placement Tracking Portal**

A DevOps-Enabled Full-Stack College Project

- Project: Campus Placement Management System
- Technology: Java / Spring Boot / PostgreSQL / Docker / Jenkins / Ansible
- Repository: github.com/AARTI756/CampusPlacementPortal
- Version: 1.0.0
- Tasks Completed: 15 / 15

---

## Slide 2: Problem Statement and Objectives

**Problem:**
Campus placement coordination relies on spreadsheets — causing data inconsistency, no real-time visibility, and high administrative overhead.

**Solution:**
A centralized web portal for managing students, companies, placement drives, and applications.

**Objectives:**
- Build a functional placement tracking portal
- Implement a complete DevOps pipeline (Git → Test → Build → Deploy → Monitor)
- Demonstrate repeatability and reliability through automation

---

## Slide 3: Requirements and Scope

**Functional Requirements:**
- Student profiles (CRUD + search)
- Company and placement drive management
- Application tracking with status workflow
- Dashboard with live placement statistics

**DevOps Requirements:**
- Git branching strategy (Tasks 4–6)
- Jenkins CI/CD with quality gate (Tasks 7–8, 10, 12)
- Selenium automated UI testing (Tasks 9–10)
- Docker containerization with persistent storage (Tasks 11–12)
- Ansible configuration management + provisioning (Tasks 13–14)

---

## Slide 4: Architecture and Technology Stack

**Application Architecture:**
```
Browser → Thymeleaf → Spring MVC → Service → JPA → PostgreSQL
```

**Technology Stack:**

| Layer | Choice |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.4 |
| Frontend | Thymeleaf + Bootstrap 5 |
| Database | PostgreSQL 15 (Docker), H2 (Tests) |
| Build | Maven 3.9 |
| Testing | JUnit 5 + Selenium 4.19 |
| CI/CD | Jenkins (Pipeline as Code) |
| Container | Docker / Docker Compose |
| Config Mgmt | Ansible 2.10.8 (WSL) |
| VCS | Git / GitHub |

---

## Slide 5: Application Features and UI

**Dashboard** — Summary cards (Students / Companies / Drives / Placed) with navigation links; alerts for unplaced students and upcoming drives.

**Students** — List, search, add, edit, delete with confirmation.

**Companies** — CRUD for company records with industry and package.

**Placement Drives** — Drives linked to companies with date and location.

**Applications** — Status tracking: Applied → Interview Scheduled → Selected / Rejected.

**Consistent UI:** Bootstrap 5, responsive layout, dark navbar, flash messages for success/error.

*[Show live demo of dashboard and student management]*

---

## Slide 6: Git Workflow and Collaboration

**Branching Strategy:** GitHub Flow

```
main          ← stable releases (v1.0.0)
develop       ← integration
feature/...   ← per-task development
release/...   ← final release preparation
```

**Key branches (10 feature branches created):**
- `feature/docker-containerization` (Task 11)
- `feature/jenkins-docker-cd` (Task 12)
- `feature/ansible-config-management` (Task 13)
- `feature/provisioning-reliability` (Task 14)
- `release/final-v1.0.0` (Task 15)

**Git practices:** Commit conventions (`feat:`, `fix:`, `docs:`), pull requests, meaningful commit messages.

---

## Slide 7: Jenkins CI and Pipeline as Code

**4 Jenkins Jobs:**

| Job | Type | Purpose |
|---|---|---|
| CI | Freestyle | Basic build validation |
| Pipeline | Scripted pipeline | Build + JAR deploy → port 8082 |
| Continuous-Testing | Scripted pipeline | Quality gate: unit + Selenium tests |
| Docker-CD | Scripted pipeline | Docker build + deploy → port 8083 |

**Quality Gate:** If any test fails → build fails → no deployment.

**Jenkinsfiles checked into Git** — versioned, reviewable, reproducible.

*[Show Jenkins dashboard and Console Output of a successful Docker-CD build]*

---

## Slide 8: Selenium Testing and Quality Gates

**Test Suite: 7 Tests — 0 Failures**

| Test Class | Tests | What It Verifies |
|---|---|---|
| `DashboardSeleniumTest` | 1 | Dashboard title loads |
| `ApplicationSeleniumTest` | 1 | Applications page loads |
| `StudentSeleniumTest` | 3 | Student CRUD journeys |
| `PlacementPortalApplicationTests` | 2 | Unit: service layer |

**Selenium approach:**
- ChromeDriver in headless mode
- Explicit waits (`WebDriverWait`)
- Runs against live app on `http://localhost:8083`
- Screenshots on failure

*Command:* `mvn test -DbaseUrl=http://localhost:8083`

---

## Slide 9: Docker Deployment and PostgreSQL Persistence

**Multi-stage Dockerfile:**
- Stage 1: Maven builds JAR inside Docker
- Stage 2: Minimal JRE Alpine image runs JAR
- Final image size: ~360MB

**Three image versions:**
- `v1` → Task 11, `v2` → Task 12 (Jenkins CD), `v3` → Task 15 (final)

**PostgreSQL persistence:**
- Named volume `postgres_data` survives container replacement
- `docker compose down` (safe) vs. `docker compose down -v` (NEVER — destroys data)

**Port mapping:**
- App: `0.0.0.0:8083 → 8080` (container)
- DB: `0.0.0.0:5433 → 5432` (container)

*[Show `docker ps`, `docker images`, application at localhost:8083]*

---

## Slide 10: Ansible Provisioning and Reliability/Recovery

**Ansible 2.10.8 (WSL Ubuntu)**

**Three playbooks:**

| Playbook | Purpose | Key Result |
|---|---|---|
| `site.yml` | Config verification | `ok=19 changed=0` (idempotent) |
| `provision.yml` | Ensure stack running | `ok=5 changed=0` idle / `changed=1` recovery |
| `reliability.yml` | Health check + auto-recover | `ok=9 changed=0` / `ok=10 changed=1` |

**Recovery demonstration:**
1. Stop app container → confirmed DOWN
2. Run `provision.yml` → `changed=1` (app restarted)
3. Run `reliability.yml` → HTTP 200 all endpoints

**PostgreSQL never touched** — recovery only restarts the app container.

*[Show actual Ansible terminal output]*

---

## Slide 11: Test Results, Limitations, and Future Enhancements

**Actual Results:**
```
Maven Tests:  7 run, 0 failures, BUILD SUCCESS
Docker:       v3 image, app + db running, HTTP 200 verified
Ansible:      All playbooks pass, idempotency confirmed, recovery demonstrated
Jenkins:      4 jobs intact, Docker-CD pipeline deploys successfully
```

**Known Limitations:**
- No Docker Hub push (images are local)
- No authentication/authorization
- No pagination for large datasets
- Ansible requires WSL + Docker Desktop on Windows

**Future Enhancements:**
- Docker Hub registry integration in Jenkins pipeline
- Role-based authentication (Spring Security)
- Kubernetes (Helm chart) deployment
- Flyway database migrations
- PDF/Excel report generation

---

## Slide 12: Conclusion and Live Demo Flow

**Project Summary:**
- 15 tasks completed across 10 feature branches
- Full DevOps lifecycle implemented end-to-end
- Application is production-quality for a college-scale system

**Live Demo Sequence:**
1. Show GitHub repo: branches, commit history
2. Open `http://localhost:8083` → Dashboard (live data)
3. Add a student → confirm in Students list
4. Run `mvn test -DbaseUrl=http://localhost:8083` → 7 tests PASS
5. Open Jenkins → show `CampusPlacementPortal-Docker-CD` successful build
6. Run `wsl ansible-playbook -i ansible/inventory.ini ansible/reliability.yml`
7. Stop app container → run reliability playbook → show RECOVERED
8. Show `docker ps`, `docker images` — v3 running

**Thank you for evaluating this project!**
