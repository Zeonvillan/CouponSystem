# CouponSystem

A comprehensive REST API for managing different types of coupons (Cart-wise, Product-wise, and BxGy) with discount calculation and application functionality.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Coupon Types](#coupon-types)
- [Implemented Cases](#implemented-cases)
- [Assumptions](#assumptions)
- [Limitations](#limitations)
- [Testing](#testing)
- [Project Structure](#project-structure)

## Features

- **Multiple Coupon Types**: Cart-wise, Product-wise, and BxGy (Buy X Get Y) coupons
- **CRUD Operations**: Complete Create, Read, Update, Delete operations for coupons
- **Coupon Applicability**: Check which coupons are applicable to a given cart
- **Discount Calculation**: Calculate and apply discounts based on coupon rules
- **Expiration Support**: Coupons can have expiration dates
- **Comprehensive Validation**: Input validation and error handling
- **Unit Testing**: Extensive test coverage for all components
- **Health Monitoring**: Health check and statistics endpoints

## Technology Stack

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **MySQL 8.0** (Production database)
- **H2 Database** (Testing only)
- **Maven** (Build tool)
- **JUnit 5** (Testing)
- **Mockito** (Mocking framework)

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher (running on localhost:3306)
- MySQL user: `root` with password: `root`

### Database Setup

1. **Install MySQL**
   - Install MySQL 8.0 or higher
   - Ensure MySQL is running on `localhost:3306`
   - Create user `root` with password `root` (or use existing root user)

2. **Create Database**
   ```sql
   CREATE DATABASE IF NOT EXISTS coupon_management_db;
   ```

   Or run the provided script:
   ```bash
   mysql -u root -p < database-setup.sql
   ```

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CouponSystem
   ```

2. **Ensure MySQL is running**
   - Start MySQL service
   - Verify connection: `mysql -u root -p`

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080/api`
   - Health Check: `http://localhost:8080/api/health`
   - Database: MySQL on `localhost:3306/coupon_management_db`

### Sample Data

The application automatically creates sample coupons on startup:
- Cart-wise: 10% off on carts over Rs. 100
- Product-wise: 20% off on Product 1
- BxGy: Buy 3 of Product 1 or 2, Get 1 of Product 3 free

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Create Coupon
```http
POST /coupons
Content-Type: application/json
```

**Cart-wise Coupon Example:**
```json
{
  "type": "cart-wise",
  "description": "10% off on carts over Rs. 100",
  "threshold": 100,
  "discount": 10,
  "discountType": "PERCENTAGE",
  "expirationDate": "2024-12-31T23:59:59"
}
```

**Product-wise Coupon Example:**
```json
{
  "type": "product-wise",
  "description": "20% off on Product 1",
  "productId": 1,
  "discount": 20,
  "discountType": "PERCENTAGE"
}
```

**BxGy Coupon Example:**
```json
{
  "type": "bxgy",
  "description": "Buy 3 of Product 1 or 2, Get 1 of Product 3 free",
  "buyProducts": [
    {"productId": 1, "quantity": 3},
    {"productId": 2, "quantity": 3}
  ],
  "getProducts": [
    {"productId": 3, "quantity": 1}
  ],
  "repetitionLimit": 2
}
```

#### 2. Get All Coupons
```http
GET /coupons
```

#### 3. Get Coupon by ID
```http
GET /coupons/{id}
```

#### 4. Update Coupon
```http
PUT /coupons/{id}
Content-Type: application/json
```

#### 5. Delete Coupon
```http
DELETE /coupons/{id}
```

#### 6. Get Applicable Coupons
```http
POST /applicable-coupons
Content-Type: application/json
```

**Request Body:**
```json
{
  "cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50},
      {"product_id": 2, "quantity": 3, "price": 30},
      {"product_id": 3, "quantity": 2, "price": 25}
    ]
  }
}
```

**Response:**
```json
{
  "applicable_coupons": [
    {
      "coupon_id": 1,
      "type": "cart-wise",
      "discount": 44.00
    },
    {
      "coupon_id": 3,
      "type": "bxgy",
      "discount": 50
    }
  ]
}
```

#### 7. Apply Coupon
```http
POST /apply-coupon/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50},
      {"product_id": 2, "quantity": 3, "price": 30},
      {"product_id": 3, "quantity": 2, "price": 25}
    ]
  }
}
```

**Response:**
```json
{
  "updated_cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50, "total_discount": 0},
      {"product_id": 2, "quantity": 3, "price": 30, "total_discount": 0},
      {"product_id": 3, "quantity": 4, "price": 25, "total_discount": 50}
    ]
  },
  "total_price": 490,
  "total_discount": 50,
  "final_price": 440
}
```

#### 8. Health Check
```http
GET /health
```

#### 9. Statistics
```http
GET /stats
```

## Coupon Types

### 1. Cart-wise Coupons
- Apply discount to entire cart if total exceeds threshold
- Support percentage and fixed amount discounts
- Discount applied proportionally to all items

### 2. Product-wise Coupons
- Apply discount to specific products
- Support percentage and fixed amount discounts
- Only affects targeted products in cart

### 3. BxGy (Buy X Get Y) Coupons
- Complex "Buy X, Get Y" deals
- Support multiple buy and get products
- Repetition limits for multiple applications
- Free products added to cart with equivalent discount

## Implemented Cases

### Cart-wise Scenarios
✅ **Percentage Discount**: 10% off on carts over Rs. 100  
✅ **Fixed Amount Discount**: Rs. 50 off on carts over Rs. 200  
✅ **Threshold Validation**: Coupon only applies if cart total meets threshold  
✅ **Proportional Distribution**: Discount distributed across all cart items  

### Product-wise Scenarios
✅ **Single Product Discount**: 20% off on specific product  
✅ **Multiple Product Support**: Different discounts for different products  
✅ **Quantity-based Calculation**: Discount applies to all units of the product  
✅ **Product Availability Check**: Coupon only applies if product is in cart  

### BxGy Scenarios
✅ **Basic BxGy**: Buy 2 of Product X, Get 1 of Product Y free  
✅ **Multiple Buy Products**: Buy from array [X, Y, Z]  
✅ **Multiple Get Products**: Get from array [A, B, C]  
✅ **Repetition Limits**: Apply coupon multiple times with limits  
✅ **Complex Scenarios**: Buy 6 of [X, Y], Get 3 of [A, B, C] free  
✅ **Availability Constraints**: Limited by available products in cart  

### Edge Cases Handled
✅ **Insufficient Buy Products**: BxGy not applicable if requirements not met  
✅ **Limited Get Products**: Free products limited by cart availability  
✅ **Expired Coupons**: Automatic expiration checking  
✅ **Inactive Coupons**: Only active coupons are considered  
✅ **Empty Carts**: Graceful handling of empty carts  
✅ **Invalid Inputs**: Comprehensive validation and error messages  

## Assumptions

1. **Product IDs**: Products are identified by Long IDs (1, 2, 3, etc.)
2. **Currency**: All prices are in the same currency (no currency conversion)
3. **Decimal Precision**: Monetary values use 2 decimal places
4. **Single Currency**: No multi-currency support
5. **Cart State**: Cart is stateless - no persistence between requests
6. **Coupon Stacking**: Only one coupon can be applied at a time
7. **Product Availability**: Products in cart are assumed to be available
8. **Price Consistency**: Product prices are provided in the cart request
9. **Time Zone**: All timestamps use system default timezone
10. **Rounding**: Discount calculations use HALF_UP rounding mode

## Limitations

### Current Implementation Limitations

1. **Single Coupon Application**: Cannot stack multiple coupons
2. **No User Context**: No user-specific coupon restrictions
3. **No Usage Limits**: Coupons don't track usage count per user
4. **No Minimum Quantity**: Product-wise coupons don't support minimum quantity requirements
5. **No Category Support**: No product category-based coupons
6. **No Geographic Restrictions**: No location-based coupon applicability
7. **No Time-based Rules**: No day-of-week or time-of-day restrictions
8. **No Inventory Integration**: No real-time inventory checking
9. **No Audit Trail**: No tracking of coupon usage history
10. **No A/B Testing**: No support for coupon experiments

### Unimplemented Cases

1. **Tiered Discounts**: Progressive discounts based on cart value
2. **Combo Offers**: Complex product combination offers
3. **Loyalty Points**: Integration with loyalty point systems
4. **Dynamic Pricing**: Real-time price adjustments
5. **Bulk Discounts**: Quantity-based discount tiers
6. **Seasonal Coupons**: Automatic activation based on dates
7. **Referral Coupons**: User referral-based discounts
8. **Shipping Discounts**: Free shipping or shipping discount coupons

### Technical Limitations

1. **Database**: Currently uses in-memory H2 database
2. **Scalability**: No horizontal scaling considerations
3. **Caching**: No caching layer for performance optimization
4. **Rate Limiting**: No API rate limiting implemented
5. **Authentication**: No user authentication or authorization
6. **Monitoring**: Basic health checks only
7. **Logging**: Standard logging without centralized log management
8. **Configuration**: Limited external configuration options

## Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CouponServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Coverage
- **Service Layer**: Comprehensive unit tests for all business logic
- **Controller Layer**: Integration tests for all API endpoints
- **Edge Cases**: Tests for error conditions and boundary cases
- **Validation**: Tests for input validation and error handling

### Test Categories
- **Unit Tests**: Individual component testing
- **Integration Tests**: API endpoint testing
- **Edge Case Tests**: Boundary condition testing
- **Validation Tests**: Input validation testing

## Project Structure

```
src/
├── main/
│   ├── java/com/zeonvillan/test/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA entities
│   │   ├── exception/      # Custom exceptions
│   │   ├── model/          # Domain models
│   │   ├── repository/     # Data repositories
│   │   └── service/        # Business logic
│   └── resources/
│       └── application.yml # Application configuration
└── test/
    └── java/com/zeonvillan/test/
        ├── controller/     # Controller tests
        └── service/        # Service tests
```

## Troubleshooting

### Database Connection Issues

1. **MySQL Connection Refused**
   ```
   Error: java.sql.SQLException: Connection refused
   ```
   - Ensure MySQL is running: `sudo systemctl start mysql` (Linux) or start MySQL service (Windows)
   - Check MySQL port: `netstat -an | grep 3306`

2. **Access Denied for User 'root'**
   ```
   Error: Access denied for user 'root'@'localhost'
   ```
   - Reset root password: `ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';`
   - Grant privileges: `GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';`

3. **Database Does Not Exist**
   ```
   Error: Unknown database 'coupon_management_db'
   ```
   - Run: `CREATE DATABASE coupon_management_db;`
   - Or use the provided `database-setup.sql` script

4. **Table Creation Issues**
   - Check Hibernate logs for DDL statements
   - Ensure user has CREATE privileges
   - Verify MySQL version compatibility

### Application Issues

1. **Port Already in Use**
   ```
   Error: Port 8080 was already in use
   ```
   - Change port in `application.properties`: `server.port=8081`
   - Or kill process using port 8080

2. **Bean Creation Errors**
   - Check all dependencies are properly imported
   - Verify database connection
   - Check application logs for specific error details

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement changes with tests
4. Ensure all tests pass
5. Submit a pull request

## License

This project is licensed under the MIT License.
