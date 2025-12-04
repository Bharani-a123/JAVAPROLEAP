# Budget Tracker API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
The API uses session-based authentication. Users must login through the web interface before accessing protected endpoints.

## Response Format
All API responses follow a consistent JSON format:

**Success Response:**
```json
{
  "data": {...},
  "status": "success"
}
```

**Error Response:**
```json
{
  "error": "Error message",
  "status": "error"
}
```

## API Endpoints

### Authentication APIs

#### Register User
- **URL:** `/auth/register`
- **Method:** `POST`
- **Description:** Register a new user account
- **Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890"
}
```
- **Response:**
```json
{
  "message": "User registered successfully",
  "userId": 1
}
```

#### Get User by Username
- **URL:** `/auth/user/{username}`
- **Method:** `GET`
- **Description:** Get user details by username
- **Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890"
}
```

### Expense Management APIs

#### Get All Expenses
- **URL:** `/expenses`
- **Method:** `GET`
- **Description:** Get all expenses for the authenticated user
- **Response:**
```json
[
  {
    "id": 1,
    "description": "Grocery Shopping",
    "amount": 85.50,
    "category": "FOOD",
    "paymentMode": "CREDIT_CARD",
    "expenseDate": "2024-01-15",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

#### Add New Expense
- **URL:** `/expenses`
- **Method:** `POST`
- **Description:** Add a new expense
- **Request Body:**
```json
{
  "description": "Grocery Shopping",
  "amount": 85.50,
  "category": "FOOD",
  "paymentMode": "CREDIT_CARD",
  "expenseDate": "2024-01-15"
}
```

#### Update Expense
- **URL:** `/expenses/{id}`
- **Method:** `PUT`
- **Description:** Update an existing expense
- **Request Body:** Same as Add New Expense

#### Delete Expense
- **URL:** `/expenses/{id}`
- **Method:** `DELETE`
- **Description:** Delete an expense
- **Response:**
```json
{
  "message": "Expense deleted successfully"
}
```

#### Get Expenses by Date Range
- **URL:** `/expenses/date-range`
- **Method:** `GET`
- **Parameters:**
  - `startDate`: Start date (YYYY-MM-DD)
  - `endDate`: End date (YYYY-MM-DD)
- **Example:** `/expenses/date-range?startDate=2024-01-01&endDate=2024-01-31`

#### Get Expenses by Category
- **URL:** `/expenses/category/{category}`
- **Method:** `GET`
- **Description:** Get expenses filtered by category
- **Categories:** FOOD, TRANSPORTATION, ENTERTAINMENT, UTILITIES, HEALTHCARE, SHOPPING, EDUCATION, TRAVEL, RENT, OTHER

#### Get Total Expenses
- **URL:** `/expenses/total`
- **Method:** `GET`
- **Parameters:**
  - `startDate`: Start date (YYYY-MM-DD)
  - `endDate`: End date (YYYY-MM-DD)
- **Response:**
```json
{
  "total": 1250.75
}
```

### Budget Management APIs

#### Get All Budgets
- **URL:** `/budgets`
- **Method:** `GET`
- **Description:** Get all budgets for the authenticated user
- **Response:**
```json
[
  {
    "id": 1,
    "category": "FOOD",
    "budgetLimit": 500.00,
    "spentAmount": 85.50,
    "startDate": "2024-01-01",
    "endDate": "2024-01-31",
    "spentPercentage": 17.1,
    "remainingAmount": 414.50
  }
]
```

#### Create New Budget
- **URL:** `/budgets`
- **Method:** `POST`
- **Description:** Create a new budget
- **Request Body:**
```json
{
  "category": "FOOD",
  "budgetLimit": 500.00,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31"
}
```

#### Update Budget
- **URL:** `/budgets/{id}`
- **Method:** `PUT`
- **Description:** Update an existing budget
- **Request Body:** Same as Create New Budget

#### Delete Budget
- **URL:** `/budgets/{id}`
- **Method:** `DELETE`
- **Description:** Delete a budget

#### Get Active Budgets
- **URL:** `/budgets/active`
- **Method:** `GET`
- **Description:** Get budgets that are currently active (current date falls within budget period)

#### Get Budget Alerts
- **URL:** `/budgets/alerts`
- **Method:** `GET`
- **Description:** Get budgets that are near or over the limit (>80% spent)

### Goal Tracking APIs

#### Get All Goals
- **URL:** `/goals`
- **Method:** `GET`
- **Description:** Get all goals for the authenticated user
- **Response:**
```json
[
  {
    "id": 1,
    "name": "Emergency Fund",
    "description": "Build emergency fund for 6 months expenses",
    "targetAmount": 5000.00,
    "currentAmount": 1200.00,
    "targetDate": "2024-12-31",
    "status": "ACTIVE",
    "progressPercentage": 24.0,
    "remainingAmount": 3800.00
  }
]
```

#### Create New Goal
- **URL:** `/goals`
- **Method:** `POST`
- **Description:** Create a new financial goal
- **Request Body:**
```json
{
  "name": "Emergency Fund",
  "description": "Build emergency fund for 6 months expenses",
  "targetAmount": 5000.00,
  "targetDate": "2024-12-31"
}
```

#### Update Goal
- **URL:** `/goals/{id}`
- **Method:** `PUT`
- **Description:** Update an existing goal
- **Request Body:** Same as Create New Goal

#### Delete Goal
- **URL:** `/goals/{id}`
- **Method:** `DELETE`
- **Description:** Delete a goal

#### Add Money to Goal
- **URL:** `/goals/{id}/add`
- **Method:** `POST`
- **Description:** Add money to a specific goal
- **Request Body:**
```json
{
  "amount": 100.00
}
```

#### Get Active Goals
- **URL:** `/goals/active`
- **Method:** `GET`
- **Description:** Get goals with status 'ACTIVE'

#### Get Completed Goals
- **URL:** `/goals/completed`
- **Method:** `GET`
- **Description:** Get goals with status 'COMPLETED'

### Analytics APIs

#### Get Dashboard Data
- **URL:** `/analytics/dashboard`
- **Method:** `GET`
- **Description:** Get comprehensive dashboard data including expenses, budgets, goals, and recent activity
- **Response:**
```json
{
  "monthlyExpenses": 1250.75,
  "categoryExpenses": {
    "FOOD": 450.00,
    "TRANSPORTATION": 200.00,
    "ENTERTAINMENT": 150.00
  },
  "activeBudgets": [...],
  "budgetAlerts": [...],
  "activeGoals": [...],
  "recentExpenses": [...]
}
```

#### Get Monthly Expense Trend
- **URL:** `/analytics/monthly-trend`
- **Method:** `GET`
- **Parameters:**
  - `months`: Number of months to include (default: 6)
- **Response:**
```json
{
  "2024-01": 1250.75,
  "2023-12": 1100.50,
  "2023-11": 980.25
}
```

#### Get Category-wise Expenses
- **URL:** `/analytics/category-expenses`
- **Method:** `GET`
- **Parameters:**
  - `startDate`: Start date (YYYY-MM-DD)
  - `endDate`: End date (YYYY-MM-DD)
- **Response:**
```json
{
  "FOOD": 450.00,
  "TRANSPORTATION": 200.00,
  "ENTERTAINMENT": 150.00,
  "UTILITIES": 300.00
}
```

## Data Models

### User
```json
{
  "id": "Long",
  "username": "String (unique)",
  "email": "String (unique)",
  "password": "String (encrypted)",
  "firstName": "String",
  "lastName": "String",
  "phoneNumber": "String",
  "createdAt": "LocalDateTime",
  "updatedAt": "LocalDateTime",
  "enabled": "Boolean"
}
```

### Expense
```json
{
  "id": "Long",
  "description": "String",
  "amount": "BigDecimal",
  "category": "Enum (FOOD, TRANSPORTATION, ENTERTAINMENT, UTILITIES, HEALTHCARE, SHOPPING, EDUCATION, TRAVEL, RENT, OTHER)",
  "paymentMode": "Enum (CASH, CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, OTHER)",
  "expenseDate": "LocalDate",
  "createdAt": "LocalDateTime"
}
```

### Budget
```json
{
  "id": "Long",
  "category": "Enum (same as Expense categories)",
  "budgetLimit": "BigDecimal",
  "spentAmount": "BigDecimal",
  "startDate": "LocalDate",
  "endDate": "LocalDate",
  "createdAt": "LocalDateTime"
}
```

### Goal
```json
{
  "id": "Long",
  "name": "String",
  "description": "String",
  "targetAmount": "BigDecimal",
  "currentAmount": "BigDecimal",
  "targetDate": "LocalDate",
  "status": "Enum (ACTIVE, COMPLETED, PAUSED)",
  "createdAt": "LocalDateTime"
}
```

## Error Codes

| HTTP Status | Description |
|-------------|-------------|
| 200 | Success |
| 400 | Bad Request - Invalid input data |
| 401 | Unauthorized - Authentication required |
| 403 | Forbidden - Access denied |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error |

## Rate Limiting
Currently, no rate limiting is implemented. In production, consider implementing rate limiting to prevent abuse.

## Security Considerations
- All endpoints (except authentication) require user authentication
- CSRF protection is disabled for API endpoints
- Passwords are encrypted using BCrypt
- Users can only access their own data (enforced by user context)