package com.tap.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tap.dao.RequestDao;
import com.tap.daoImp.RequestDaoImp;
import com.tap.model.Request;
import com.tap.model.Software;
import com.tap.model.User;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get parameters from the form submission
            String softwareIdParam = request.getParameter("software");  // softwareId from the dropdown
            String accessType = request.getParameter("accessType");
            String reason = request.getParameter("reason");
            
            // Validate the form data
            if (softwareIdParam == null || accessType == null || reason == null || reason.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Please fill all fields.");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }

            // Parse the softwareId into an integer
            int softwareId = Integer.parseInt(softwareIdParam);

            // Fetch the logged-in employee's user ID (from session)
            User employee = (User) request.getSession().getAttribute("user");
            if (employee == null) {
                response.sendRedirect("login.jsp");  // Redirect to login page if not logged in
                return;
            }

            // Create the Software object using the softwareId
            Software software = new Software();
            software.setId(softwareId);

            // Create the Request object
            Request accessRequest = new Request();
            accessRequest.setUser_id(employee);  // Employee who made the request
            accessRequest.setSoftware_id(software);  // The software they want access to
            accessRequest.setAccessType(accessType);  // Access type selected
            accessRequest.setReason(reason);  // Reason for the request
            accessRequest.setStatus(Request.Status.Pending);  // Default status is "Pending"

            // Call the DAO to add the request to the database
            RequestDao requestDao = new RequestDaoImp();
            boolean result = requestDao.addRequest(accessRequest);

            // Handle the result
            if (result) {
                response.sendRedirect("success.jsp");  // Redirect to success page if successful
            } else {
                request.setAttribute("errorMessage", "Failed to submit the request.");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}
