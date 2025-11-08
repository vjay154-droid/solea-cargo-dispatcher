Solea Cargo Dispatcher

Overview

Solea Cargo Dispatcher is a Spring Boot backend application for managing cargo orders within the Solea Intergalactic Empire. The system supports operations like creating, updating, and querying products, vehicles, and planets, while handling validation and error scenarios.

Features

Manage Products: create, update, and fetch product information.

Manage Planets: fetch planets and their data.

Manage Vehicles: select vehicles based on cargo requirements.

Place Orders: automatically calculate required vehicle based on order volume.

Exception Handling: consistent error responses via a custom RestExceptionHandler.

Uses JSON data files in src/main/resources/data for initial data loading.

Getting Started
Prerequisites

Java 21

Maven

IDE (IntelliJ, Eclipse, VS Code, etc.)

Build and Run

1. Clone the repository:
    git clone <repository-url>
    cd solea-cargo-dispatcher

2. Build the project:
   mvn clean install

3. Run the application:
   mvn spring-boot:run

The server will start at http://localhost:3003
(Change port in application.yaml file if required)

API Endpoints
Products:
GET /products – Get all products.
GET /products/{id} – Get product by ID.
POST /products – Create a new product.
PATCH /products/{id} – Update an existing product. At least one field must be provided.

Planets:
GET /planets – Get all planets.
GET /planets/{id} – Get planet by ID.

Vehicles:
GET /vehicles - Get all vehicles.
GET /Vehicles/{id} - Get vehicle by ID.

Orders:
POST /orders – Place an order. Automatically selects appropriate vehicle based on volume and speed.
GET /orders - Get all orders.
GET /orders/{id} - Get order by ID.

Data Loading

The application loads initial data from JSON files in src/main/resources/data using the DataLoader service. This allows easy modification of products, planets, and vehicles without changing code.

esting

Unit tests use JUnit 5 and Mockito.

Controller validation tests use MockMvc.

Tests cover:

Positive and negative cases for CRUD operations.

Validation errors.

Exception handling.

Run Tests:
mvn test

Logging

Logging is included in controllers and services to track incoming requests and important events. 
Logging is include in RestExceptionHandler to log the errors.



