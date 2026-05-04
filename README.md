# Library Management System (Spring Boot)

A Library Management System built with **Spring Boot 3**, featuring JWT authentication, role-based access control, and a responsive web interface using Thymeleaf.

## ✨ Features

- **JWT (JSON Web Token) authentication** for API endpoints
- **Session-based authentication** for web pages (Thymeleaf)
- **Role-based access control** (User & Admin)
- **Secure password hashing** with BCrypt
- **RESTful API** for Books, Members, and Loans
- **H2 in-memory database** for easy setup
- **CORS enabled** for API
- **Exception handling** and input validation
- **Demo data** seeded on startup

## 🚀 Quick Start

### 1. Backend Setup (Spring Boot)

```bash
# Go to the backend directory
cd library-management-utspbold

# Run with Maven (if installed globally)
mvn spring-boot:run

# Or with Maven wrapper (Windows)
mvnw.cmd spring-boot:run

# Or with Maven wrapper (Linux/Mac)
./mvnw spring-boot:run
```

The backend will run at `http://localhost:8481`

### 2. Accessing the Application

- **Web Interface:**
  - Login: `http://localhost:8481/login`
  - Home: `http://localhost:8481/`
  - Books: `http://localhost:8481/books`
  - Members: `http://localhost:8481/members`
  - Loans: `http://localhost:8481/loans`
- **H2 Console:** `http://localhost:8481/h2-console`

## 🔑 Default Users

| Username | Password   | Role  |
|----------|------------|-------|
| Alogo    | Alogo.24   | ADMIN |
| Ronaldo  | Ronaldo.7  | USER  |

## 📡 API Endpoints

### Authentication
- `POST /api/auth/login` – Login user (JWT)
- `POST /api/auth/register` – Register new user (JWT)
- `GET /api/auth/me` – Get current user info (JWT)

### Books
- `GET /api/books` – Get all books
- `POST /api/books` – Create new book
- `PUT /api/books/{id}` – Update book
- `DELETE /api/books/{id}` – Delete book

### Members
- `GET /api/members` – Get all members
- `POST /api/members` – Create new member
- `PUT /api/members/{id}` – Update member
- `DELETE /api/members/{id}` – Delete member

### Loans
- `GET /api/loans` – Get all loans
- `POST /api/loans` – Create new loan
- `PUT /api/loans/{id}` – Update loan
- `DELETE /api/loans/{id}` – Delete loan

## 🛠️ Technologies Used

- **Spring Boot 3.x**
- **Spring Security** (JWT & session-based)
- **Spring Data JPA**
- **H2 Database**
- **Thymeleaf** (for web pages)
- **Maven**

## 🔧 Configuration

- **Database:** H2 in-memory, auto-created on startup
- **Authentication:**
  - API endpoints use JWT
  - Web pages use session-based login
- **JWT Secret:** Configured in `JwtUtil.java` and `application.properties`

## 🗃️ Demo Data

On startup, the system seeds:
- 2 sample books
- 2 sample members
- 2 sample loan transactions
- Default users: admin/admin123 and user/user123

## 🚨 Troubleshooting

- **Port 8481 already in use:**
  - Find and kill the process using the port (see README for commands)
- **Maven not found:**
  - Use the Maven wrapper (`mvnw.cmd` or `./mvnw`)
- **Authentication issues:**
  - Use default users or register a new one
- **Database issues:**
  - H2 database is recreated on each startup

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

---


