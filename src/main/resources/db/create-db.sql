CREATE DATABASE IF NOT EXISTS supermarket;
Use supermarket;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    total_spent BIGINT DEFAULT 0 CHECK (total_spent >= 0),
    spin int default 0 CHECK (spin >= 0)
);

CREATE TABLE prizes (
    prize_id INT AUTO_INCREMENT PRIMARY KEY,
    prize_description VARCHAR(100) NOT NULL,  
    probability DECIMAL(5,4) NOT NULL,  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prize_history (
    user_id INT NOT NULL,
    prize_id INT NOT NULL,
    won_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (prize_id) REFERENCES prizes(prize_id)
);

CREATE TABLE missions (
    mission_id INT AUTO_INCREMENT PRIMARY KEY,
    mission_description TEXT,
    spin_reward INT DEFAULT 1,              
    is_active BOOLEAN DEFAULT TRUE          
);

CREATE TABLE user_missions (
    user_id INT NOT NULL,
    mission_id INT NOT NULL,
    mission_date DATE NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (mission_id) REFERENCES missions(mission_id) ON DELETE CASCADE,
    UNIQUE KEY (user_id, mission_id, mission_date)  -- One mission per user per day
);

CREATE TABLE purchase_rewards (
    reward_id INT AUTO_INCREMENT PRIMARY KEY,
    threshold BIGINT NOT NULL,
    spin INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_purchase_rewards (
    user_id INT NOT NULL,
    reward_id INT NOT NULL,
    claimed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (reward_id) REFERENCES purchase_rewards(reward_id),
    UNIQUE KEY (user_id, reward_id)
);

-- Insert sample data into users table
INSERT INTO `users`(`email`,`password`,`full_name`) VALUES
('t@gmail.com','123','Miyazaki Hiagari'),
('tung@gmail.com','123','Hoàng Đạo Thành');


