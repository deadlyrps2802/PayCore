# PayCore

Payroll & HR Management System built with Spring Boot, Angular, Spring Security/JWT and PostgreSQL/H2.

## Internship Engineering Additions

The project already contained baseline service-level JUnit coverage. During the internship, the automated quality layer was extended with authentication/security tests and CI automation.

### Added Testing
- `backend/src/test/java/com/paycore/service/AuthServiceTest.java` — authentication success and missing-user scenarios using JUnit 5 + Mockito.
- `backend/src/test/java/com/paycore/security/JwtTokenProviderTest.java` — JWT generation/username extraction, invalid-token rejection and tampered-token rejection.
- Existing service tests remain in place for employee, leave and salary business logic.

### Continuous Integration
`.github/workflows/ci.yml` runs the backend Maven test suite on pushes and pull requests targeting `main` using Java 17.

Run tests locally:

```bash
cd backend
mvn test
```

## Internship Contribution Summary

**Focus:** improve regression protection around authentication/security and automate backend validation in CI.

**Technical tools:** JUnit 5, Mockito, Spring Security, JWT, Maven and GitHub Actions.

**Suggested next steps:** MockMvc API tests, Spring Boot integration tests, standardized `@ControllerAdvice` error responses, OpenAPI/Swagger documentation, audit logging and coverage reporting.
