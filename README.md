# Online Banking System

## Project Description
A Java-based desktop application for managing a simulated online banking system. The application features a user-friendly graphical interface (GUI) and connects to a MySQL database to handle bank accounts, deposits, withdrawals, and transaction history reliably and securely.

## Features
- **Account Management**: Create and manage bank accounts with basic information.
- **Transactions**: Perform deposits and withdrawals smoothly.
- **Transaction History**: View real-time history of all transactions made on an account.
- **Modern GUI**: A sleek and responsive graphical user interface utilizing the FlatLaf Look and Feel.
- **Automated Verification**: Built-in test runner to easily verify the core functionality of the system.

## Technologies Used
- **Language**: Java
- **GUI Framework**: Java Swing
- **Look and Feel**: FlatLaf (FormDev)
- **Database**: MySQL
- **Database Connectivity**: JDBC (MySQL Connector/J)

## OOP Concepts Implemented
- **Encapsulation**: Used in entity classes like `BankAccount` and `Transaction` to protect properties and expose safe getter/setter methods.
- **Abstraction**: Defined interfaces like `BankOperations` to hide complex implementation details behind a unified contract.
- **Inheritance & Polymorphism**: Applied within the structure of Swing GUI components and event listeners.

## Project Architecture
The project follows a standard multi-layer architecture:
- **Presentation Layer**: Handled by `BankGUI`, responsible for user interactions and displaying information.
- **Service Layer**: Managed by `BankService`, acting as a bridge between the GUI and the database, enforcing business rules.
- **Data Access Layer**: Facilitated by `DatabaseConnection`, connecting the application safely to the MySQL instance.
- **Entity/Model Layer**: POJOs (`BankAccount`, `Transaction`) representing domain models.

## Folder Structure
```text
java_project/
│
├── .vscode/               # VS Code specific settings
├── lib/                   # External libraries (JDBC driver, FlatLaf)
│   ├── mysql-connector-java.jar
│   └── flatlaf-3.4.1.jar
├── src/                   # Application source code
│   └── bank/
│       ├── BankAccount.java
│       ├── Transaction.java
│       ├── BankOperations.java
│       ├── BankService.java
│       ├── DatabaseConnection.java
│       ├── BankGUI.java
│       ├── Main.java
│       └── TestRunner.java
├── db_setup.sql           # SQL script to initialize the database
├── README.md              # Project documentation
└── .gitignore             # Git ignore file
```

## Database Setup Instructions
1. Install MySQL Server and ensure it is running.
2. Open your MySQL client (e.g., MySQL Workbench or CLI).
3. Execute the `db_setup.sql` script provided in the root directory to create the database schema and required tables:
   ```sql
   source /path/to/db_setup.sql;
   ```
4. **Configure Credentials**: 
   Open `src/bank/DatabaseConnection.java` and update the database credentials with your own MySQL username and password before compiling and running the project:
   ```java
   private static final String USER = "your_mysql_username";
   private static final String PASSWORD = "YOUR_PASSWORD";
   ```

## How to Compile
From the root directory of the project, run:
```bash
javac -cp ".;lib/*" -d out src/bank/*.java
```
*Note: For Linux/macOS, use `:` instead of `;` in the classpath.*

## How to Run
To run the main application:
```bash
java -cp "out;lib/*" bank.Main
```
To run the automated verification tests:
```bash
java -cp "out;lib/*" bank.TestRunner
```
*Note: For Linux/macOS, use `:` instead of `;` in the classpath.*

## Screenshots
*(Leave placeholders for future screenshots)*
- **Main Menu**: `[Insert Screenshot Here]`
- **Transaction History**: `[Insert Screenshot Here]`

## Future Improvements
- Implement user authentication (login/registration).
- Add support for multiple account types (Savings, Checking).
- Encrypt sensitive data in the database.
- Export transaction history to PDF or CSV.
- Expand test coverage with a testing framework like JUnit.

## Author
[Your Name/Username]
