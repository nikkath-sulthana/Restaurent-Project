# 🍽️ Nik & Jab's Dine House - Restaurant Management System

A full-stack Restaurant Management System built using Spring Boot and React. The application allows customers to log in using OTP, create a dining session, place food orders, and pay their bill digitally.

---

## 🚀 Features

### Authentication
- Phone number login
- OTP verification
- Customer registration

### Dining Session
- Create dining session
- Prevent multiple active sessions
- Track active customer session

### Menu
- View all menu items
- View menu by category
- Admin can add, update, and delete menu items

### Cart
- Create cart
- Add items
- Update quantity
- Remove items
- View cart
- Automatic total calculation

### Orders
- Place order from cart
- Multiple orders per dining session
- Order status tracking
    - PLACED
    - PREPARING
    - READY
    - SERVED
    - CANCELLED

### Billing (Upcoming)
- Generate final bill
- Calculate total amount
- Payment integration
- Close dining session

---

## 🛠️ Tech Stack

### Backend
- Java 21 (or your JDK version)
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

### Frontend
- React
- Vite
- Tailwind CSS
- Axios

### Tools
- IntelliJ IDEA
- Postman
- Git
- GitHub

---

## 📂 Project Structure

src
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── config
└── util

---

## 🗄️ Database Tables

- customers
- otp_details
- dining_sessions
- menu_items
- carts
- cart_items
- orders
- order_items

---

## 📌 API Modules

### Authentication
- Send OTP
- Verify OTP

### Menu
- Get all menu items
- Get menu item by ID
- Add menu item
- Update menu item
- Delete menu item

### Cart
- Create cart
- Add item
- Update quantity
- Remove item
- View cart

### Order
- Place Order
- View Orders

### Billing (Upcoming)
- Checkout
- Generate Bill
- Payment

---

## 🔄 Application Flow

Customer Login
↓
OTP Verification
↓
Create Dining Session
↓
View Menu
↓
Create Cart
↓
Add Items
↓
Place Order
↓
Kitchen Processes Order
↓
Customer Can Place More Orders
↓
Checkout
↓
Generate Final Bill
↓
Payment
↓
Close Dining Session

---

## ⚙️ Installation

### Clone Repository

```bash
git clone <repository-url>
```

### Navigate

```bash
cd restaurant-management-system
```

### Configure Database

Update the database credentials in:

```
application.properties
```

### Run Backend

```bash
mvn spring-boot:run
```

### Run Frontend

```bash
npm install
npm run dev
```

---

## 🔮 Future Enhancements

- JWT Authentication
- Role-based Authorization
- Online Payment Gateway
- Email Notifications
- QR Code Table Login
- Order History
- Dashboard Analytics
- Docker Deployment
- Redis Caching

---

## 👨‍💻 Author

**Nikkath Sulthana**

Backend Developer | Java | Spring Boot | React | MySQL