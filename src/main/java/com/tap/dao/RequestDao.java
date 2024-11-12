package com.tap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.tap.model.Request;
import com.tap.model.Software;
import com.tap.model.User;

public interface RequestDao {

    // Add a new request to the database
    boolean addRequest(Request request);

    // Retrieve a request by its ID
    Request getRequest(int requestId);

    // Retrieve all requests from the database
    List<Request> getAllRequests();

    // Update an existing request in the database
    boolean updateRequest(Request request);

    // Delete a request by its ID
    void deleteRequest(int requestId);

    // Get requests by a specific user
    List<Request> getRequestsByUser(int userId);

    // Get requests for a specific software
    List<Request> getRequestsBySoftware(int softwareId);

    // Get requests by status (Pending, Approved, Rejected)
    List<Request> getRequestsByStatus(Request.Status status);

    // Get request by its ID
    Request getRequestById(int requestId);

    // Update the status of a request
    public boolean updateRequestStatus(int requestId, Request.Status status);

    // Get all pending requests
    List<Request> getPendingRequests();

    // Extract a Request from ResultSet
    Request extractRequestFromResultSet(ResultSet res) throws SQLException;
    
    
    Software getSoftwareById(int softwareId);

    // Other methods as needed...
    User getUserById(int userId);
    
    
}
