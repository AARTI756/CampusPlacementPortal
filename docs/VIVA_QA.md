# Viva Q&A — Campus Placement Tracking Portal

30+ project-specific questions with concise correct answers.

---

## Architecture and Spring Boot

**Q1. What is the role of Spring Boot in this project?**  
Spring Boot auto-configures the web server (embedded Tomcat), JPA/Hibernate, and test context. It reduces boilerplate so the team can focus on business logic. `@SpringBootApplication` enables component scanning, auto-configuration, and application entry point.

**Q2. What is Thymeleaf and why was it used instead of React or Angular?**  
Thymeleaf is a server-side Java template engine. HTML templates are rendered on the server and sent as complete pages. It was chosen to avoid a separate frontend build pipeline, making the project simpler for a college demonstration while still producing a professional UI.

**Q3. Explain the layered architecture of the application.**  
- **Controller layer** — Spring MVC `@Controller` classes handle HTTP requests and return view names.
- **Service layer** — Business logic, transaction boundaries.
- **Repository layer** — Spring Data JPA interfaces auto-implement CRUD via Hibernate.
- **Database** — PostgreSQL in production, H2 in-memory for tests.

**Q4. What does `@Entity` and `@Repository` do in JPA?**  
`@Entity` marks a class as a JPA-managed database table. `@Repository` marks an interface as a Spring Data repository; Spring generates the SQL implementation automatically at runtime.

**Q5. How does the application connect to different databases in different environments?**  
Via Spring profiles. The default profile uses `application.properties` pointing to PostgreSQL. The `test` profile (activated via `@ActiveProfiles("test")`) uses `application-test.properties` pointing to H2 in-memory. Switching is automatic based on the active profile.

---

## Maven and Testing

**Q6. What is Maven and what does `mvn clean test` do?**  
Maven is a Java build tool that manages dependencies, compilation, testing, and packaging. `clean` deletes the `target/` directory. `test` compiles source and test code, then runs all JUnit tests via the Surefire plugin. If any test fails, the build fails.

**Q7. What is the difference between `mvn test` and `mvn package`?**  
`mvn test` compiles and runs tests only. `mvn package` runs tests AND produces the deployable JAR (or WAR) artifact in `target/`. `-DskipTests` skips test execution during packaging.

**Q8. What are the two types of tests in this project?**  
- **Unit tests** (`PlacementPortalApplicationTests`) — Test service/repository logic using H2, no real browser.
- **Selenium tests** (`StudentSeleniumTest`, `DashboardSeleniumTest`, `ApplicationSeleniumTest`) — End-to-end browser tests using ChromeDriver against the running application.

**Q9. Where are JUnit test results stored?**  
In `target/surefire-reports/` as XML files (one per test class). Jenkins reads these to display test trends and fail the pipeline on test failures.

---

## Git Branches, Merge Conflicts, and Tags

**Q10. What is the branching strategy used in this project?**  
GitHub Flow: `main` holds stable releases; `develop` is the integration branch; feature branches are created for each task and merged via pull requests. Naming convention: `feature/<description>`, `release/<version>`.

**Q11. How do you resolve a Git merge conflict?**  
1. Run `git merge <branch>` — Git marks conflicts in files with `<<<<<<<`, `=======`, `>>>>>>>`.  
2. Edit the file to keep the correct version.  
3. Run `git add <file>` to mark resolved.  
4. Run `git commit` to complete the merge.

**Q12. What is an annotated Git tag and how was it created for this release?**  
Annotated tags include author, date, and message, unlike lightweight tags. Created via:
```bash
git tag -a v1.0.0 -m "Final release v1.0.0"
git push origin v1.0.0
```
This marks the exact commit that passed all mandatory release checks.

**Q13. What is the difference between `git merge` and `git rebase`?**  
`merge` creates a merge commit preserving branch history. `rebase` rewrites commits on top of the target branch for a linear history. In this project, `merge` is used to preserve the contribution history of each feature branch.

---

## Jenkins CI/CD and Quality Gates

**Q14. What is a Jenkins Pipeline and how is it different from a Freestyle job?**  
A Freestyle job is configured via the Jenkins UI — brittle and hard to version. A Pipeline is defined in a `Jenkinsfile` checked into source control — versioned, reviewable, and reproducible. Pipelines support stages, parallel execution, and code-based logic.

**Q15. What is a quality gate in CI/CD?**  
A quality gate is a mandatory check that must pass before the pipeline proceeds to the next stage. In this project: if unit tests fail, the pipeline fails and no Docker image is built or deployed. The "Unit Test" stage is the quality gate.

**Q16. Name the four Jenkins jobs in this project and their purposes.**  
1. `CampusPlacementPortal-CI` — Basic Maven CI (Task 7)
2. `CampusPlacementPortal-Pipeline` — Full build + JAR deploy to port 8082 (Task 8)
3. `CampusPlacementPortal-Continuous-Testing` — Maven + Selenium quality gate (Task 10)
4. `CampusPlacementPortal-Docker-CD` — Docker build + container deploy to port 8083 (Task 12)

**Q17. How does the Docker-CD pipeline deploy without downtime?**  
It stops the existing container, removes it, then starts a new container with the updated image. This is a short interruption rather than a zero-downtime rolling deployment. For true zero-downtime, Kubernetes or a load balancer would be needed.

---

## Selenium WebDriver

