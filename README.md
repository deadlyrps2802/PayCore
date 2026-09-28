# PayCore — Payroll & HR Management System

PayCore is a full-stack Human Capital Management (HCM) platform built for managing employee records, organization salary structures, leave requests, and monthly payslip generation.

---

## Technical Stack

- **Frontend:** Angular v17 (Feature-Based Architecture), Bootstrap 5, RxJS, Responsive Enterprise Design
- **Backend:** Java 17, Spring Boot 3.2, Spring Security (JWT), Hibernate (JPA)
- **Database:** PostgreSQL (Production / Docker) / H2 In-Memory (Local Development)
- **DevOps & Cloud:** Docker, Nginx, Docker Compose, Vercel (Frontend), Render (Backend)

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

```
PayCore/
├── backend/                              <-- Spring Boot 3 REST API
│   ├── src/main/java/com/paycore/
│   │   ├── config/DataInitializer.java   <-- Auto-seeds demo accounts on startup
│   │   ├── controller/                   <-- REST Endpoints (/api/employees, /api/salary, /api/leaves)
│   │   ├── dto/                          <-- Data Transfer Objects
│   │   ├── entity/                       <-- Database Tables (JPA Data Models)
│   │   ├── repository/                   <-- Spring Data JPA Repositories
│   │   ├── security/                     <-- JWT Token Authentication & Security Config
│   │   └── service/                      <-- Business Logic & Salary Calculations
│   └── pom.xml
│
└── frontend/                             <-- Angular 17 SPA (Feature-Based)
    └── src/app/
        ├── core/                         <-- App Infrastructure (Auth, Tokens, Guards)
        │   ├── guards/                   <-- Route Protection Guards
        │   ├── interceptors/             <-- JWT Bearer Token Interceptor
        │   ├── models/                   <-- User & Notification Data Models
        │   └── services/                 <-- Auth & Notification HTTP Services
        │
        └── features/                     <-- Modular Feature Components
            ├── dashboard/                <-- Admin & Employee Dashboard Overview
            ├── employee/                 <-- Employee Directory Component & Service
            ├── leave/                    <-- Leave Application & Approval Workflow
            ├── login/                    <-- Authentication Screen
            └── salary/                   <-- Salary Configuration & Payslip Generator
```

---

## Pre-Populated Credentials (Seed Data)

The application automatically seeds initial demo accounts on startup:

| Role | Email | Password | Default Employee Code |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@paycore.com` | `Password123!` | `EMP-1001` |
| **Employee** | `employee@paycore.com` | `Password123!` | `EMP-1002` |

---

## Quickstart Guide

### Local Development Setup

#### 1. Backend (Spring Boot)
Requires JDK 17+:

```bash
cd backend
./mvnw spring-boot:run
```

- Backend REST API running on `http://localhost:8080`
- H2 Database Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:paycoredb`, Username: `sa`, Password: empty)

#### 2. Frontend (Angular)
Requires Node.js 18+ and npm:

```bash
cd frontend
npm install
npm start
```

Access the Angular dev server at `http://localhost:4200`.

---

### Cloud Deployment Guide

- **Frontend (Vercel):** Connect repository, set Root Directory to `frontend`, Framework to `Angular`, Output Directory to `dist/paycore-frontend/browser`.
- **Backend (Render):** Connect repository, set Root Directory to `backend`, Runtime to `Docker`.


## Engineering Additions (Internship Work)

### Automated Backend Testing
- Added JUnit 5 + Mockito unit tests for Employee, Leave, Salary, Authentication, and JWT security flows.
- Covered successful flows, validation/error cases, duplicate employee data, unpaid-leave salary deductions, authentication failures, and JWT validation.
- Current backend unit-test suite: **10 tests**.

Run locally:

```bash
cd backend
mvn test
```

### Continuous Integration
GitHub Actions workflow (`.github/workflows/ci.yml`) runs the backend test suite automatically on pushes and pull requests to `main`.

### Suggested Next Enhancements
- Spring Boot integration tests with `@SpringBootTest` + H2/Testcontainers.
- Controller/API tests with MockMvc.
- Global exception handling with `@ControllerAdvice`.
- OpenAPI/Swagger API documentation.
- Audit logging for payroll and leave actions.
- Pagination and filtering for employee/leave lists.
- Role-specific dashboard analytics.
- Docker health checks and production environment profiles.


## Architecture

See **[ARCHITECTURE.md](ARCHITECTURE.md)** for Mermaid diagrams covering:
- High-level Angular → Spring Boot → Database architecture
- REST request flow
- Payroll calculation flow
- JWT authentication and RBAC flow
- JUnit/Mockito + GitHub Actions CI flow

## Internship Engineering Contributions

The project already contained basic service-level tests. The internship contribution extends that foundation with authentication/security coverage and CI automation:

- **JUnit 5 + Mockito:** authentication service and JWT security tests.
- **JWT validation coverage:** valid, invalid and tampered token scenarios.
- **GitHub Actions:** automated backend Maven test execution on pushes and pull requests to `main`.
- **Documentation:** contribution guide and architecture diagrams for technical handover/presentation.

### Test Structure

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
