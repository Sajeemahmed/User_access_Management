package com.tap.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tap.dao.SoftwareDao;
import com.tap.daoImp.SoftwareDaoImp;
import com.tap.model.Software;
import com.tap.model.User;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is Admin
        Object roleObj = request.getSession().getAttribute("role");
        User.Role role = null;

        if (roleObj instanceof String) {
            try {
                role = User.Role.valueOf((String) roleObj);
            } catch (IllegalArgumentException e) {
                // Handle invalid role string in session
                response.sendRedirect("error.jsp");
                return;
            }
        } else if (roleObj instanceof User.Role) {
            role = (User.Role) roleObj;
        }

        if (role == null || role != User.Role.Admin) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Fetch form data from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String accessLevelParam = request.getParameter("accessLevel");

        // Create Software object and set properties
        Software software = new Software();
        software.setName(name);
        software.setDescription(description);

        // Convert the accessLevelParam string to AccessLevel enum
        if (accessLevelParam != null && !accessLevelParam.isEmpty()) {
            try {
                Software.AccessLevel accessLevel = Software.AccessLevel.valueOf(accessLevelParam);
                software.setAccessLevel(accessLevel);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid access level selected.");
                request.getRequestDispatcher("/createSoftware.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "Access level must be selected.");
            request.getRequestDispatcher("/createSoftware.jsp").forward(request, response);
            return;
        }

        // Call DAO to insert software into DB
        SoftwareDao softwareDao = new SoftwareDaoImp();
        boolean success = softwareDao.addSoftware(software);

        // Redirect based on success or failure
        if (success) {
            response.sendRedirect("success.jsp");
        } else {
            request.setAttribute("error", "Failed to create software. Please try again.");
            request.getRequestDispatcher("/createSoftware.jsp").forward(request, response);
        }
    }
}
