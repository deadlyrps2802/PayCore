# PayCore — Payroll & HR Management System

PayCore is a full-stack Human Capital Management (HCM) platform built for managing employee records, organization salary structures, leave requests, and monthly payslip generation.

---

## Technical Stack

- **Frontend:** Angular v17 (Feature-Based Architecture), Bootstrap 5, RxJS, Responsive Enterprise Design
- **Backend:** Java 17, Spring Boot 3.2, Spring Security (JWT), Hibernate (JPA)
- **Database:** PostgreSQL (Production / Docker) / H2 In-Memory (Local Development)
- **DevOps & Cloud:** Docker, Nginx, Docker Compose, Vercel (Frontend), Render (Backend)
- **API Documentation:** OpenAPI 3 + Swagger UI
- **Testing:** JUnit 5 + Mockito + Spring Security Test
- **CI:** GitHub Actions

---

## Project Features

### 1. Role-Based Access Control (RBAC)
- **Admin (`ROLE_ADMIN`):** Full management access to add/update employees, configure salary structures, review leave applications, and generate monthly payslips.
- **Employee (`ROLE_EMPLOYEE`):** Self-service profile view, personal payslip access, and leave application submission.

### 2. Core Modules
- **Employee Management:** Full employee CRUD, auto-generated Employee IDs (`EMP-1001`), DOB date picker, unique mobile verification, designation and department assignments.
- **Salary & Payslip Service:** Admin salary configuration (Basic, HRA, Allowances, Medical, PF, Tax), monthly payslip generation with automatic **Unpaid Leave Deductions** calculation (`Daily Rate = Gross / 30 * Unpaid Days`). Interactive printable payslip modal formatted in Indian Rupees (₹).
- **Leave Management:** Employee leave application form (Paid, Unpaid, Sick, Casual), manager approval/rejection workflow.
- **In-App Notifications:** Instant notifications dispatched for payslip generation and leave application status updates.

---

## Project Structure Overview

```text
PayCore/
├── backend/                              <-- Spring Boot 3 REST API
│   ├── src/main/java/com/paycore/
│   │   ├── config/DataInitializer.java   <-- Auto-seeds demo accounts on startup
│   │   ├── controller/                   <-- REST Endpoints
│   │   ├── dto/                          <-- Data Transfer Objects
│   │   ├── entity/                       <-- Database Tables (JPA Data Models)
│   │   ├── repository/                   <-- Spring Data JPA Repositories
│   │   ├── security/                     <-- JWT Token Authentication & Security Config
│   │   └── service/                      <-- Business Logic & Salary Calculations
│   └── pom.xml
│
└── frontend/                             <-- Angular 17 SPA (Feature-Based)
    └── src/app/
        ├── core/                         <-- Auth, Tokens, Guards, Interceptors
        └── features/                     <-- Dashboard, Employee, Leave, Login, Salary
```

---

## Pre-Populated Credentials (Seed Data)

The application automatically seeds initial demo accounts on startup:

| Role | Email | Password | Default Employee Code |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@paycore.com` | `Password123!` | `EMP-1001` |
| **Employee** | `employee@paycore.com` | `Password123!` | `EMP-1002` |

> For a shared/public repository, replace demo credentials before production deployment and never commit real secrets.

---

## Quickstart Guide

### Local Development Setup

#### 1. Backend (Spring Boot)
Requires JDK 17+:

```bash
cd backend
mvn spring-boot:run
```

- Backend REST API: `http://localhost:8080`
- H2 Database Console: `http://localhost:8080/h2-console`

#### 2. Frontend (Angular)
Requires Node.js 18+ and npm:

```bash
cd frontend
npm install
npm start
```

Access the Angular dev server at `http://localhost:4200`.

### Swagger / OpenAPI

After starting the backend, API documentation is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

Swagger UI provides an interactive view of the REST endpoints and makes API review/demo easier.

---

### Cloud Deployment Guide

- **Frontend (Vercel):** Connect repository, set Root Directory to `frontend`, Framework to `Angular`, Output Directory to `dist/paycore-frontend/browser`.
- **Backend (Render):** Connect repository, set Root Directory to `backend`, Runtime to `Docker`.

---

## Engineering Additions (Internship Work)

### Automated Backend Testing
The project already contained basic service-level tests. I extended the automated testing layer with authentication and JWT security coverage.

Current test classes:

```text
backend/src/test/java/com/paycore/
├── service/
│   ├── EmployeeServiceTest.java
│   ├── LeaveServiceTest.java
│   ├── SalaryServiceTest.java
│   └── AuthServiceTest.java
└── security/
    └── JwtTokenProviderTest.java
```

Coverage includes:
- Employee lookup and duplicate mobile validation.
- Leave creation and invalid date-range validation.
- Unpaid-leave salary deduction and net-pay calculation.
- Authentication success and missing-user failure handling.
- JWT generation, username extraction, invalid-token handling, and tampered-token handling.

Run tests locally:

```bash
cd backend
mvn test
```

### Continuous Integration
GitHub Actions workflow (`.github/workflows/ci.yml`) runs the backend Maven test suite automatically on pushes and pull requests to `main`.

### JWT Validation Hardening
Invalid JWT signature/security failures are now handled inside the JWT validation boundary and return `false` instead of leaking an unhandled JWT exception to callers.

### API Documentation
Springdoc OpenAPI + Swagger UI has been added so the REST API can be explored and tested interactively during development and technical demos.

---

## Architecture

See **[ARCHITECTURE.md](ARCHITECTURE.md)** for Mermaid diagrams covering:
- High-level Angular → Spring Boot → Database architecture
- REST request flow
- Payroll calculation flow
- JWT authentication and RBAC flow
- JUnit/Mockito + GitHub Actions CI flow

## Internship Engineering Contributions

The internship work focuses on maintainability, security validation and developer workflow rather than changing the core payroll behavior:

- **JUnit 5 + Mockito:** authentication service and JWT security tests.
- **JWT validation hardening:** valid, invalid and tampered token scenarios.
- **GitHub Actions:** automated backend Maven test execution on pushes and pull requests to `main`.
- **OpenAPI/Swagger:** interactive API documentation for REST endpoints.
- **Documentation:** architecture diagrams and an internship contribution/presentation guide for technical handover.

### Suggested Next Enhancements

These are roadmap items and are **not claimed as completed**:
- Spring Boot integration tests with `@SpringBootTest` + H2/Testcontainers.
- Controller/API tests with MockMvc.
- Global exception handling with `@ControllerAdvice`.
- Audit logging for payroll and leave actions.
- Pagination and filtering for employee/leave lists.
- Role-specific dashboard analytics.
- Docker health checks and production environment profiles.
- Code coverage reporting with JaCoCo.
