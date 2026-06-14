# Mom's Bakery System (MBS) - Project Instructions

## Project Summary
A flexible order management system for a home-based bakery. The system focuses on a manual confirmation workflow, allowing the baker to manage
production capacity based on requested delivery dates from customers.

## Core Tech Stack
- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **Security:** Spring Security & JWT (Admin: Owner, Public: Customers)
- **Database:** JPA/Hibernate (PostgreSQL) with JPA Auditing
- **Documentation:** Springdoc OpenAPI (Swagger)
- 
## Architectural Mandates
1. *Entity Design:*
- All domain entities must extend `AbstractAuditingEntity`.
- Use `@Getter` and `@Setter` instead of `@Data`.
- Primary Keys must be **UUID**.
- Use **Enums** for Order Status: `WAITING_CONFIRMATION`, `CONFIRMED`, `IN_PRODUCTION`, `READY_FOR_PICKUP`, `DELIVERED`, `CANCELLED`.
- 
2. *Flexible Order Logic:*
- Orders must include a `requestedDeliveryDate` (LocalDateTime) and `notes` for specific requests.
- The confirmation process is manual: The Admin (Owner) reviews requested dates and moves status to `CONFIRMED`.

3. *Security & Access:*
- Public: View Catalog, Place Orders (Anonymous or Registered).
- Admin: Product/Category Management, Order Dashboard, Status Updates.
- Implement `CustomAccessDeniedHandler` and `CustomAuthenticationEntryPoint`.

4. *Data Transfer (DTOs):*
- Use Java 21 **Records** for all API Requests and Responses.
- Wrap all API responses in a consistent `WebResponse<T>` envelope.

5. *Validation:*
- Use `@Valid` in Controllers for input validation.
- Use a dedicated **`ValidationService`** for business rules (e.g., ensuring `requestedDeliveryDate` is in the future).

## Naming Conventions
- Packages: `com.rizalamar.momsbakery`
- Sub-packages: `domain`, `repository`, `service`, `controller`, `dto`, `security`, `config`, `exception`.
- Database Tables: snake_case.
- Variables & Methods: camelCase.

## Key Domains
- **Category:** (Sweet, Savory, etc.)
- **Product:** (Name, Price, Stock status, ImageURL)
- **Order:** (CustomerName, WA_Number, RequestedDeliveryDate, TotalPrice, Status Enum)
- **OrderItem:** (Product, Quantity, Subtotal)