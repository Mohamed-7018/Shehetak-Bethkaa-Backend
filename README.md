
<div align="center">

# 🏥 Sehetak Bethakaa Backend

<img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=26&duration=3000&pause=1000&color=2ECC71&center=true&vCenter=true&width=600&lines=Health+Management+Backend+API;Spring+Boot+%7C+JWT+Security;PostgreSQL+%7C+Hibernate+%7C+JPA;Secure+Authentication+System" />

Backend service for **Sehetak Bethakaa**, a health management platform that allows users to securely manage their **health profiles, diseases, and authentication**.

</div>

---

# 🚀 Tech Stack

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Security-SpringSecurity-green?style=for-the-badge)
![JWT](https://img.shields.io/badge/Auth-JWT-yellow?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue?style=for-the-badge&logo=postgresql)
![Hibernate](https://img.shields.io/badge/ORM-Hibernate-brown?style=for-the-badge)
![Maven](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven)

</div>

---

# 📌 Overview

**Sehetak Bethakaa** is a secure backend API designed to manage personal health data.

The system allows users to:

- securely authenticate
- maintain health profiles
- track diseases
- manage medical history

It is built following **clean architecture principles** using **Spring Boot and REST APIs**.

---

# ✨ Features

## 🔐 Authentication

- User Registration
- Email Verification
- Login with JWT
- Refresh Token Flow
- Forgot Password
- Reset Password
- Logout
- Anonymous (Guest) Authentication

---

## 👤 User Management

- Create user accounts
- Retrieve authenticated user
- Manage health profile
- Update personal information

---

## 🦠 Disease Management

- Create diseases
- Update diseases
- Delete diseases
- Search diseases by name
- Filter diseases by gender
- Filter diseases by type
- Combined filtering

---

## ❤️ Health Profile

Users can store and manage their health data:

- Height
- Weight
- BMI
- Birthday
- Blood type
- Medical notes
- Current diseases
- Disease history

---

# 🧱 Architecture

```

Controller
↓
Service
↓
Repository
↓
Database (PostgreSQL)

```

Security Layer:

```
API _Key
↓
Spring Security
↓
JWT Authentication
↓
Authorization Filters

```

---

# 📁 Project Structure

```

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
└── helper utilities

```

---

# ⚙️ Environment Variables

Example `.env` configuration

```

DB_URL=jdbc:postgresql://localhost:5432/sehetak
DB_USERNAME=postgres
DB_PASSWORD=password

JWT_SECRET=your-secret-key
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=86400000

MAIL_USERNAME=[your_email@gmail.com](mailto:your_email@gmail.com)
MAIL_PASSWORD=your_app_password

````

---

# ▶️ Running the Project

## 1 Clone the repository

```bash
git clone https://github.com/your-username/sehetak-bethakaa-backend.git
````

---

## 2 Navigate to project

```bash
cd sehetak-bethakaa-backend
```

---

## 3 Run the application

```bash
mvn spring-boot:run
```

Server runs on

```
http://localhost:8080
```

---

# 🔮 Future Improvements

* Rate limiting
* Audit logging
* Swagger / OpenAPI documentation
* Role-based access control
* Guest account cleanup scheduler
* Health analytics module
* Docker containerization
* CI/CD pipeline

---

# 📊 Repository Stats

<div align="center">

![GitHub repo size](https://img.shields.io/github/repo-size/Mohamed-7018/sehetak-bethakaa-backend)
![GitHub stars](https://img.shields.io/github/stars/Mohamed-7018/sehetak-bethakaa-backend)
![GitHub forks](https://img.shields.io/github/forks/Mohamed-7018/sehetak-bethakaa-backend)
![GitHub issues](https://img.shields.io/github/issues/Mohamed-7018/sehetak-bethakaa-backend)

</div>

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch

```
git checkout -b feature/new-feature
```

3. Commit your changes

```
git commit -m "Add new feature"
```

4. Push your branch

```
git push origin feature/new-feature
```

5. Open a Pull Request

---

# 📄 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

**Mohamed Samir**

Backend Developer
Java | Spring Boot | APIs | Security

---

<div align="center">

⭐ If you like this project, consider **starring the repository**

</div>

