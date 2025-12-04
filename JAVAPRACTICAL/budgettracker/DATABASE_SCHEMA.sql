-- Budget Tracker Database Schema
-- MySQL Database Schema for Personal Finance Management System

CREATE DATABASE IF NOT EXISTS budget_tracker;
USE budget_tracker;

-- Users table for authentication and profile management
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Expenses table for tracking daily expenses
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category ENUM('FOOD', 'TRANSPORTATION', 'ENTERTAINMENT', 'UTILITIES', 
                  'HEALTHCARE', 'SHOPPING', 'EDUCATION', 'TRAVEL', 'RENT', 'OTHER') NOT NULL,
    payment_mode ENUM('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'UPI', 'NET_BANKING', 'OTHER') NOT NULL,
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, expense_date),
    INDEX idx_user_category (user_id, category),
    INDEX idx_expense_date (expense_date)
);

-- Budgets table for managing spending limits
CREATE TABLE budgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category ENUM('FOOD', 'TRANSPORTATION', 'ENTERTAINMENT', 'UTILITIES', 
                  'HEALTHCARE', 'SHOPPING', 'EDUCATION', 'TRAVEL', 'RENT', 'OTHER') NOT NULL,
    budget_limit DECIMAL(10,2) NOT NULL,
    spent_amount DECIMAL(10,2) DEFAULT 0.00,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_category_date (user_id, category, start_date, end_date),
    INDEX idx_user_active (user_id, start_date, end_date)
);

-- Goals table for tracking financial goals
CREATE TABLE goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    target_amount DECIMAL(10,2) NOT NULL,
    current_amount DECIMAL(10,2) DEFAULT 0.00,
    target_date DATE,
    status ENUM('ACTIVE', 'COMPLETED', 'PAUSED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_status (user_id, status),
    INDEX idx_user_created (user_id, created_at)
);

-- Insert sample data for testing
INSERT INTO users (username, email, password, first_name, last_name, phone_number) VALUES
('admin', 'admin@budgettracker.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Admin', 'User', '1234567890'),
('john_doe', 'john@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John', 'Doe', '9876543210');

-- Sample expenses
INSERT INTO expenses (user_id, description, amount, category, payment_mode, expense_date) VALUES
(1, 'Grocery Shopping', 85.50, 'FOOD', 'CREDIT_CARD', '2024-01-15'),
(1, 'Gas Station', 45.00, 'TRANSPORTATION', 'DEBIT_CARD', '2024-01-14'),
(1, 'Movie Tickets', 25.00, 'ENTERTAINMENT', 'UPI', '2024-01-13'),
(1, 'Electricity Bill', 120.00, 'UTILITIES', 'NET_BANKING', '2024-01-12'),
(1, 'Doctor Visit', 75.00, 'HEALTHCARE', 'CASH', '2024-01-11');

-- Sample budgets
INSERT INTO budgets (user_id, category, budget_limit, spent_amount, start_date, end_date) VALUES
(1, 'FOOD', 500.00, 85.50, '2024-01-01', '2024-01-31'),
(1, 'TRANSPORTATION', 200.00, 45.00, '2024-01-01', '2024-01-31'),
(1, 'ENTERTAINMENT', 150.00, 25.00, '2024-01-01', '2024-01-31'),
(1, 'UTILITIES', 300.00, 120.00, '2024-01-01', '2024-01-31');

-- Sample goals
INSERT INTO goals (user_id, name, description, target_amount, current_amount, target_date) VALUES
(1, 'Emergency Fund', 'Build emergency fund for 6 months expenses', 5000.00, 1200.00, '2024-12-31'),
(1, 'Vacation Fund', 'Save for summer vacation', 2000.00, 450.00, '2024-06-30'),
(1, 'New Laptop', 'Save for a new laptop', 1500.00, 300.00, '2024-08-15');

-- Views for analytics and reporting

-- Monthly expense summary view
CREATE VIEW monthly_expense_summary AS
SELECT 
    u.id as user_id,
    u.username,
    YEAR(e.expense_date) as year,
    MONTH(e.expense_date) as month,
    e.category,
    SUM(e.amount) as total_amount,
    COUNT(*) as transaction_count
FROM users u
JOIN expenses e ON u.id = e.user_id
GROUP BY u.id, YEAR(e.expense_date), MONTH(e.expense_date), e.category;

