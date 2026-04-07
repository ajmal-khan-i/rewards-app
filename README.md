# 📘 Reward Points Calculation Application

# 📌 Overview

# This is a Spring Boot REST API that calculates customer reward points based on their transactions within a given date range.

# The system supports:

# Customer-wise reward calculation

# Date range filtering (mandatory)

# Monthly and yearly reward aggregation

# Exception handling with structured error responses

# Unit tested service and controller layers



# 🏗️ Tech Stack

# Java 8

# Spring Boot

# Spring Data JPA

# H2 (In memory DB)

# Maven

# JUnit

# Mockito







# 📊 Business Logic

# Reward points are calculated as:

# 

# 💰 Rules:

# If transaction > $100 → 2 points for every $1 above $100 + 50 points for first $50

# If transaction > $50 and ≤ $100 → 1 point per $1 above $50

# If ≤ $50 → 0 points









# 📅 API Design

# 🔹 Get Rewards by Customer and Date Range

# GET /api/rewards/{customerId}?startDate=YYYY-MM-DD\&endDate=YYYY-MM-DD



# Example 1:

# GET /api/rewards/1?startDate=2024-11-01\&endDate=2025-02-28

# 📤 Response Format

# {

# &#x20;   "customerId": 1,

# &#x20;   "customerName": "John",

# &#x20;   "startDate": "2024-11-01",

# &#x20;   "endDate": "2025-02-28",

# &#x20;   "yearlyRewards": {

# &#x20;       "2024": {

# &#x20;           "monthly": {

# &#x20;               "11": 100,

# &#x20;               "12": 280

# &#x20;           },

# &#x20;           "total": 380

# &#x20;       },

# &#x20;       "2025": {

# &#x20;           "monthly": {

# &#x20;               "01": 175,

# &#x20;               "02": 70

# &#x20;           },

# &#x20;           "total": 245

# &#x20;       }

# &#x20;   },

# &#x20;   "totalRewards": 625

# }



# Example 2:

# GET /api/rewards/2?startDate=2024-01-01\&endDate=2025-03-31

# 📤 Response Format

{

&#x20;   "customerId": 2,

&#x20;   "customerName": "Alice",

&#x20;   "startDate": "2024-01-01",

&#x20;   "endDate": "2025-03-31",

&#x20;   "yearlyRewards": {

&#x20;       "2024": {

&#x20;           "monthly": {

&#x20;               "01": 740,

&#x20;               "02": 260,

&#x20;               "03": 50

&#x20;           },

&#x20;           "total": 1050

&#x20;       }

&#x20;   },

&#x20;   "totalRewards": 1050

}

# 

# ❌ Error Response Format

# {

# &#x20; "timestamp": "2026-04-07T10:15:30",

# &#x20; "status": 400,

# &#x20; "error": "Invalid date range",

# &#x20; "message": "Start date must be before end date",

# &#x20; "path": "/api/rewards/1"

# }





# ⚠️ Validations



# ✔ Date Rules

# Start date is mandatory

# End date is mandatory

# Start date must be ≤ End date



# ✔ Data Rules

# Customer must exist

# Transactions must exist in given range





# 🧠 Edge Cases Handled

# No transactions → 404 / custom exception

# Invalid customer → 404

# Invalid date range → 400

# Empty transaction list in range

# Multi-year date range support





# 🧪 Testing

# ✔ Unit Tests Covered

# Reward calculation logic

# Customer not found scenario

# No transactions scenario

# Valid API response validation

# Mocked repository interactions

# 

# Run tests:

# 

# mvn test





# 📂 Project Structure

# com.app.rewards

# │

# ├── controller

# ├── service

# ├── repository

# ├── model

# ├── dto

# ├── exception

# └── config





# 🚀 How to Run

# 1\. Clone repo

# git clone https://github.com/your-username/rewards-app.git



# 2\. Build project

# mvn clean install



# 3\. Run application

# mvn spring-boot:run



👨‍💻 Author



Ajmal Khan

Java Spring Boot Developer



🏁 Summary



This project demonstrates:



Clean layered architecture

Real-world business logic implementation

REST API design best practices

Unit testing with Mockito

Proper error handling strategy

