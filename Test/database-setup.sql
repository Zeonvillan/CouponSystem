-- Coupon Management System Database Setup
-- Run this script in MySQL to create the database and user

-- Create database
CREATE DATABASE IF NOT EXISTS coupon_management_db;

-- Use the database
USE coupon_management_db;

-- Create user (if needed)
-- CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
-- GRANT ALL PRIVILEGES ON coupon_management_db.* TO 'root'@'localhost';
-- FLUSH PRIVILEGES;

-- The tables will be created automatically by Hibernate when the application starts
-- due to spring.jpa.hibernate.ddl-auto=update configuration

-- Optional: Insert some sample data (this will be done by DataInitializer in the application)
-- But you can run these manually if needed:

/*
-- Sample Cart-wise Coupon
INSERT INTO coupons (coupon_type, description, is_active, threshold, discount, discount_type, created_at, updated_at, expiration_date) 
VALUES ('cart-wise', '10% off on carts over Rs. 100', true, 100.00, 10.00, 'PERCENTAGE', NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY));

-- Sample Product-wise Coupon  
INSERT INTO coupons (coupon_type, description, is_active, product_id, discount, discount_type, created_at, updated_at, expiration_date)
VALUES ('product-wise', '20% off on Product 1', true, 1, 20.00, 'PERCENTAGE', NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY));

-- Sample BxGy Coupon
INSERT INTO coupons (coupon_type, description, is_active, repetition_limit, created_at, updated_at, expiration_date)
VALUES ('bxgy', 'Buy 3 of Product 1 or 2, Get 1 of Product 3 free', true, 2, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY));
*/

-- Check if database is created successfully
SELECT 'Database coupon_management_db created successfully!' as status;