**Q18. What is Selenium WebDriver and how does it work?**  
Selenium WebDriver is a browser automation API. It controls a real browser (Chrome) via the WebDriver protocol. In tests, you call `driver.get(url)` to navigate, `driver.findElement(By.id("name"))` to locate elements, and `element.sendKeys("text")` to interact.

**Q19. Why are explicit waits used in Selenium tests?**  
Web pages load asynchronously. If you look for an element before it appears, the test fails. `WebDriverWait` + `ExpectedConditions.visibilityOfElementLocated()` retries until the element appears or a timeout expires — making tests reliable.

**Q20. What is ChromeDriver and why does the test use `--headless` mode?**  
ChromeDriver is a WebDriver implementation that controls Google Chrome. `--headless=new` runs Chrome without a visible window, which is required for CI environments (like Jenkins servers) that have no display.

**Q21. What do the Selenium tests cover in this project?**  
- Dashboard page title/heading verification
- Student creation via form submission
- Student search by name
- Student deletion
- Applications page accessibility

---

## Docker Images, Containers, Volumes, and Networks

**Q22. What is the difference between a Docker image and a container?**  
An image is a read-only template (like a class). A container is a running instance of an image (like an object). Multiple containers can run from the same image simultaneously.

**Q23. Explain the multi-stage Dockerfile used in this project.**  
- **Stage 1 (build):** Uses `maven:3.9-eclipse-temurin-17` to compile the source and package the JAR inside Docker.
- **Stage 2 (runtime):** Uses `eclipse-temurin:17-jre-alpine` (smaller image, no Maven/JDK) and copies only the JAR from Stage 1.

Result: The final image is ~100MB instead of ~500MB if Maven and JDK were included.

**Q24. What is a Docker volume and why is it important for PostgreSQL?**  
A Docker volume is persistent storage managed by Docker, outside the container filesystem. Without a volume, all database data is lost when the PostgreSQL container is removed. The `postgres_data` volume preserves data across container restarts and replacements.

**Q25. What is a Docker network and why does the app use `db:5432` not `localhost:5433`?**  
Docker Compose creates a private network (`placement_net`). Services communicate by service name. Inside the `app` container, `localhost` refers to itself, not the host machine. `db:5432` resolves to the `db` service's internal port. `localhost:5433` only works from the host machine outside Docker.

**Q26. What Docker image tags were created in this project?**  
- `v1` — Task 11 initial containerization
- `v2` — Task 12 Jenkins-Docker CD pipeline
- `v3` — Task 15 final release with UI improvements
- `latest` — always points to the most recent build (v3)

---

## Ansible Inventory, Playbooks, and Idempotency

**Q27. What is Ansible and why was it chosen for configuration management?**  
Ansible is an agentless automation tool using SSH (or local connection). Playbooks are YAML files describing the desired state. It was chosen because: no agent installation needed, simple YAML syntax, and built-in idempotency semantics.

**Q28. What does idempotency mean in Ansible?**  
Running a playbook multiple times produces the same result and same system state as running it once. If a container is already running, the playbook should report `ok` (not `changed`) without restarting it unnecessarily. This makes automation safe to rerun at any time.

**Q29. What is `ansible_connection=local` and why is it used?**  
It tells Ansible to run tasks directly on the local machine (WSL shell) instead of using SSH. Used here because there is no remote server — the Docker containers are on the same Windows host. Docker commands are called via `cmd.exe` passthrough from WSL.

**Q30. Explain the three Ansible playbooks and their responsibilities.**  
- `site.yml` (Task 13): Configuration management — verifies Docker, image, containers, and all HTTP endpoints.
- `provision.yml` (Task 14): Automated provisioning — ensures the full Docker Compose stack is running using `docker compose up -d`.
- `reliability.yml` (Task 14): Health checking — verifies containers and endpoints, auto-recovers a stopped app container.

---

## Health Checks, Recovery, and Rollback

**Q31. How does the reliability playbook perform auto-recovery?**  
It checks if the app container is running using `docker ps -q --filter`. If it is stopped (empty output), it runs `docker start <container>`. Then it retries the HTTP health check up to 5 times with 5-second delays. The DB container is never restarted automatically.

**Q32. What is the difference between `docker compose down` and `docker compose down -v`?**  
`down` stops and removes containers and the network. `down -v` also deletes named volumes including `postgres_data`, permanently destroying all database data. In this project, `down -v` is never used.

**Q33. How would you perform a rollback if the new Docker image causes errors?**  
1. Stop the failing container: `docker stop campusplacementportal-app-cd`
2. Update `docker-compose.yml` to the previous image tag (e.g., `v2`)
3. Run: `docker compose -p campusplacementportal up -d`
4. The database is unaffected; only the app image changes.

---

## Limitations and Future Improvements

**Q34. What are the main limitations of the current implementation?**  
- No Docker Hub registry push — images are local only
- No authentication or authorization
- Selenium tests must target a running instance on the correct port
- Ansible uses WSL CMD passthrough — works only on Windows with Docker Desktop
- No pagination on list pages with large datasets

**Q35. How would you extend the Jenkins pipeline to push to Docker Hub?**  
Add a "Push to Registry" stage using Jenkins credentials binding:
```groovy
withCredentials([usernamePassword(credentialsId: 'dockerhub', ...)]) {
    sh 'docker push myrepo/campus-placement-portal:v3'
}
```
Never hardcode credentials in the Jenkinsfile.
