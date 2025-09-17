# Order Processing (Spring Boot)

Production-ready Spring Boot application that implements an Order Processing backend.

Features:
- Create orders with multiple items
- Retrieve order details
- Update order status
- Cancel pending orders (only while status is PENDING)
- Scheduled job to move PENDING orders to PROCESSING (configurable)
- H2 for local development, production profile for external DB
- Actuator + OpenAPI UI

## Build & Run (local)
```
mvn clean package
java -jar target/order-processing-1.0.0.jar
```

OpenAPI UI: http://localhost:8080/swagger-ui.html or /swagger-ui/index.html

## Docker
```
docker build -t order-processing:latest .
docker run -p 8080:8080 order-processing:latest
```

## Notes
- Update `application-prod.yml` with your production DB settings.
- Consider adding authentication, request auditing, distributed tracing and more robust job processing (e.g. using a queue) for higher scale.
