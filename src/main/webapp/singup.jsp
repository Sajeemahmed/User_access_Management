<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #0277bd; /* Light Blue background */
            color: #0277bd; /* Dark blue text */
        }

        .container {
            width: 300px;
            margin: 50px auto;
            padding: 30px;
            background-color: #ffffff; /* White background for the form */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            border: 1px solid #0288d1; 
            box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;/* Blue border for the container */
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #0277bd; /* Blue text for the heading */
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #0288d1; /* Light blue for label text */
        }

        input[type="text"], input[type="password"], select {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px 0;
            border: 1px solid #0288d1; /* Blue border */
            border-radius: 4px;
             /* Light blue background for input fields */
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #006BFF; /* Red background */
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0277bd; /* Darker blue on hover */
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }

        .success {
            color: green;
            text-align: center;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Sign Up</h2>

        <form action="signupservlet" method="POST">
            <!-- Username Field -->
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>

            <!-- Password Field -->
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>

            <!-- Role Selection -->
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="">Select Role</option>
                <option value="Employee">Employee</option>
                <option value="Manager">Manager</option>
                <option value="Admin">Admin</option>
            </select>

            <!-- Submit Button -->
            <button type="submit">Sign Up</button>
        </form>

        <!-- Display error message if present -->
        

        <!-- Display success message if registration is successful -->
        

    </div>

</body>
</html>
