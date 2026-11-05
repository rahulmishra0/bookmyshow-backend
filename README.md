\# 🎬 BookMyShow Backend System



A production-ready, high-concurrency ticket booking backend built with \*\*Java, Spring Boot, and MySQL\*\*. This project demonstrates enterprise-level backend architecture, including database locking, transaction management, and clean layered design.



\## 🏗️ Architecture \& Tech Stack



\- \*\*Framework:\*\* Spring Boot 4.1.x

\- \*\*Language:\*\* Java 21

\- \*\*Database:\*\* MySQL

\- \*\*ORM:\*\* Spring Data JPA (Hibernate)

\- \*\*Concurrency:\*\* Pessimistic Locking (`@Lock`)

\- \*\*Tools:\*\* Maven, Swagger/OpenAPI



\### Architecture Diagram



```mermaid

graph TD

&#x20;   Client\[Client / Swagger UI] -->|HTTP Requests| Controller\[Controllers]

&#x20;   Controller -->|Business Logic| Service\[Services]

&#x20;   Service -->|Database Queries| Repository\[Repositories]

&#x20;   Repository -->|Hibernate/JPA| DB\[(MySQL Database)]

&#x20;   

&#x20;   subgraph BookMyShow Backend

&#x20;       Controller

&#x20;       Service

&#x20;       Repository

&#x20;   end

