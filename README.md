> 🚧 **Project Status: Under Development**  
> This application is currently in active development. Features may change, and bugs may exist.

# 🛒 Comwen - Full Stack eCommerce Web Application

A modern full-stack eCommerce web application built using **Spring Boot**, **React.js**, and deployed on **AWS**. The application supports user authentication, product listing, secure checkout, and admin product management.

---

## 🔧 Tech Stack

### Backend
- Java + Spring Boot
- Spring Framework
- Spring Data JPA (Hibernate)
- Spring Security 6
- JWT (JSON Web Token)
- RESTful APIs
- MySQL Database
- Maven
- AWS EC2 / S3 / RDS (for deployment)

### Frontend
- React.js
- HTML5 / CSS3 / JavaScript (ES6+)
- Axios
- React Router
- Bootstrap / Tailwind CSS (Optional)

---

## ✅ Features

### 👤 User Side
- User Registration & Login with JWT Authentication
- Browse Products
- View Product Details
- Add to Cart & Checkout
- Order Summary Page

### 🛠️ Admin Panel
- Add / Edit / Delete Products
- Manage Orders
- Role-based access using Spring Security

---

## 🚀 Getting Started

### 🔍 Prerequisites
- Java 17+
- Node.js & npm
- MySQL
- AWS Account (for deployment)

---

## 🔙 Backend Setup

```bash
cd backend
Configure application.properties or application.yml with your DB and JWT settings:

spring.datasource.url=jdbc:mysql://localhost:3306/comwen_db
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_jwt_secret_key
Run the application:
