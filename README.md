User Access Management System
Overview
This project is a web-based User Access Management System that allows users to register, request access to applications, and enables managers to approve or reject requests.

Technologies Used: Java Servlets, JSP, PostgreSQL

Features
User Registration & Login: Allows new users to sign up and existing users to log in.
Role-Based Access:
Employee: Can request access to applications.
Manager: Can approve or reject access requests.
Admin: Can create new applications and manage access.
Software & Access Management: Admins can add applications; employees can request access; managers handle approvals.
Setup Instructions
Clone the Repository:

Database Setup:
Create a PostgreSQL database.
Execute the provided SQL script to create tables: users, software, and requests.
Configure and Build:

Update database connection details in configuration files.
Build using Maven:
bash
Copy code
mvn clean install
Run on Tomcat Server:
Deploy on Apache Tomcat and access the application in a browser.

Usage Guide
Sign-Up/Login: Users can sign up as "Employee" by default or log in with assigned roles.
Employee Actions: Request application access.
Manager Actions: View and manage pending access requests.
Admin Actions: Create and manage applications, handle access requests.

Database Design (Simplified)
users: Stores user information and roles (Employee, Manager, Admin).
software: Stores application details and access levels.
requests: Logs user access requests and approval statuses.

Deliverables
Source Code: Java Servlets, JSP pages.
Database Script: PostgreSQL table creation script.

Documentation: This README for setup and usage.
Thankyou.........
