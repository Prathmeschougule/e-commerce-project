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

### User Side
- User Registration & Login with JWT Authentication
- Browse Products
- View Product Details
- Add to Cart & Checkout
- Order Summary Page

### Admin Panel
- Add / Edit / Delete Products
- Manage Orders
- Role-based access using Spring Security

---

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js & npm
- MySQL
- AWS Account (for deployment)

---

### 🔙 Backend Setup

```bash
cd backend

Run the application:
mvn spring-boot:run
🔜 Frontend Setup
cd frontend
Install dependencies:
npm install
Start the app:
npm start
Make sure the backend server is running on a different port (e.g., 8080), and frontend uses a proxy for API calls (configure in package.json):
"proxy": "http://localhost:8080"

🔐 Authentication
JWT Token is stored in local storage
All protected endpoints are secured with Spring Security
Token is sent in headers: Authorization: Bearer <token>

☁️ Deployment (Optional)
Deploy Backend on AWS
Use AWS EC2 instance for deployment
Store images (if any) on S3
Use AWS RDS for MySQL database
Deploy Frontend
Host React build using S3 + CloudFront or Netlify/Vercel

 To Do
 Add payment integration (e.g., Razorpay/Stripe)
 Email notifications
 Product search and filters
 Responsive mobile UI

🤝 Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

📜 License
This project is licensed under the MIT License.
```bash
🙋‍♂️ Developed By
Prathmesh Chougule
Java Full Stack Developer
