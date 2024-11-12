package com.tap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;  // Import BCrypt

import com.tap.dao.UserDao;
import com.tap.daoImp.UserDaoImp;
import com.tap.model.User;
import com.tap.model.User.Role;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Trim input to avoid spaces
        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();

        // Use UserDao to validate the user
        UserDao userDao = new UserDaoImp();
        User user = userDao.getUserByUsername(userName);

        
     
        // Debugging log to check if user is found
        if (user != null) {
            System.out.println("Fetched User: " + user.getUserName() + " with Role: " + user.getRole());
        } else {
            System.out.println("User not found with username: " + userName);
        }

        // If user exists, use BCrypt to verify the entered password
        if (user != null && verifyPassword(password, user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUserName());
            session.setAttribute("role", user.getRole().name());
            
            

            Role role = user.getRole();
            System.out.println("Redirecting user based on role: " + role);

            if ("Employee".equals(role.name())) {
                System.out.println("Redirecting to requestAccess.jsp");
                response.sendRedirect("requestAccess.jsp");
            } else if ("Manager".equals(role.name())) {
                System.out.println("Redirecting to pendingRequests.jsp");
                response.sendRedirect("pendingRequests.jsp");
            } else if ("Admin".equals(role.name())) {
                System.out.println("Redirecting to createSoftware.jsp");
                response.sendRedirect("createSoftware.jsp");
            } else {
                System.out.println("Redirecting to dashboard.jsp");
                response.sendRedirect("dashboard.jsp");
            }
        } else {
            System.out.println("Login failed for user: " + userName);  // Debugging log
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
     
    // Method to verify password using BCrypt
    private boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(enteredPassword, storedHashedPassword);  // Verify password with BCrypt
    }
}
