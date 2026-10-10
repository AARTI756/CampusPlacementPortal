# Release Checklist — Campus Placement Tracking Portal v1.0.0

**Release Date:** 2026-10-10  
**Release Branch:** `release/final-v1.0.0`  
**Release Tag:** `v1.0.0`

---

## Mandatory Pre-Release Checks

### 1. Source Code
- [x] All 15 task requirements reviewed against original task sheet
- [x] All required feature branches preserved on GitHub
- [x] `release/final-v1.0.0` branch created from `feature/provisioning-reliability` (Task 14 HEAD)
- [x] Working tree clean (`git status` shows nothing to commit)
- [x] No credentials, passwords, or tokens committed to source

### 2. Maven Build & Tests
- [x] `mvn clean test` passes: **Tests run: 7, Failures: 0, Errors: 0, Skipped: 0**
- [x] `mvn package -DskipTests` produces JAR: `target/placement-portal-0.0.1-SNAPSHOT.jar`
- [x] JUnit XML reports generated in `target/surefire-reports/`
- [x] Selenium tests run against `http://localhost:8083` with `-DbaseUrl` flag

### 3. Docker
- [x] `campus-placement-portal:v3` image built from final release code
- [x] `campus-placement-portal:latest` tag updated to v3
- [x] Container `campusplacementportal-app-cd` running (port 8083)
- [x] Container `campusplacementportal-db-1` running (port 5433, healthy)
- [x] `postgres_data` volume intact
- [x] `docker-compose.yml` updated to use `campus-placement-portal:v3`

### 4. Application Endpoints
- [x] `http://localhost:8083/` — HTTP 200
- [x] `http://localhost:8083/students` — HTTP 200
- [x] `http://localhost:8083/companies` — HTTP 200
- [x] `http://localhost:8083/drives` — HTTP 200
- [x] `http://localhost:8083/applications` — HTTP 200

### 5. Database
- [x] Application connects to PostgreSQL successfully (no startup errors)
- [x] Data persists after container restart
- [x] No user records deleted during testing

### 6. Ansible
- [x] `ansible/site.yml` — `ok=19 changed=0 failed=0` (both runs)
- [x] `ansible/provision.yml` — `ok=5 changed=0 failed=0` (idle); `changed=1` when container stopped
- [x] `ansible/reliability.yml` — `ok=9 changed=0 failed=0` (healthy); `ok=10 changed=1` (recovery)
- [x] Idempotency demonstrated on all three playbooks

### 7. Jenkins
- [x] `CampusPlacementPortal-CI` — existing job intact (not broken)
- [x] `CampusPlacementPortal-Pipeline` — existing job intact (not broken)
- [x] `CampusPlacementPortal-Continuous-Testing` — existing job intact (not broken)
- [x] `CampusPlacementPortal-Docker-CD` — existing job intact (not broken)
- [x] Jenkins accessible at `http://localhost:8081`

### 8. UI Quality
- [x] Dashboard summary cards have navigation links ("View Details")
- [x] Cards are equal height with card footers
- [x] All pages load without server errors
- [x] All navigation links functional
- [x] Consistent Bootstrap 5 styling throughout

### 9. Documentation
- [x] `README.md` — finalized with complete setup and usage instructions
- [x] `docs/FINAL_REPORT.md` — complete 17-section project report
- [x] `docs/TROUBLESHOOTING.md` — common issues and recovery steps
- [x] `docs/VIVA_QA.md` — 35 project-specific Q&A
- [x] `docs/RELEASE_CHECKLIST.md` — this document
- [x] `docs/DEVOPS_WORKFLOW.md` — updated Tasks 1–15 Done
- [x] `ansible/README.md` — updated with Task 14 commands

### 10. Git Hygiene
- [x] `.gitignore` excludes `target/`, `*.log`, `.env`, `*.class`
- [x] No accidental temp files committed
- [x] Meaningful commit messages throughout history
- [x] Release branch pushed to GitHub
- [x] Annotated tag `v1.0.0` pushed to GitHub

### 11. Evidence Directory
- [x] `docs/evidence/` directory created

### 12. Presentation
- [x] `presentation/VIVA_PRESENTATION.md` — 12-slide outline created

---

## Actual Command Results

```
# Maven tests
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS — Total time: 42.212 s

# Docker images
campus-placement-portal:v3     (latest — final release)
campus-placement-portal:v2     (Task 12 Jenkins CD)
campus-placement-portal:v1     (Task 11 initial)

# Docker containers
campusplacementportal-app-cd   Up   campus-placement-portal:v3   0.0.0.0:8083→8080
campusplacementportal-db-1     Up   postgres:15 (healthy)         0.0.0.0:5433→5432

# Ansible site.yml (Task 13)
localhost: ok=19  changed=0  unreachable=0  failed=0  skipped=2

# Ansible provision.yml (Task 14)
localhost: ok=5   changed=0  unreachable=0  failed=0  skipped=0  [idle run]
localhost: ok=5   changed=1  unreachable=0  failed=0  skipped=0  [recovery run]

# Ansible reliability.yml (Task 14)
localhost: ok=9   changed=0  unreachable=0  failed=0  skipped=2  [healthy]
localhost: ok=10  changed=1  unreachable=0  failed=0  skipped=1  [recovery run]
```

---

## Release Tag

```bash
git tag -a v1.0.0 -m "Final release: Campus Placement Portal v1.0.0 — all 15 tasks complete"
git push origin v1.0.0
```

---

## Known Limitations Not Blocking Release

| Item | Limitation | Impact |
|---|---|---|
| Docker Hub registry | Not configured; local images only | Low — local demo sufficient for college project |
| Selenium port default | Tests default to 8080 without `-DbaseUrl` flag | Low — documented; Jenkins job overrides correctly |
| Ansible WSL passthrough | Requires Docker Desktop on Windows host | Low — expected on this environment |
| No auth | Single-user portal | Medium — future enhancement |

---

## Final Release Status: ✅ APPROVED FOR SUBMISSION
