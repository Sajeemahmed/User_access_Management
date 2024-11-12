package com.tap.controller;

import com.tap.dao.RequestDao;
import com.tap.daoImp.RequestDaoImp;
import com.tap.model.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RequestDao requestDao = new RequestDaoImp();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the requestId and action from the form
        String requestIdStr = request.getParameter("requestId");
        String action = request.getParameter("action");

        // Validate the requestId parameter
        if (requestIdStr == null || requestIdStr.isEmpty()) {
            response.sendRedirect("errorPage.jsp?error=Missing Request ID");
            return;
        }

        int requestId;
        try {
            requestId = Integer.parseInt(requestIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("errorPage.jsp?error=Invalid Request ID");
            return;
        }

        // Fetch the Request object from the database based on the requestId
        Request requestObj = requestDao.getRequestById(requestId);

        if (requestObj == null) {
            response.sendRedirect("errorPage.jsp?error=Request Not Found");
            return;
        }

        // Handle the approve/reject action
        boolean updateSuccess = false;
        if ("approve".equals(action)) {
            updateSuccess = requestDao.updateRequestStatus(requestId, Request.Status.Approved);
        } else if ("reject".equals(action)) {
            updateSuccess = requestDao.updateRequestStatus(requestId, Request.Status.Rejected);
        } else {
            // If the action is neither 'approve' nor 'reject', redirect to an error page
            response.sendRedirect("errorPage.jsp?error=Invalid Action");
            return;
        }

        // Redirect to a success or failure page based on the update result
        if (updateSuccess) {
            response.sendRedirect("pendingRequests.jsp?message=Action Successful");
        } else {
            response.sendRedirect("errorPage.jsp?error=Unable to update request status");
        }
    }
}
