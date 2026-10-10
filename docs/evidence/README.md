# Evidence Directory

This directory contains screenshots and terminal output logs captured during Task 15 validation.

## What to capture for the viva demonstration

**Recommended screenshots to take (manually via browser/terminal):**

1. `dashboard.png` — Browser screenshot of `http://localhost:8083/`
2. `students.png` — Browser screenshot of `http://localhost:8083/students`
3. `companies.png` — Browser screenshot of `http://localhost:8083/companies`
4. `drives.png` — Browser screenshot of `http://localhost:8083/drives`
5. `applications.png` — Browser screenshot of `http://localhost:8083/applications`
6. `jenkins_docker_cd.png` — Jenkins `CampusPlacementPortal-Docker-CD` successful build
7. `jenkins_test_report.png` — Jenkins test results trend
8. `docker_images.png` — Terminal: `docker images` showing v1, v2, v3
9. `docker_ps.png` — Terminal: `docker ps` showing running containers
10. `ansible_site.png` — Terminal: `ansible-playbook site.yml` output (ok=19 changed=0)
11. `ansible_provision.png` — Terminal: `ansible-playbook provision.yml` idempotency
12. `ansible_reliability.png` — Terminal: `ansible-playbook reliability.yml` (ok=9)
13. `ansible_recovery.png` — Terminal: reliability playbook recovery (ok=10 changed=1)
14. `maven_test_success.png` — Terminal: `mvn clean test` (Tests run: 7, Failures: 0)

## Verified Terminal Results (recorded 2026-10-10)

```
# Maven test results
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS — Total time: 42.212 s

# Docker images
campus-placement-portal:v3    latest  (final release)
campus-placement-portal:v2    (Task 12)
campus-placement-portal:v1    (Task 11)

# Running containers
campusplacementportal-app-cd  campus-placement-portal:v3  Up  0.0.0.0:8083→8080
campusplacementportal-db-1    postgres:15 (healthy)        Up  0.0.0.0:5433→5432

# Ansible site.yml (Task 13) - Run 1 and Run 2
localhost: ok=19 changed=0 unreachable=0 failed=0 skipped=2

# Ansible provision.yml (Task 14) - Run 1 (idle)
localhost: ok=5 changed=0 unreachable=0 failed=0 skipped=0

# Ansible provision.yml (Task 14) - Recovery run (app stopped)
localhost: ok=5 changed=1 unreachable=0 failed=0 skipped=0

# Ansible reliability.yml (Task 14) - Healthy
localhost: ok=9 changed=0 unreachable=0 failed=0 skipped=2

# Ansible reliability.yml (Task 14) - Auto-recovery run
localhost: ok=10 changed=1 unreachable=0 failed=0 skipped=1
  App Container: RECOVERED
  DB Container : HEALTHY
  Dashboard    : HTTP 200
  /students    : HTTP 200
  /companies   : HTTP 200
  /drives      : HTTP 200
```
