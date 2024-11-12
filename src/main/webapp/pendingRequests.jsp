<%@ page import="com.tap.model.Request" %>
<%@ page import="com.tap.dao.RequestDao" %>
<%@ page import="com.tap.daoImp.RequestDaoImp" %>
<%@ page import="com.tap.model.User.Role" %>
<%@ page import="java.util.List" %>

<%
    // Retrieve the user's role from the session as a String
    String roleStr = (String) session.getAttribute("role");
    Role role = null;

    // Attempt to convert the role String to the Role enum
    try {
        if (roleStr != null) {
            role = Role.valueOf(roleStr); // Convert the role string to Role enum
        }
        out.println("Role retrieved from session: " + roleStr); // Debugging log for role
    } catch (IllegalArgumentException e) {
        // Redirect to accessDenied.jsp if role conversion fails
        response.sendRedirect("accessDenied.jsp");
        return;
    }

    // Redirect if the role is not "Manager"
    if (role == null || role != Role.Manager) {
        response.sendRedirect("accessDenied.jsp");
        return;
    }

    // Retrieve the list of pending requests using the RequestDao
    RequestDao requestDao = new RequestDaoImp();
    List<Request> pendingRequests = requestDao.getPendingRequests();
    out.println("Number of pending requests fetched: " + (pendingRequests != null ? pendingRequests.size() : "null"));  // Debugging log
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Pending Access Requests</title>
    <style>
        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        
        /* Styling for Approve and Reject buttons */
        .approve-btn, .reject-btn {
            padding: 5px 10px;
            font-size: 14px;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        
        .approve-btn {
            background-color: #4CAF50; /* Green color for Approve */
        }
        
        .reject-btn {
            background-color: #f44336; /* Red color for Reject */
        }
        
        /* Button hover effect */
        .approve-btn:hover {
            background-color: #45a049;
        }
        
        .reject-btn:hover {
            background-color: #e53935;
        }
    </style>
</head>
<body>
    <h1>Pending Access Requests</h1>

    <!-- Check if there are pending requests to display -->
    <% if (pendingRequests != null && !pendingRequests.isEmpty()) { %>
        <!-- Table for displaying pending requests -->
        <table>
            <tr>
                <th>Employee Name</th>
                <th>Software Name</th>
                <th>Access Type</th>
                <th>Reason for Request</th>
                <th>Actions</th>
            </tr>
            <!-- Loop through each pending request and display details -->
            <%
                for (Request req : pendingRequests) {
            %>
                <tr>
                    <td><%= req.getUser_id().getUserName() %></td>
                    <td><%= req.getSoftware_id().getName() %></td>
                    <td><%= req.getAccessType() %></td>
                    <td><%= req.getReason() %></td>
                    <td>
                        <!-- Approve Button Form -->
                        <form action="ApprovalServlet" method="post" style="display:inline;">
                            <input type="hidden" name="requestId" value="<%= req.getId() %>">
                            <button type="submit" name="action" value="approve" class="approve-btn">Approve</button>
                        </form>
                        <!-- Reject Button Form -->
                        <form action="ApprovalServlet" method="post" style="display:inline;">
                            <input type="hidden" name="requestId" value="<%= req.getId() %>">
                            <button type="submit" name="action" value="reject" class="reject-btn">Reject</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    <% } else { %>
        <!-- Message if no pending requests are available -->
        <p>No pending access requests.</p>
        <!-- Link to request access if no pending requests -->
        <p><a href="requestAccess.jsp">Request Access</a></p>
    <% } %>
</body>
</html>
