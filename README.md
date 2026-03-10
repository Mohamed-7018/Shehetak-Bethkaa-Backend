# Sehetak Bethakaa – Backend API

Backend service for **Sehetak Bethakaa**, a health management application that allows users to manage personal health profiles, diseases, and authentication securely.

Built with **Spring Boot**, **Spring Security**, and **JWT authentication**.

---

# Tech Stack

* Java 17+
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* PostgreSQL 
* Maven
* Lombok

---

# Features

## Authentication

* User Registration
* Email Verification
* Login with JWT
* Refresh Token Flow
* Forgot Password / Reset Password
* Logout
* Anonymous (Guest) Authentication

## User Management

* Create user accounts
* Retrieve authenticated user
* Manage health profile

## Disease Management

* Create diseases
* Update diseases
* Delete diseases
* Search diseases by name
* Filter diseases by gender
* Filter diseases by type
* Filter by gender and type

## Health Profile

* Store user health data
* Height, weight, BMI, birthday
* Blood type
* Medical notes
* Current diseases
* Disease history

---

# Project Structure

```id="7mkn5n"
src/main/java/com/example/sehetak_bethakaa

├── annotation
│   └── custom annotations
│
├── config
│   └── security and jwt configuration
│
├── controller
│   └── REST API controllers
│
├── dto
│   ├── request
│   └── response
│
├── entity
│   └── JPA entities
│
├── exception
│   └── custom exceptions
│
├── repository
│   └── Spring Data JPA repositories
│
├── service
│   └── business logic
│
└── util
    └── custom utils
```

---

# Environment Variables

Example `.env` configuration:

```id="g1s6g4"
DB_URL=jdbc:postgresql://localhost:5432/sehetak
DB_USERNAME=postgres
DB_PASSWORD=password

JWT_SECRET=your-secret-key
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=86400000

MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

---

# Running the Project

### 1. Clone the repository

```id="m0qvjq"
git clone https://github.com/your-username/sehetak-bethakaa-backend.git
```

---

### 2. Navigate to the project

```id="qez4rq"
cd sehetak-bethakaa-backend
```

---

### 3. Run the application

```id="r66v2o"
mvn spring-boot:run
```

Server starts on:

```id="7fyq5v"
http://localhost:8080
```

---

# Future Improvements

* Rate limiting
* Audit logging
* API documentation with Swagger
* Role-based access control improvements
* Guest account cleanup scheduler
* Health analytics module

---

# License

This project is licensed under the MIT License.

---

# Author

Developed by **Mohamed Samir**.
