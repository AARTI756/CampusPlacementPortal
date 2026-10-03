# Git Workflow and Collaboration Guide

## 1. Branching Strategy
We follow a structured branching strategy to maintain stability in our production code while allowing parallel development.

- **`main`**: The primary branch containing stable, production-ready code. Commits here represent release milestones.
- **`develop`**: The integration branch for upcoming features. All new features and bug fixes are merged here first.
- **`feature/*`**: Short-lived branches created from `develop` for specific tasks (e.g., `feature/add-student-search`, `feature/docker-setup`).
- **`bugfix/*`**: Short-lived branches for fixing issues in `develop`.
- **`hotfix/*`**: Branches created directly from `main` to fix critical production issues immediately.

## 2. Branch Naming Convention
Branches should be named based on their type, followed by a short, descriptive name using kebab-case.
- `feature/add-dockerfile`
- `feature/ui-dashboard`
- `bugfix/fix-search-query`
- `hotfix/db-connection-crash`

## 3. Commit Message Convention
We use a standard structure to make our commit history readable and trackable.
- **Format:** `[type]: [Subject]`
- **Types:**
  - `feat`: A new feature
  - `fix`: A bug fix
  - `docs`: Documentation only changes
  - `style`: Changes that do not affect the meaning of the code (formatting)
  - `refactor`: A code change that neither fixes a bug nor adds a feature
  - `test`: Adding missing tests or correcting existing tests
  - `chore`: Changes to the build process or auxiliary tools (e.g., Maven, GitHub Actions)

**Examples:**
- `feat: add student search functionality`
- `fix: resolve issue with PostgreSQL timezone`
- `docs: update DEVOPS_WORKFLOW.md`

## 4. Pull Request (PR) Process
1. **Create a branch:** Branch off from `develop`.
2. **Develop and test:** Write your code, ensuring `mvn clean test` passes locally.
3. **Commit:** Use clear commit messages following the convention.
4. **Push:** Push your branch to the remote repository.
5. **Open a PR:** Open a Pull Request on GitHub aiming to merge into `develop`. 
6. **Review:** Request a review from at least one team member. 
7. **Merge:** Once approved and all CI checks pass, squash and merge into `develop`.
