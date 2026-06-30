CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

CREATE TABLE IF NOT EXISTS accounts (
    account_number INT PRIMARY KEY,
    customer_name VARCHAR(100),
    balance DOUBLE
);

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number INT,
    transaction_type VARCHAR(20),
    amount DOUBLE,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
