# PayCore Architecture & Engineering Overview

## High-Level Architecture

```mermaid
flowchart LR
    U[Admin / Employee] --> FE[Angular 17 SPA]
    FE -->|HTTP/REST + JWT| BE[Spring Boot 3 REST API]
    FE -. API exploration .-> SW[Swagger / OpenAPI]
    BE --> SEC[Spring Security 6 + JWT]
    BE --> SVC[Service Layer]
    BE --> EX[Global Exception Handler]
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
    participant Errors

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
    Service-->>Errors: Unhandled exception
    Errors-->>API: Standardized ApiResponse error
```

## Payroll Flow

```mermaid
flowchart TD
    A[Employee Salary Structure] --> B[Calculate Gross Salary]
    B --> C[Read Approved Unpaid Leave]
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
    VAL -. Invalid token .-> DENY[Reject / Continue unauthenticated]
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

## API Documentation Flow

```mermaid
flowchart LR
    CONTROLLERS[Spring REST Controllers] --> OPENAPI[Springdoc OpenAPI]
    OPENAPI --> SWAGGER[Swagger UI]
    SWAGGER --> DEV[Developer / Reviewer]
```

## Layer Responsibilities

| Layer | Responsibility |
|---|---|
| Angular | UI, routing, forms, HTTP calls, role-based views |
| Controller | REST endpoints and request/response handling |
| Exception Handler | Centralized validation and REST error responses |
| Service | Payroll, leave, employee and notification business rules |
| Repository | Database access through Spring Data JPA |
| Entity | Persistent domain model |
| Security | JWT authentication, authorization and RBAC |
| OpenAPI | Interactive REST API documentation |
| Tests | Regression protection for business and security flows |
| CI | Automated backend build/test validation |
