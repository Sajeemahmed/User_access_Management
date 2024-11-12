package com.tap.daoImp;

import com.tap.dao.RequestDao;
import com.tap.model.Request;
import com.tap.model.Software;
import com.tap.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImp implements RequestDao {

    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;

    // SQL Queries
    final static String INSERT_QUERY = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, ?)";
    final static String SELECT_QUERY = "SELECT * FROM requests WHERE id = ?";
    final static String SELECT_ALL_QUERY = "SELECT * FROM requests";
    final static String UPDATE_QUERY = "UPDATE requests SET user_id = ?, software_id = ?, access_type = ?, reason = ?, status = ? WHERE id = ?";
    final static String DELETE_QUERY = "DELETE FROM requests WHERE id = ?";
    final static String SELECT_BY_USER_QUERY = "SELECT * FROM requests WHERE user_id = ?";
    final static String SELECT_BY_SOFTWARE_QUERY = "SELECT * FROM requests WHERE software_id = ?";
    final static String SELECT_BY_STATUS_QUERY = "SELECT * FROM requests WHERE status = ?";  // Correct query to fetch requests based on status
    final static String UPDATE_REQUESTSTATUS_QUERY = "UPDATE requests SET status = ? WHERE id = ?";  


    public RequestDaoImp() {
        super();
        String url = "jdbc:postgresql://localhost:5432/user_management";  // Adjust for your PostgreSQL database URL
        String username = "postgres";  // Your PostgreSQL username
        String password = "Sajeem08";  // Your PostgreSQL password
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addRequest(Request request) {
        try {
            pstmt = con.prepareStatement(INSERT_QUERY);
            pstmt.setInt(1, request.getUser_id().getId());       // Ensure request.getUser() is not null and has a valid ID
            pstmt.setInt(2, request.getSoftware_id().getId());   // Ensure request.getSoftware() is not null and has a valid ID
            pstmt.setString(3, request.getAccessType());      // Access type
            pstmt.setString(4, request.getReason());          // Reason
            pstmt.setString(5, request.getStatus().name());   // Assuming status is an enum

            pstmt.executeUpdate();
            return true;  // Successfully added
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Request getRequest(int requestId) {
        Request request = null;

        try {
            pstmt = con.prepareStatement(SELECT_QUERY);
            pstmt.setInt(1, requestId);
            res = pstmt.executeQuery();

            if (res.next()) {
                request = extractRequestFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public Request getRequestById(int requestId) {
        // Implementing the missing method
        Request request = null;
        try {
            pstmt = con.prepareStatement(SELECT_QUERY); // Using the existing SELECT_QUERY
            pstmt.setInt(1, requestId);
            res = pstmt.executeQuery();

            if (res.next()) {
                request = extractRequestFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public List<Request> getAllRequests() {
        List<Request> requestList = new ArrayList<>();

        try {
            stmt = con.createStatement();
            res = stmt.executeQuery(SELECT_ALL_QUERY);

            while (res.next()) {
                requestList.add(extractRequestFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    @Override
    public boolean updateRequest(Request request) {
        try {
            pstmt = con.prepareStatement(UPDATE_QUERY);
            pstmt.setInt(1, request.getUser_id().getId());
            pstmt.setInt(2, request.getSoftware_id().getId());
            pstmt.setString(3, request.getAccessType());
            pstmt.setString(4, request.getReason());
            pstmt.setString(5, request.getStatus().name());
            pstmt.setInt(6, request.getId());

            pstmt.executeUpdate();
            return true; // Return true to indicate success
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteRequest(int requestId) {
        try {
            pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, requestId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Request> getRequestsByUser(int userId) {
        List<Request> requestList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(SELECT_BY_USER_QUERY);
            pstmt.setInt(1, userId);
            res = pstmt.executeQuery();

            while (res.next()) {
                Request request = extractRequestFromResultSet(res);  // Declare 'request' once
                requestList.add(request);  // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    @Override
    public List<Request> getRequestsBySoftware(int softwareId) {
        List<Request> requestList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(SELECT_BY_SOFTWARE_QUERY);
            pstmt.setInt(1, softwareId);
            res = pstmt.executeQuery();

            while (res.next()) {
                Request request = extractRequestFromResultSet(res);  // Declare 'request' once
                requestList.add(request);  // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    @Override
    public List<Request> getRequestsByStatus(Request.Status status) {
        List<Request> requestList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(SELECT_BY_STATUS_QUERY);
            pstmt.setString(1, status.name());
          
            res = pstmt.executeQuery();

            while (res.next()) {
                Request request = extractRequestFromResultSet(res);  // Declare 'request' once
                requestList.add(request);  // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    
    // Method to update the status of a specific request
    @Override
    public boolean updateRequestStatus(int requestId, Request.Status status) {
       
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_REQUESTSTATUS_QUERY)) {
            pstmt.setString(1, status.name());  // Convert status to string
            pstmt.setInt(2, requestId);  // Set the request ID
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;  // Return true if one or more rows were updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Return false if update fails
    }
    
    
    // Method to get all pending requests

    @Override
    public List<Request> getPendingRequests() {
        // Declare the list of pending requests
        List<Request> pendingRequests = new ArrayList<>();
        
        // SQL query to fetch requests with 'Pending' status
        String sql = "SELECT * FROM requests WHERE status = 'Pending'";  // Adjust table and column names if necessary

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_management", "postgres", "Sajeem08");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Iterate over the result set
            while (rs.next()) {
                // Retrieve related User and Software objects based on foreign keys
                int userId = rs.getInt("user_id");
                int softwareId = rs.getInt("software_id");
                String accessType = rs.getString("access_type");
                String reason = rs.getString("reason");
                String statusStr = rs.getString("status");
                Request.Status status = Request.Status.valueOf(rs.getString("status"));

                // Fetch User and Software objects (assuming these are already defined and exist in your system)
                User user = getUserById(userId);
                Software software = getSoftwareById(softwareId);

                // Create a Request object and add it to the list
                Request request = new Request(rs.getInt("id"), user, software, accessType, reason, status);
                pendingRequests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // For debugging
        }

        // Return the list of pending requests
        return pendingRequests;
    }
 // Helper method to extract Request object from result set
    public Request extractRequestFromResultSet(ResultSet res) throws SQLException {
        Request request = new Request();
        request.setId(res.getInt("id"));
        request.setAccessType(res.getString("access_type"));
        request.setReason(res.getString("reason"));
        request.setStatus(Request.Status.valueOf(res.getString("status")));

        // Fetch the user data
        int userId = res.getInt("user_id");
        User user = getUserById(userId);  // Method to fetch User by ID from the users table
        request.setUser_id(user);

        // Fetch the software data
        int softwareId = res.getInt("software_id");
        Software software = getSoftwareById(softwareId);  // Method to fetch Software by ID from the software table
        request.setSoftware_id(software);

        return request;
    }
 // In RequestDaoImp class

    
    // Fetch software by ID (dummy implementation, assumes method exists)
    @Override
    public Software getSoftwareById(int softwareId) {
        Software software = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM software WHERE id = ?");
            pstmt.setInt(1, softwareId);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                software = new Software();
                software.setId(res.getInt("id"));
                software.setName(res.getString("name"));
                // Set other fields if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return software;
    }
 // Fetch user by ID (dummy implementation, assumes method exists)
    @Override
    public User getUserById(int userId) {
        User user = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setInt(1, userId);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                user = new User();
                user.setId(res.getInt("id"));
                user.setUserName(res.getString("userName"));
                // Set other fields if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



 
}
