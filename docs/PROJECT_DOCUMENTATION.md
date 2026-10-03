# Campus Placement Tracking Portal - Problem Definition and Scope

## 1. Problem Statement
The current manual processes for tracking student placements, managing company drives, and communicating statuses are inefficient, error-prone, and lack real-time visibility. Both students and placement coordinators struggle with fragmented data across spreadsheets, emails, and notice boards, which leads to miscommunication, missed opportunities, and administrative bottlenecks.

## 2. Need for the Campus Placement Tracking Portal
A centralized, automated placement tracking portal is required to streamline the placement lifecycle. It will eliminate data silos, provide a single source of truth for student eligibility and placement status, and simplify the application process for upcoming company drives. This will improve the efficiency of the placement cell and provide a better experience for students.

## 3. Target Users
- **Placement Coordinators / Administrators:** Staff members who manage company relationships, create placement drives, update application statuses, and monitor overall placement metrics.
- **Students:** Individuals looking for job opportunities who need to apply for drives and track their application progress.

## 4. Stakeholders
- College Administration and Management
- Training and Placement Cell (T&P Cell)
- Enrolled Students
- Partner Recruiting Companies (indirectly, via the coordinators)

## 5. Existing Pain Points
- **Data Fragmentation:** Student profiles, company details, and drive schedules are scattered across multiple manual records.
- **Manual Eligibility Checking:** Verifying if a student meets the minimum CGPA and other criteria for a drive is time-consuming.
- **Status Tracking Difficulties:** Students are unaware of their current application status (e.g., Shortlisted, Interviewing, Rejected).
- **Reporting Overhead:** Compiling placement statistics (e.g., total placed vs. unplaced) requires manual calculation.

## 6. Project Objectives
- To develop a web-based portal that centralizes student and company data.
- To provide a dashboard that offers real-time placement statistics (total students, placed students, active drives).
- To allow the creation and management of placement drives with specific eligibility criteria.
- To enable tracking of student applications through various stages (Applied, Shortlisted, Interview, Selected, Rejected).

## 7. Project Constraints
- **Time:** The project must be completed within the current semester timeframe.
- **Technology:** Must utilize Java, Spring Boot, PostgreSQL, and Thymeleaf as per the curriculum requirements.
- **Scope:** The MVP must remain focused on core CRUD and tracking functionalities; complex features like automated email notifications or machine learning matching are out of scope.

## 8. Measurable Success Criteria
- The application successfully compiles and runs locally and can be containerized.
- The dashboard accurately reflects the real-time count of students, companies, drives, and placement statuses.
- Coordinators can create a new drive, and a student can be mapped to it successfully.
- Application status updates automatically reflect in the student's overall placement status (e.g., updating an application to "SELECTED" sets the student as "Placed").

## 9. Approved MVP Scope
The Minimum Viable Product (MVP) specifically includes:
- **Dashboard:** Real-time summary statistics and basic alerts.
- **Student Module:** Add, view, and search student profiles (Name, Email, Department, CGPA).
- **Company Module:** Add, view, and search partner companies.
- **Placement Drive Module:** Create and list placement drives with criteria (Role, Date, Min CGPA).
- **Application Module:** Apply students to drives and update their progress statuses.
- **REST APIs:** Basic endpoints for potential future external integrations.
- **Database:** PostgreSQL integration with standard relational mapping.
