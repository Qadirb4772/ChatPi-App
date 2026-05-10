# ChatPi App

## Introduction
ChatPi App is a Java-based desktop chatting application developed using Object-Oriented Programming (OOP) concepts, Java Swing for GUI, JDBC for database connectivity, and MySQL for data storage.

The project was created as an academic semester project to demonstrate practical implementation of OOP principles, database management, exception handling, GUI development, and software design in Java.

---

# Purpose of the System

The purpose of ChatPi App is to provide a simple and secure communication platform where users can create accounts, log in, create chats, and exchange messages.

The system solves the following problems:

- Secure management of user accounts
- Organized communication between users
- Permanent storage of chats and messages
- Easy interaction through a graphical interface

---

# Target Users

This system is designed for:

- Students learning Java and OOP
- Beginners learning JDBC and MySQL
- Users who want a simple desktop chatting application

---

# Main Features

## User Authentication
- User Signup
- User Login
- Password Validation
- Forgot Password Functionality

## Chat Management
- Create Chats
- Join Chats
- Send Messages
- View Chat History

## Database Integration
- MySQL Database Connectivity
- Persistent Data Storage
- JDBC-Based SQL Operations

## GUI System
- Java Swing Interface
- Interactive Windows and Forms
- User-Friendly Navigation

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core Programming Language |
| Java Swing | GUI Development |
| JDBC | Database Connectivity |
| MySQL | Database Management |
| OOP Concepts | Software Design |

---

# Project Structure

```text
ChatPiApp/
│
├── src/
│   ├── Chat/
│   │   ├── App/
│   │   ├── DB/
│   │   ├── Models/
│   │   ├── Services/
│   │   └── UI/
│   │
│   └── JDBC/
│
├── database/
│   └── chatpi.sql
│
├── README.md
└── .gitignore
```

---

# Core Modules

## 1. App Package
This package contains the main application logic and the starting point of the program.

### Responsibilities
- Starting the application
- Managing program flow
- Connecting frontend and backend components

---

## 2. DB Package
This package handles all database-related functionality.

### Responsibilities
- Establishing JDBC connections
- Executing SQL queries
- Inserting and retrieving records
- Managing database operations

### Example Responsibilities
- Connecting with MySQL database
- Saving users and messages
- Retrieving chat history

---

## 3. Models Package
This package contains entity classes representing real-world objects used in the system.

### Example Classes
- User
- Chat
- Message

### Responsibilities
- Defining object attributes
- Constructors
- Getters and setters
- Data representation

---

## 4. Services Package
This package contains the business logic of the application.

### Responsibilities
- User authentication
- Chat management
- Message sending
- Validation logic
- Processing user requests

---

## 5. UI Package
This package contains graphical user interface screens created using Java Swing.

### Responsibilities
- Login screen
- Signup form
- Chat interface
- Buttons and event handling

---

# Key OOP Features Used

## 1. Encapsulation
Data members are declared private and accessed through public getter and setter methods to protect data integrity.

### Example
```java
private String username;

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}
```

### Benefit
- Prevents direct access to sensitive data
- Provides controlled modification of object attributes

---

## 2. Inheritance
Inheritance is used to reuse common functionality between related classes.

### Example
A parent class can provide common methods and child classes can extend that functionality.

### Benefit
- Code reusability
- Reduced duplication
- Better code organization

---

## 3. Polymorphism

### Method Overloading
Methods with the same name but different parameter lists are used.

### Method Overriding
Child classes override methods of parent classes to provide specialized behavior.

### Benefit
- Flexibility
- Improved readability
- Better software scalability

---

## 4. Abstraction
Interfaces and abstract classes are used to hide implementation details and expose only required functionality.

### Benefit
- Simplifies complex systems
- Improves maintainability
- Enhances modularity

---

## 5. Exception Handling
Try-catch blocks are used to handle runtime errors and prevent program crashes.

### Example
```java
try {
    Connection con = DriverManager.getConnection(url, user, password);
}
catch(SQLException e) {
    System.out.println(e.getMessage());
}
```

### Benefit
- Prevents abnormal termination
- Improves reliability
- Handles database errors safely

---

## 6. Validation
Validation mechanisms are implemented for:

- Empty fields
- Incorrect login credentials
- Duplicate usernames
- Invalid input handling

### Benefit
- Improves system security
- Prevents invalid data entry
- Enhances user experience

---

# Database Design

## Database Used
MySQL

---

# Main Tables

## Users Table
Stores user account information.

```sql
Users(
    user_id,
    username,
    email,
    password
)
```

---

## Chats Table
Stores chat information.

```sql
Chats(
    chat_id,
    chat_name,
    created_at
)
```

---

## Messages Table
Stores messages sent in chats.

```sql
Messages(
    message_id,
    sender_id,
    chat_id,
    message_text,
    sent_at
)
```

---

## User_Chat Table
Maintains many-to-many relationship between users and chats.

```sql
User_Chat(
    user_id,
    chat_id
)
```

---

# How to Run the Project

## Software Requirements

Before running the project, install the following:

- JDK 17 or above
- MySQL Server
- Java IDE (IntelliJ IDEA, Eclipse, or NetBeans)

---

# Step 1: Clone the Repository

```bash
git clone https://github.com/your-username/ChatPi-App.git
```

---

# Step 2: Open Project in IDE

Open the project folder in your preferred Java IDE.

Recommended IDEs:
- IntelliJ IDEA
- Eclipse
- Visual Studio Code (highly recommended)

---

# Step 3: Create Database

Open MySQL and create a database:

```sql
CREATE DATABASE chatpi;
```

---

# Step 4: Import SQL File

Import the provided SQL file into MySQL.

Example:

```sql
SOURCE chatpi.sql;
```

---

# Step 5: Configure JDBC Connection

Open the JDBC connection class and update the database credentials:

```java
String url = "jdbc:mysql://localhost:3306/chatpi";
String user = "root";
String password = "your_password";
```

---

# Step 6: Compile the Project

```bash
javac Main.java
```

---

# Step 7: Run the Application

```bash
java Main
```

Or simply run the main file directly from your IDE.

---

# Example Workflow

1. User creates an account
2. User logs into the system
3. User creates or joins a chat
4. User sends messages
5. Messages are stored in the database

---

# Security Features

- Password validation
- Input validation
- Exception handling
- Controlled database access

---

# Future Improvements

The following features can be added in future versions:

- Real-time messaging using sockets
- File sharing support
- Voice and video calling
- Online/offline user status
- Notifications system
- Better GUI design

---

# Limitations

- Desktop-based application only
- Requires local MySQL setup
- Real-time communication is limited
- Internet-based deployment not implemented

---

# Learning Outcomes

This project helped in understanding:

- Object-Oriented Programming
- JDBC Connectivity
- MySQL Database Integration
- Java Swing GUI Development
- Exception Handling
- Validation Techniques
- Software Design Principles

---

# Author

**Qadir Bakhsh**
Collaborated with **ZafranUllah Khan**

---

# License

This project is developed for educational purposes only.
