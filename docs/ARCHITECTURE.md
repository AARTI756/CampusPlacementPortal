# System Architecture

The Campus Placement Tracking Portal uses a classic three-tier architecture implemented with Java and Spring Boot.

## 1. Presentation Layer (Client / UI)
- **Server-Side Rendered HTML:** The application uses Thymeleaf templates to generate dynamic HTML content. This provides a robust, state-free frontend that is highly compatible with basic browsers and easy to test with Selenium.
- **REST APIs:** The system also exposes raw JSON endpoints (`/api/v1/*`) to allow for potential future separation of the frontend (e.g., using React/Angular) or external service integration.

## 2. Application Layer (Spring Boot)
- **Controllers:** `WebController` handles HTTP requests for HTML views, interacting with the models and returning Thymeleaf templates. `ApiController` handles REST requests.
- **Service Layer:** `DashboardService` encapsulates the business logic for calculating real-time dashboard statistics and alerts.
- **Repositories:** Interfaces extending Spring Data `JpaRepository` handle all data access operations without requiring boilerplate SQL.

## 3. Data Layer (PostgreSQL / H2)
- **Entities:** JPA entities (`Student`, `Company`, `PlacementDrive`, `Application`) represent the relational data model.
- **Database:** PostgreSQL is the primary database, designed to be run as a Docker container for consistency across environments. An H2 in-memory database is used for local integration testing to ensure tests run reliably without a persistent database dependency.

## Architecture Diagrams
Detailed diagrams are located in the `docs/diagrams/` folder:
- [Use-Case Diagram](diagrams/use-case.md)
- [System Architecture](diagrams/architecture.md)
- [Database ERD](diagrams/database.md)
