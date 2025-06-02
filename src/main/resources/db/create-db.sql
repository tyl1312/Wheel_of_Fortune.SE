CREATE DATABASE IF NOT EXISTS `supermarket`;
USE `supermarket`;

-- Users table
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) UNIQUE,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(255),
  `gender` varchar(255),
  `total_spent` bigint DEFAULT 0,
  `spin` int DEFAULT 0,
  PRIMARY KEY (`user_id`)
);

-- Missions table
CREATE TABLE `missions` (
  `mission_id` int NOT NULL AUTO_INCREMENT,
  `mission_description` varchar(255),
  `spin_reward` int DEFAULT 1,
  `mission_type` enum('DAILY','ONE_TIME') NOT NULL,
  PRIMARY KEY (`mission_id`)
);

-- Daily missions progress
CREATE TABLE `user_daily_missions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `mission_id` int NOT NULL,
  `mission_date` date NOT NULL,
  `is_completed` boolean DEFAULT false,
  `is_claimed` boolean DEFAULT false,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`, `mission_id`, `mission_date`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`mission_id`) REFERENCES `missions`(`mission_id`) ON DELETE CASCADE
);

-- One-time missions progress
CREATE TABLE `user_onetime_missions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `mission_id` int NOT NULL,
  `is_completed` boolean DEFAULT false,
  `is_claimed` boolean DEFAULT false,
  `completed_at` timestamp NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`, `mission_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) ON DELETE CASCADE,
  FOREIGN KEY (`mission_id`) REFERENCES `missions`(`mission_id`) ON DELETE CASCADE
);

-- Prizes table
CREATE TABLE `prizes` (
  `prize_id` int NOT NULL AUTO_INCREMENT,
  `prize_description` varchar(255),
  `probability` float NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`prize_id`)
);

-- Prize history (what users won)
CREATE TABLE `prize_history` (
  `user_id` int NOT NULL,
  `prize_id` int NOT NULL,
  `won_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`prize_id`) REFERENCES `prizes`(`prize_id`)
);

-- Purchase rewards (spending milestones)
CREATE TABLE `purchase_rewards` (
  `reward_id` int NOT NULL AUTO_INCREMENT,
  `threshold` bigint NOT NULL,
  `spin` int NOT NULL,
  PRIMARY KEY (`reward_id`)
);

-- User claimed purchase rewards
CREATE TABLE `user_purchase_rewards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `reward_id` int NOT NULL,
  `claimed_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`, `reward_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`reward_id`) REFERENCES `purchase_rewards`(`reward_id`)
);