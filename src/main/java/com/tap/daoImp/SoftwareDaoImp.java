package com.tap.daoImp;

import com.tap.dao.SoftwareDao;
import com.tap.model.Software;
import com.tap.model.Software.AccessLevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDaoImp implements SoftwareDao {

    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;

    // SQL Queries
    final static String INSERT_QUERY = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
    final static String SELECT_QUERY = "SELECT * FROM software WHERE id = ?";
    final static String SELECT_ALL_QUERY = "SELECT * FROM software";
    final static String UPDATE_QUERY = "UPDATE software SET name = ?, description = ?, access_levels = ? WHERE id = ?";
    final static String DELETE_QUERY = "DELETE FROM software WHERE id = ?";
    final static String SELECT_BY_ACCESS_LEVEL_QUERY = "SELECT * FROM software WHERE access_levels = ?";
    final static String SELECT_BY_NAME_QUERY = "SELECT * FROM software WHERE name = ?";  // Updated query to fetch by name

    // Constructor to initialize the database connection
    public SoftwareDaoImp() {
        String url = "jdbc:postgresql://localhost:5432/user_management"; // Adjust accordingly
        String username = "postgres";
        String password = "Sajeem08";
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Software method
    @Override
    public boolean addSoftware(Software software) {
        pstmt = null;
        try {
            pstmt = con.prepareStatement(INSERT_QUERY);
            pstmt.setString(1, software.getName());
            pstmt.setString(2, software.getDescription());
            pstmt.setString(3, software.getAccessLevel().name());  // Use name() to get the enum as a string

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Software by ID
    @Override
    public Software getSoftware(int softwareId) {
        pstmt = null;
        res = null;
        Software software = null;

        try {
            pstmt = con.prepareStatement(SELECT_QUERY);
            pstmt.setInt(1, softwareId);
            res = pstmt.executeQuery();

            if (res.next()) {
                software = extractSoftwareFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return software;
    }

    // Get all Software
    @Override
    public List<Software> getAllSoftware() {
        pstmt = null;
        res = null;
        List<Software> softwareList = new ArrayList<>();

        try {
            stmt = con.createStatement();
            res = stmt.executeQuery(SELECT_ALL_QUERY);

            while (res.next()) {
                softwareList.add(extractSoftwareFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return softwareList;
    }

    // Update Software
    @Override
    public void updateSoftware(Software software) {
        pstmt = null;
        try {
            pstmt = con.prepareStatement(UPDATE_QUERY);
            pstmt.setString(1, software.getName());
            pstmt.setString(2, software.getDescription());
            pstmt.setString(3, software.getAccessLevel().name());  // Using name() to set the access level as string
            pstmt.setInt(4, software.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Software
    @Override
    public void deleteSoftware(int softwareId) {
        pstmt = null;
        try {
            pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, softwareId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get Software by Access Level
    public List<Software> getSoftwareByAccessLevel(String accessLevel) {
        pstmt = null;
        res = null;
        List<Software> softwareList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(SELECT_BY_ACCESS_LEVEL_QUERY);
            pstmt.setString(1, accessLevel);  // Ensure this value matches the enum's name
            res = pstmt.executeQuery();

            while (res.next()) {
                softwareList.add(extractSoftwareFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return softwareList;
    }

    // Get Software by Name (Updated)
    @Override
    public Software getSoftwareByName(String name) {
        pstmt = null;
        res = null;
        Software software = null;

        try {
            pstmt = con.prepareStatement(SELECT_BY_NAME_QUERY);
            pstmt.setString(1, name);  // Use the software name to search for the software
            res = pstmt.executeQuery();

            if (res.next()) {
                software = extractSoftwareFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return software;
    }

    // Helper method to extract Software from ResultSet
    private Software extractSoftwareFromResultSet(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String description = res.getString("description");
        String accessLevelString = res.getString("access_levels");

        // Convert the string to the corresponding AccessLevel enum value (case-insensitive)
        AccessLevel accessLevel = null;
        try {
            accessLevel = AccessLevel.valueOf(accessLevelString.trim());  // No need for .toUpperCase() if values in DB are properly capitalized
        } catch (IllegalArgumentException e) {
            // Handle case when the access level from DB is invalid
            accessLevel = AccessLevel.Read; // Default to 'Read' if the value is invalid
        }

        return new Software(id, name, description, accessLevel);
    }
}