-- Budget utilization view
CREATE VIEW budget_utilization AS
SELECT 
    b.id,
    b.user_id,
    u.username,
    b.category,
    b.budget_limit,
    b.spent_amount,
    (b.spent_amount / b.budget_limit * 100) as utilization_percentage,
    (b.budget_limit - b.spent_amount) as remaining_amount,
    b.start_date,
    b.end_date,
    CASE 
        WHEN b.spent_amount > b.budget_limit THEN 'EXCEEDED'
        WHEN (b.spent_amount / b.budget_limit * 100) > 80 THEN 'WARNING'
        ELSE 'NORMAL'
    END as status
FROM budgets b
JOIN users u ON b.user_id = u.id;

-- Goal progress view
CREATE VIEW goal_progress AS
SELECT 
    g.id,
    g.user_id,
    u.username,
    g.name,
    g.target_amount,
    g.current_amount,
    (g.current_amount / g.target_amount * 100) as progress_percentage,
    (g.target_amount - g.current_amount) as remaining_amount,
    g.target_date,
    g.status,
    DATEDIFF(g.target_date, CURDATE()) as days_remaining
FROM goals g
JOIN users u ON g.user_id = u.id;

-- Indexes for better performance
CREATE INDEX idx_expenses_user_date_category ON expenses(user_id, expense_date, category);
CREATE INDEX idx_budgets_date_range ON budgets(start_date, end_date);
CREATE INDEX idx_goals_target_date ON goals(target_date);

-- Stored procedures for common operations

DELIMITER //

-- Procedure to update budget spent amount when expense is added
CREATE PROCEDURE UpdateBudgetSpending(
    IN p_user_id BIGINT,
    IN p_category VARCHAR(50),
    IN p_amount DECIMAL(10,2),
    IN p_expense_date DATE
)
BEGIN
    UPDATE budgets 
    SET spent_amount = spent_amount + p_amount
    WHERE user_id = p_user_id 
      AND category = p_category 
      AND p_expense_date BETWEEN start_date AND end_date;
END //

-- Procedure to get user financial summary
CREATE PROCEDURE GetUserFinancialSummary(
    IN p_user_id BIGINT,
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    SELECT 
        'Total Expenses' as metric,
        COALESCE(SUM(amount), 0) as value
    FROM expenses 
    WHERE user_id = p_user_id 
      AND expense_date BETWEEN p_start_date AND p_end_date
    
    UNION ALL
    
    SELECT 
        'Active Budgets' as metric,
        COUNT(*) as value
    FROM budgets 
    WHERE user_id = p_user_id 
      AND CURDATE() BETWEEN start_date AND end_date
    
    UNION ALL
    
    SELECT 
        'Active Goals' as metric,
        COUNT(*) as value
    FROM goals 
    WHERE user_id = p_user_id 
      AND status = 'ACTIVE';
END //

DELIMITER ;

-- Triggers for data integrity and automation

DELIMITER //

-- Trigger to automatically update goal status when target is reached
CREATE TRIGGER update_goal_status 
AFTER UPDATE ON goals
FOR EACH ROW
BEGIN
    IF NEW.current_amount >= NEW.target_amount AND NEW.status = 'ACTIVE' THEN
        UPDATE goals 
        SET status = 'COMPLETED' 
        WHERE id = NEW.id;
    END IF;
END //

-- Trigger to update budget spending when expense is added
CREATE TRIGGER update_budget_on_expense_insert
AFTER INSERT ON expenses
FOR EACH ROW
BEGIN
    CALL UpdateBudgetSpending(NEW.user_id, NEW.category, NEW.amount, NEW.expense_date);
END //

-- Trigger to adjust budget spending when expense is updated
CREATE TRIGGER update_budget_on_expense_update
AFTER UPDATE ON expenses
FOR EACH ROW
BEGIN
    -- Subtract old amount
    CALL UpdateBudgetSpending(OLD.user_id, OLD.category, -OLD.amount, OLD.expense_date);
    -- Add new amount
    CALL UpdateBudgetSpending(NEW.user_id, NEW.category, NEW.amount, NEW.expense_date);
END //

-- Trigger to adjust budget spending when expense is deleted
CREATE TRIGGER update_budget_on_expense_delete
AFTER DELETE ON expenses
FOR EACH ROW
BEGIN
    CALL UpdateBudgetSpending(OLD.user_id, OLD.category, -OLD.amount, OLD.expense_date);
END //

DELIMITER ;