-- Active: 1745855485184@@localhost@3306
-- Create the database
CREATE DATABASE IF NOT EXISTS shopping_mall;
USE shopping_mall;

-- Stores table
CREATE TABLE stores (
    store_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL COMMENT 'Fashion, Electronics, Food, etc.',
    floor_number INT NOT NULL,
    location VARCHAR(200) COMMENT 'Description of location in mall',
    contact_phone VARCHAR(20),
    opening_hours VARCHAR(50),
    logo_url VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Rewards table
CREATE TABLE rewards (
    reward_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reward_name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    redeem_points INT NOT NULL COMMENT 'Points required to redeem',
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Customers table
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(15) UNIQUE COMMENT 'Vietnamese phone number',
    full_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    points INT DEFAULT 0,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT chk_email_or_phone_not_null CHECK (email IS NOT NULL OR phone_number IS NOT NULL)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Mall facilities
CREATE TABLE facilities (
    facility_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL COMMENT 'Restroom, Parking, Info Desk, ATM',
    floor_number INT NOT NULL,
    location_description VARCHAR(200),
    opening_hours VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample data for Vietnamese mall

-- Stores
INSERT INTO stores (store_name, category, floor_number, location, contact_phone, opening_hours) VALUES
('Biti\'s', 'Fashion', 1, 'Near North Entrance', '028 3823 4567', '9:00 - 22:00'),
('Thegioididong', 'Electronics', 2, 'Center Area', '028 3823 4568', '8:30 - 21:30'),
('Highlands Coffee', 'Food & Beverage', 3, 'Food Court Area', '028 3823 4569', '7:00 - 23:00'),
('VinMart+', 'Supermarket', 1, 'South Wing', '028 3823 4570', '8:00 - 22:00'),
('FPT Shop', 'Electronics', 2, 'East Wing', '028 3823 4571', '9:00 - 21:00');

-- Rewards
INSERT INTO rewards (reward_name, description, category, redeem_points) VALUES
('Biti\'s Hunter X', 'Popular sneaker model', 'Footwear', 1200),
('iPhone 14 Pro', 'Latest Apple smartphone', 'Mobile Phones', 29990),
('Cà phê sữa đá', 'Traditional Vietnamese iced coffee', 'Beverage', 45),
('Bánh mì chả lụa', 'Vietnamese sandwich with pork roll', 'Food', 25),
('Laptop Dell XPS 15', 'Premium business laptop', 'Computers', 39990);

-- Customers
INSERT INTO customers (phone_number, full_name, email, password) VALUES
('0912345678', 'Nguyễn Văn A', 'nguyenvana@gmail.com', 'a'),
('0987654321', 'Trần Thị B', 'tranthib@yahoo.com','b'),
('0909123456', 'Lê Văn C', NULL, 'c');

-- Facilities
INSERT INTO facilities (name, type, floor_number, location_description, opening_hours) VALUES
('Parking Lot', 'Parking', -1, 'Basement level', '24/7'),
('Information Desk', 'Info Desk', 1, 'Main entrance', '8:00 - 22:00'),
('Restroom', 'Restroom', 2, 'Near elevator', 'Mall hours'),
('ATM Vietcombank', 'ATM', 1, 'North Wing', '24/7'),
('Nursing Room', 'Family', 3, 'Near food court', 'Mall hours');
