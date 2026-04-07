# Reward Points Calculation Application

---

# Overview

This is a **Spring Boot REST API** that calculates customer reward points based on their transactions within a given date range.

The system supports:

- Customer-wise reward calculation
- Date range filtering (mandatory)
- Monthly and yearly reward aggregation
- Exception handling with structured error responses
- Unit tested service and controller layers

---

#  Tech Stack

- Java 8
- Spring Boot 2
- Spring Data JPA
- H2 (In-memory DB)
- Maven
- JUnit
- Mockito

---

#  Business Logic

Reward points are calculated as:

## Rules:

- If transaction > $100 → 2 points for every $1 above $100 + 50 points for first $50
- If transaction > $50 and ≤ $100 → 1 point per $1 above $50
- If ≤ $50 → 0 points

---

#  API Design

## Get Rewards by Customer and Date Range

```http
GET /api/rewards/{customerId}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
```
## Example 1

```http
GET /api/rewards/1?startDate=2024-11-01&endDate=2025-02-28

Respone

{
  "customerId": 1,
  "customerName": "John",
  "startDate": "2024-11-01",
  "endDate": "2025-02-28",
  "yearlyRewards": {
    "2024": {
      "11": 100,
      "12": 280
    },
    "2025": {
      "01": 175,
      "02": 70
    }
  },
  "totalRewards": 625
}
```

## Example 2
```http
GET /api/rewards/2?startDate=2024-01-01&endDate=2025-03-31

Response

{
  "customerId": 2,
  "customerName": "Alice",
  "startDate": "2024-01-01",
  "endDate": "2025-03-31",
  "yearlyRewards": {
    "2024": {
      "01": 740,
      "02": 260,
      "03": 50
    }
  },
  "totalRewards": 1050
}
```


## Error Response Format
```
{
    "message": "Customer not found",
    "status": 404,
    "timestamp": "2026-04-07T17:56:15.0371724"
}
```

## Validation
## Date Rules
- Start date is mandatory
- End date is mandatory
- Start date must be ≤ End date

## Data Rules
- Customer must exist
- Transactions must exist in given date range

## Edge Cases Handled
- No transactions found → 404 custom exception
- Invalid customer ID → 404
- Invalid date range → 400 Bad Request
- Empty transaction list in range
- Multi-year date range support

# Testing
## Unit Test Covered
- Reward calculation logic
- Customer not found scenario
- No transactions scenario
- Valid API response validation
- Mocked repository interactions

## Run Tests
- mvn test

# How to Run
1. Clone Repository - 
https://github.com/ajmal-khan-i/rewards-app

2. Build Project - 
mvn clean install

3. Run Application - 
mvn spring-boot:run

# Swagger API Documentation

This project uses SpringDoc OpenAPI (Swagger UI) for API documentation.

## Swagger UI URL
Once the application is running, access Swagger UI at:
http://localhost:8080/swagger-ui/index.html

# Author
Ajmal Khan - Java Spring Boot Developer

# Summary
- Clean layered architecture
- Real-world business logic implementation
- REST API best practices
- Unit testing with Mockito
- Proper exception handling strategy
- Swagger API documentation integration