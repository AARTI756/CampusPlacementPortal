# Campus Placement Portal — Ansible Configuration Management

**Task 13: Configuration Management with Ansible**

## Overview

This directory contains the Ansible configuration-management setup for the
Campus Placement Tracking Portal Docker environment.  It verifies, validates,
and manages the running Docker containers that were deployed in Task 11/12.

## Prerequisites

- WSL (Windows Subsystem for Linux) with Ubuntu 22.04
- Ansible installed in WSL (`sudo apt-get install -y ansible`)
- Docker Desktop running on the Windows host
- The Docker Compose stack from Task 11/12 must be started:
  `docker compose -p campusplacementportal up -d`

## Directory Structure

```
ansible/
├── inventory.ini        # Inventory: localhost via local connection
├── group_vars/
│   └── all.yml          # All configurable variables
├── site.yml             # Main playbook
└── README.md            # This file
```

## How to Use

### 1. Check Ansible Installation

```bash
wsl ansible --version
wsl ansible-playbook --version
```

### 2. Check Inventory

```bash
wsl ansible -i ansible/inventory.ini local -m ping
```

Expected output:
```
localhost | SUCCESS => {"ping": "pong"}
```

### 3. Run the Playbook (First Run)

From the project root (Windows PowerShell):

```powershell
wsl ansible-playbook -i ansible/inventory.ini ansible/provision.yml
```

Or from within WSL:

```bash
cd /mnt/c/Users/spa/OneDrive/Desktop/Placement_project/CampusPlacementPortal
ansible-playbook -i ansible/inventory.ini ansible/provision.yml
```

### 4. Run Again for Idempotency (Second Run)

Run the exact same command again:

```powershell
wsl ansible-playbook -i ansible/inventory.ini ansible/provision.yml
```

**Expected result:** `changed=0` — no unnecessary changes.
All tasks should still pass because nothing in the environment changed.

### 5. Verify the Application

After the playbook runs, the application must be accessible:

- Dashboard:   http://localhost:8083/
- Students:    http://localhost:8083/students
- Companies:   http://localhost:8083/companies
- Drives:      http://localhost:8083/drives

From PowerShell:
```powershell
Invoke-WebRequest http://localhost:8083/ -UseBasicParsing | Select StatusCode
```

### 6. Expected Playbook Output

```
PLAY [Campus Placement Portal - Docker Configuration Management] ***

TASK [Verify Docker CLI is available] ***        ok
TASK [Display Docker version] ***                ok
TASK [Gather list of running Docker containers]  ok
TASK [Verify Docker image exists] ***            ok
TASK [Check if PostgreSQL container is running]  ok
TASK [Check current status of application] ***   ok
TASK [Report when container is already running]  ok  ← "no change needed"
TASK [Verify application endpoint] ***           ok
TASK [Verify /students endpoint] ***             ok
...
TASK [=== CONFIGURATION MANAGEMENT SUMMARY ===]  ok

PLAY RECAP ***
localhost : ok=N   changed=0   unreachable=0   failed=0
```

## Key Variables (group_vars/all.yml)

| Variable              | Value                          |
|-----------------------|-------------------------------|
| `app_port`            | 8083                          |
| `docker_image`        | campus-placement-portal:v2    |
| `app_container_name`  | campusplacementportal-app-cd  |
| `db_container_name`   | campusplacementportal-db-1    |
| `db_port`             | 5433                          |
| `compose_project_name`| campusplacementportal         |

## Important Notes

- The playbook is **idempotent**: running it multiple times produces the same result.
- It does **NOT** restart the application container unless it is stopped.
- It does **NOT** delete the PostgreSQL volume or recreate the database.
- Tasks verify the current state and only act when a change is actually needed.


### Task 14: Automated Provisioning & Reliability

**Provision the environment:**
``bash
wsl ansible-playbook -i ansible/inventory.ini ansible/provision.yml
``

**Validate Reliability:**
``bash
wsl ansible-playbook -i ansible/inventory.ini ansible/reliability.yml
``

