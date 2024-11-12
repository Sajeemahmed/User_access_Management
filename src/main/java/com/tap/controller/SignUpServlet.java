package com.tap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tap.daoImp.UserDaoImp;
import com.tap.model.User;
import com.tap.model.User.Role;
import org.mindrot.jbcrypt.BCrypt;  // Import BCrypt for hashing

@WebServlet("/signupservlet")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user input from request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Convert role from string to Role enum
        Role userRole = Role.valueOf(role);

        // Hash the password before saving it to the database
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create a new User object
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(hashedPassword);  // Set the hashed password
        newUser.setRole(userRole);

        // Create a UserDaoImp instance to add the user to the database
        UserDaoImp userDao = new UserDaoImp();
        userDao.addUser(newUser);

        // Redirect to login page or success page after sign up
        resp.sendRedirect("login.jsp");  // Adjust based on your application's structure
    }
}
