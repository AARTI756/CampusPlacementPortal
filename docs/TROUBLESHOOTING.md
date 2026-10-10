# Troubleshooting Guide — Campus Placement Tracking Portal

---

## Maven Build / Test Failures

### `BUILD FAILURE: compilation error`
- Ensure Java 17+ is installed: `java -version`
- Ensure `JAVA_HOME` points to a JDK (not JRE): `echo $env:JAVA_HOME`
- Run `mvn clean compile` to isolate the error

### Selenium tests fail with `ERR_CONNECTION_REFUSED`
- The app is not running on the expected port
- Default port for Selenium tests is 8080; override: `mvn test -DbaseUrl=http://localhost:8083`
- Start the Docker deployment first: `docker compose -p campusplacementportal up -d`

### `Tests run: 0` — no tests found
- Check test class names end with `Test` or `Tests`
- Check `src/test/java` directory structure matches package names

### `Unable to find CDP implementation matching 154`
- This is a Selenium warning about Chrome DevTools Protocol version mismatch — it is **non-fatal**
- Tests still pass; ignore this warning or add selenium-devtools-v154 dependency when available

---

## PostgreSQL Connectivity

### `Connection refused` on `localhost:5433`
- Check Docker container is running: `docker ps | findstr db-1`
- Start database: `docker compose -p campusplacementportal up -d db`
- Verify port: `docker compose -p campusplacementportal ps`

### `password authentication failed for user "placement_user"`
- Container uses hardcoded credentials in `docker-compose.yml`
- If the container was recreated with different env vars, recreate: `docker compose -p campusplacementportal down` then `up -d` (do NOT add `-v`)

### App cannot connect to DB inside Docker (`db:5432`)
- Inside Docker, app must use `db:5432` (service name), not `localhost:5433`
- Check `docker-compose.yml` `DB_URL` env var: `jdbc:postgresql://db:5432/placement_db`
- Do NOT use `127.0.0.1:5433` inside the container

### `role "placement_user" does not exist`
- The volume from a different DB setup may be present
- To reset: `docker compose -p campusplacementportal down` + manually delete `postgres_data` volume (WARNING: data loss)

---

## Port Conflicts (8080–8083, 5433)

### `Port already in use`
Check which process uses the port:
```powershell
netstat -ano | findstr :8083
tasklist | findstr <PID>
```

### Port 8080 conflict with bWAPP or another container
The `hopeful_sinoussi` (bWAPP) container maps port 8080. Ensure it is stopped before running local dev:
```powershell
docker stop hopeful_sinoussi
```

### Jenkins port 8081
Jenkins runs as a Windows service. Check via Services panel or:
```powershell
netstat -ano | findstr :8081
```

---

## Jenkins Service and Pipeline Failures

### Jenkins not accessible at `http://localhost:8081`
- Open Windows Services → check "Jenkins" service is running → Start if stopped
- Or from PowerShell: `Start-Service Jenkins`

### `maven is not a tool name` / tool not found
- In Jenkinsfile, Maven tool must match the exact name configured in Jenkins
- Go to: Jenkins → Manage Jenkins → Global Tool Configuration → Maven
- Confirm the name matches `Maven-3.9.16` used in Jenkinsfile

### Pipeline stage fails with exit code 1
- Click on the failed stage → "Console Output" to see the actual error
- Do not modify existing working jobs; create a new job for testing

### `CampusPlacementPortal-Docker-CD` fails at Docker step
- Ensure Docker Desktop is running
- Ensure the Jenkins agent can run `docker` commands
- On Windows, Jenkins runs as SYSTEM; Docker Desktop must allow SYSTEM user

---

## Docker Desktop / Container / Network Issues

### `docker compose` command not found
- Use `docker compose` (V2, space) not `docker-compose` (V1, hyphen)
- Ensure Docker Desktop is updated to version with Compose V2 built-in

### Container created but app not starting
```powershell
docker compose -p campusplacementportal logs app
```
Look for startup exceptions (especially DB connection errors).

### Container keeps restarting
- App may be failing to connect to DB
- Check DB container is healthy: `docker compose -p campusplacementportal ps`
- Check logs: `docker compose -p campusplacementportal logs db`

### `Error response from daemon: Conflict — name already in use`
```powershell
docker rm campusplacementportal-app-cd
docker compose -p campusplacementportal up -d
```

### Preserve database when recreating app container
```powershell
docker stop campusplacementportal-app-cd
docker rm campusplacementportal-app-cd
docker compose -p campusplacementportal up -d
```
The `postgres_data` volume is NOT removed this way.

### NEVER run:
```powershell
docker compose down -v   # ← DELETES postgres_data volume and all data!
```

---

## Selenium / Chrome Issues

### `ChromeDriver not found`
- Selenium 4.19+ uses Selenium Manager to auto-download ChromeDriver
- Ensure internet access during first test run, or pre-download ChromeDriver matching your Chrome version

### Chrome crashes / `session not created`
- Run Chrome headless: verify `ChromeOptions` in test setup includes `--headless=new` and `--no-sandbox`
- Check Chrome version: `Google Chrome --version`

### Tests pass locally but fail in Jenkins
- Jenkins agent may not have Chrome installed
- Install Chrome on the Jenkins machine or use a Docker-based agent with Chrome

---

## WSL / Ansible Execution

### `wsl: command not found` (PowerShell)
- WSL2 must be installed and enabled
- Run: `wsl --install` as Administrator, then restart

### `ansible: command not found` in WSL
- Ansible 2.10.8 is installed in WSL Ubuntu
- Activate if needed: `wsl bash -c "ansible --version"`

### `docker: command not found` inside WSL
- Docker Desktop WSL integration must be enabled, OR
- Use the Windows CMD passthrough: `/mnt/c/Windows/System32/cmd.exe /c "docker ..."`
- Ansible playbooks in this project already use the CMD passthrough method

### Playbook fails with Jinja2 template error on `{{.Names}}`
- Never use Go template format strings in Ansible `shell` tasks directly
- Use `docker ps -q --filter name=<name> --filter status=running` instead

### `Unreachable` host in Ansible
- Check `ansible/inventory.ini` has `ansible_connection=local`
- Run: `wsl ansible -i ansible/inventory.ini local -m ping`

---

## Recovery Steps (Preserve Persistent Data)

### Full stack restart (preserves data)
```powershell
docker compose -p campusplacementportal down
docker compose -p campusplacementportal up -d
```

### Only restart app (fastest, keeps DB running)
```powershell
docker restart campusplacementportal-app-cd
```

### App container recreate with new image (keeps DB)
```powershell
docker stop campusplacementportal-app-cd
docker rm campusplacementportal-app-cd
docker compose -p campusplacementportal up -d
```

### Check volume is intact
```powershell
docker volume ls | findstr postgres
docker volume inspect campusplacementportal_postgres_data
```

### Ansible-based recovery
```powershell
wsl ansible-playbook -i ansible/inventory.ini ansible/provision.yml
wsl ansible-playbook -i ansible/inventory.ini ansible/reliability.yml
```
