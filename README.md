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

# Authentication Flow

### Register

```id="g3df4p"
POST /api/auth/register
```

Creates a new user and sends an **email verification link**.

---

### Verify Account

```id="tn9k6c"
GET /api/auth/accountVerification/{token}
```

Activates the user account.

---

### Login

```id="e7zk1p"
POST /api/auth/login
```

Returns:

```json id="l83x5g"
{
  "accessToken": "jwt_access_token",
  "refreshToken": "jwt_refresh_token",
  "uuid": "user_id"
}
```

---

### Refresh Token

```id="tf5mdh"
POST /api/auth/refresh
```

Returns a new access token when the previous one expires.

---

### Logout

```id="vyc4ma"
POST /api/auth/logout
```

Clears the security context.

---

# Anonymous Authentication

Users can access the application without registration.

### Endpoint

```id="smzsk8"
POST /api/auth/anonymous
```

Flow:

```id="54thk2"
Client
   │
   │ Continue as Guest
   ▼
Create guest user
   ▼
Generate JWT tokens
   ▼
Return access + refresh token
```

Guest users have the role:

```id="5spqv1"
ROLE_GUEST
```

Permissions for guest users are limited.

---

# Disease API

### Get all diseases

```id="5ln6qg"
GET /api/diseases
```

---

### Search by name

```id="9w9k3n"
GET /api/diseases/search?name=diabetes
```

---

### Filter by gender

```id="93y6nh"
GET /api/diseases/gender/{gender}
```

---

### Filter by type

```id="0dx9r3"
GET /api/diseases/type/{type}
```

---

### Filter by gender and type

```id="m0xf4y"
GET /api/diseases/filter?gender=BOTH&type=PERSONAL
```

---

# Health Profile API

### Create or Update Profile

```id="1lhrga"
POST /api/health-profile
```

Example request:

```json id="kyu9um"
{
  "phoneNumber": "+201234567890",
  "name": "Mohamed Samir",
  "birthday": "1990-05-15",
  "height": 175,
  "weight": 70,
  "bloodType": "O+",
  "allergies": "Peanuts",
  "notes": "No smoking",
  "diseaseIds": [1,3],
  "historyDiseaseIds": [2]
}
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
