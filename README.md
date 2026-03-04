🛸 Drone Dispatch Controller API
A Spring Boot RESTful service for managing a fleet of 10 drones capable of delivering medical loads. 
This service handles drone registration, loading, and real-time battery monitoring.

🚀 Quick Start
Prerequisites
Java 21
Spring Boot 4.0.3
Maven 4.0.0

Build and Run
Open a terminal in the project root.
Run the application:

Bash
mvn spring-boot:run

The API will be available at: http://localhost:8080/api
access swagger interface on http://localhost:8080/swagger-ui/index.html
JDBC URL: jdbc:h2:mem:dronedb
User: sa | Password: (blank)

🛠 Architectural Overview
The project follows a standard Layered Architecture to ensure separation of concerns:
Controllers: Handle HTTP requests and JSON validation.
Services: Enforce business rules (Weight limits, Battery minimums).
Repositories: Data access layer using Spring Data JPA.
DTOs: Decouple the API contract from the internal Database schema.
Schedulers: Background tasks for automated battery auditing.

📋 Functional Rules Enforced
Weight Limit: A drone cannot be loaded with items exceeding its specific weightLimit (Max 500g).
Battery Guard: Any attempt to change a drone's state to LOADING is blocked if battery levels are below 25%.
Automated Audit: A background task runs every 30 seconds to log the battery levels of all drones into the BATTERY_AUDIT_LOG table.
Data Integrity: Drone serial numbers are unique identifiers. Medication codes and names follow strict regex patterns.

📍 API Endpoints
Method,Endpoint,Description
POST,/api/drones,Register a new drone.
POST,/api/drones/{serial}/load,Load medications onto a drone.
GET,/api/drones/available,Get drones available for loading.
GET,/api/drones/{serial}/medications,Check medications on a specific drone.
GET,/api/drones/{serial}/battery,Check battery level for a drone.

💡 Design Assumptions
Security: This service is assumed to run within a private dispatch network; therefore, authentication is out of scope for this version.
Preloaded Data: 10 drones are automatically inserted into the H2 database on startup via data.sql to facilitate immediate testing.
Images: Medication images are handled as Base64 strings for simplicity in JSON transfer.
