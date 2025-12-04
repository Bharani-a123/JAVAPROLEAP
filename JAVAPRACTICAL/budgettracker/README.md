# Budget Tracker & Expense Management System

A comprehensive personal finance management platform built with Spring Boot that helps users track expenses, manage budgets, set financial goals, and analyze spending patterns.

## Features

### Core Functionality
- **User Authentication & Management**: Secure login/registration with Spring Security
- **Expense Tracking**: Record daily expenses with categories and payment modes
- **Budget Management**: Set monthly/custom period budgets with spending alerts
- **Goal Tracking**: Define savings goals and monitor progress
- **Analytics & Reports**: Visual charts and spending trend analysis

### System Modules
1. **User Management Module**: Authentication, profile management, user preferences
2. **Expense Management Module**: CRUD operations for expenses with categorization
3. **Budget Module**: Budget creation, tracking, and alert system
4. **Analytics Module**: Dashboard, charts, and financial insights
5. **Goal Tracking Module**: Savings goals with progress monitoring

## Technology Stack

- **Backend**: Spring Boot 3.5.7, Spring Security, Spring Data JPA
- **Database**: MySQL with Hibernate ORM
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js, jQuery
- **Build Tool**: Maven
- **Java Version**: 21

## Database Schema

### Core Entities
- **Users**: User authentication and profile information
- **Expenses**: Daily expense records with categories and payment modes
- **Budgets**: Budget limits and spending tracking by category
- **Goals**: Financial goals with progress tracking

### Entity Relationships
- User → Expenses (One-to-Many)
- User → Budgets (One-to-Many)  
- User → Goals (One-to-Many)

## API Endpoints

### Authentication APIs
- `POST /api/auth/register` - User registration
- `GET /api/auth/user/{username}` - Get user details

### Expense Management APIs
- `GET /api/expenses` - Get user expenses
- `POST /api/expenses` - Add new expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense
- `GET /api/expenses/date-range` - Get expenses by date range
- `GET /api/expenses/category/{category}` - Get expenses by category
- `GET /api/expenses/total` - Get total expenses

### Budget Management APIs
- `GET /api/budgets` - Get user budgets
- `POST /api/budgets` - Create new budget
- `PUT /api/budgets/{id}` - Update budget
- `DELETE /api/budgets/{id}` - Delete budget
- `GET /api/budgets/active` - Get active budgets
- `GET /api/budgets/alerts` - Get budget alerts

### Goal Tracking APIs
- `GET /api/goals` - Get user goals
- `POST /api/goals` - Create new goal
- `PUT /api/goals/{id}` - Update goal
- `DELETE /api/goals/{id}` - Delete goal
- `POST /api/goals/{id}/add` - Add money to goal
- `GET /api/goals/active` - Get active goals
- `GET /api/goals/completed` - Get completed goals

### Analytics APIs
- `GET /api/analytics/dashboard` - Get dashboard data
- `GET /api/analytics/monthly-trend` - Get monthly expense trend
- `GET /api/analytics/category-expenses` - Get category-wise expenses

## Setup Instructions

### Prerequisites
- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create MySQL database: `budget_tracker`
2. Update database credentials in `application.properties`

### Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run: `mvn spring-boot:run`
4. Access application at: `http://localhost:8080`

### Default Login
- Username: `admin`
- Password: `admin123`

## Key Features Implementation

### Security
- Spring Security with form-based authentication
- Password encryption using BCrypt
- Session management and CSRF protection
- Role-based access control

### Data Validation
- Input validation for all forms
- Server-side validation with proper error handling
- Client-side validation for better UX

### Analytics & Reporting
- Interactive charts using Chart.js
- Monthly expense trends
- Category-wise spending analysis
- Budget vs actual spending comparison
- Goal progress tracking

### Responsive Design
- Bootstrap 5 for responsive UI
- Mobile-friendly interface
- Modern and intuitive design

## Project Structure

```
src/main/java/com/budgettracker/budgettracker/
├── entity/          # JPA entities
├── repository/      # Data access layer
├── service/         # Business logic layer
├── controller/      # REST controllers and web controllers
├── config/          # Configuration classes
└── BudgettrackerApplication.java

src/main/resources/
├── templates/       # Thymeleaf templates
├── static/          # Static resources (CSS, JS, images)
└── application.properties
```

## Future Enhancements

- Email notifications for budget alerts
- Export functionality (PDF, Excel)
- Mobile application
- Advanced analytics and forecasting
- Multi-currency support
- Recurring expense/income tracking
- Bank account integration