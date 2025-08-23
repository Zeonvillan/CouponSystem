# Coupon System Project

## 📌 Project Overview
The **Coupon System Project** is a Spring Boot application that allows users to create, manage, and apply coupons with ease.  
It provides REST APIs for coupon CRUD operations and coupon application logic.

---

## 🚀 API Endpoints

```http
POST    /api/coupons             # Create coupon
GET     /api/coupons             # List all coupons
GET     /api/coupons/{id}        # Get specific coupon
PUT     /api/coupons/{id}        # Update coupon
DELETE  /api/coupons/{id}        # Delete coupon

POST    /api/applicable-coupons  # Find applicable coupons
POST    /api/apply-coupon/{id}   # Apply specific coupon
```

---

## 🛠️ Steps to Run the Project

1. Install **Java 17+** (LTS version recommended).  
2. Install **Maven** (or use the Maven wrapper included in the project).  
3. Create a MySQL database named: `coupon_management_db`.  
4. Update `application.properties` with your database username and password:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/coupon_management_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   ```

5. Build the project using Maven:
   ```sh
   mvn clean install
   ```

6. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```

7. Access the application at:
   ```http
   http://localhost:9000
   ```

8. (Optional) Use tools like **Postman** or **Swagger UI** to test the API endpoints.

---

## 📂 Tech Stack
- Java 17+  
- Spring Boot  
- Spring Data JPA / Hibernate  
- MySQL  
- Maven  

---

## 📞 Contact

- Contact Number: **9545614994**  
- GitHub Profile: [https://github.com/Zeonvillan](https://github.com/Zeonvillan)  
- LinkedIn Profile: [https://www.linkedin.com/in/mavin5/](https://www.linkedin.com/in/mavin5/)  

---
