# PayCore Architecture & Engineering Overview

## High-Level Architecture

```mermaid
flowchart LR
    U[Admin / Employee] --> FE[Angular 17 SPA]
    FE -->|HTTP/REST + JWT| BE[Spring Boot 3 REST API]
    BE --> SEC[Spring Security 6 + JWT]
    BE --> SVC[Service Layer]
    SVC --> REP[Spring Data JPA Repositories]
    REP --> DB[(PostgreSQL / H2)]
    BE --> NOTIF[Notification Service]
    CI[GitHub Actions] --> TEST[Maven + JUnit 5 + Mockito]
    TEST --> BE
```

## Request Flow

```mermaid
sequenceDiagram
    participant User
    participant Angular
    participant API
    participant Security
    participant Service
    participant DB

    User->>Angular: Login / API action
    Angular->>API: HTTP request + JWT
    API->>Security: Validate JWT + role
    Security-->>API: Authorized request
    API->>Service: Execute business logic
    Service->>DB: Read/write data
    DB-->>Service: Result
    Service-->>API: DTO / response
    API-->>Angular: JSON response
    Angular-->>User: Updated UI
```

## Payroll Flow

```mermaid
flowchart TD
    A[Employee Salary Structure] --> B[Calculate Gross Salary]
    B --> C[Read Unpaid Leave]
    C --> D[Calculate Leave Deduction]
    D --> E[PF / Tax / Other Deductions]
    E --> F[Calculate Net Salary]
    F --> G[Generate Payslip]
    G --> H[Send Notification]
```

## Security Flow

```mermaid
flowchart LR
    L[Login Credentials] --> AM[AuthenticationManager]
    AM --> JWT[JWT Token]
    JWT --> INT[JwtAuthenticationFilter]
    INT --> VAL[Token Validation]
    VAL --> ROLE[RBAC / Role Check]
    ROLE --> CTRL[Protected REST Controller]
```

## Testing & CI Flow

```mermaid
flowchart LR
    DEV[Code Push / Pull Request] --> GA[GitHub Actions]
    GA --> JAVA[Java 17]
    JAVA --> MAVEN[Maven Test]
    MAVEN --> JUNIT[JUnit 5 + Mockito]
    JUNIT --> RESULT{Tests Pass?}
    RESULT -->|Yes| OK[CI Green]
    RESULT -->|No| FAIL[CI Failed]
```

## Layer Responsibilities

| Layer | Responsibility |
|---|---|
| Angular | UI, routing, forms, HTTP calls, role-based views |
| Controller | REST endpoints and request/response handling |
| Service | Payroll, leave, employee and notification business rules |
| Repository | Database access through Spring Data JPA |
| Entity | Persistent domain model |
| Security | JWT authentication, authorization and RBAC |
| Tests | Regression protection for business and security flows |
| CI | Automated backend build/test validation |
