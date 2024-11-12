package com.tap.daoImp;

import com.tap.dao.UserDao;
import com.tap.model.User;
import org.mindrot.jbcrypt.BCrypt;  // Import BCrypt class

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements UserDao {

    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;

    // SQL Queries
 // SQL Queries as Constants
 // SQL Queries
    final static String INSERT_QUERY = "INSERT INTO \"users\" (\"username\", \"password\", \"role\") VALUES (?, ?, ?)";
    final static String SELECT_QUERY = "SELECT * FROM \"users\" WHERE \"id\" = ? LIMIT 1";
    final static String SELECT_ALL_QUERY = "SELECT * FROM \"users\"";
    final static String UPDATE_QUERY = "UPDATE \"users\" SET \"username\" = ?, \"password\" = ?, \"role\" = ? WHERE \"id\" = ?";
    final static String DELETE_QUERY = "DELETE FROM \"users\" WHERE \"id\" = ?";
    final static String SELECT_BY_ROLE_QUERY = "SELECT * FROM \"users\" WHERE \"role\" = ?";
    final static String SELECT_BY_USERNAME_QUERY = "SELECT * FROM \"users\" WHERE \"username\" = ? LIMIT 1";

       

    // Constructor: Initializes the database connection
    public UserDaoImp() {
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
    // Add a new user to the database
    @Override
    public void addUser(User user) {
        pstmt = null;
        try {
            // Updated query does not include 'id', PostgreSQL will auto-generate it
            pstmt = con.prepareStatement(INSERT_QUERY);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());  // Assuming password is hashed
            pstmt.setString(3, user.getRole().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // Retrieve a user by their ID
    @Override
    public User getUser(int id) {
        pstmt = null;
        res = null;
        User user = null;

        try {
            pstmt = con.prepareStatement(SELECT_QUERY);
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();

            if (res.next()) {
                user = extractUserFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    
    // Retrieve a user by their username
    @Override
    public User getUserByUsername(String userName) {
        pstmt = null;
        res = null;
        User user = null;

        try {
            // Use TRIM to avoid issues with spaces in the username
            String trimmedUserName = userName.trim();  // Just in case there are spaces from user input

            // Updated query with TRIM function to remove any possible spaces in the database
            pstmt = con.prepareStatement("SELECT * FROM \"users\" WHERE TRIM(\"username\") = ? LIMIT 1");
            pstmt.setString(1, trimmedUserName);  // Set the trimmed username
            res = pstmt.executeQuery();

            if (res.next()) {
                user = extractUserFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    
    // Retrieve all users from the database
    @Override
    public List<User> getAllUsers() {
        pstmt = null;
        res = null;
        List<User> users = new ArrayList<>();

        try {
            stmt = con.createStatement();
            res = stmt.executeQuery(SELECT_ALL_QUERY);

            while (res.next()) {
                users.add(extractUserFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    
    // Update the details of an existing user
    @Override
    public void updateUser(User user) {
        pstmt = null;
        try {
            pstmt = con.prepareStatement(UPDATE_QUERY);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());  // Assuming password is hashed
            pstmt.setString(3, user.getRole().name());
            pstmt.setInt(4, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // Delete a user from the database by their ID

    @Override
    public void deleteUser(int id) {
        pstmt = null;
        try {
            pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    // Retrieve users by their role

    @Override
    public List<User> getUsersByRole(User.Role role) {
        pstmt = null;
        res = null;
        List<User> users = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(SELECT_BY_ROLE_QUERY);
            pstmt.setString(1, role.name());
            res = pstmt.executeQuery();

            while (res.next()) {
                users.add(extractUserFromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Helper method to extract User from ResultSet
    private User extractUserFromResultSet(ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String userName = res.getString("username");
        String password = res.getString("password");
        String roleString = res.getString("role");
        User.Role role = User.Role.valueOf(roleString);

        return new User(id, userName, password, role);
    }

    
    // Retrieve a user by their ID (alternative method)
    @Override
    public User getUserById(int id) {
        pstmt = null;
        res = null;
        User user = null;

        try {
            pstmt = con.prepareStatement(SELECT_QUERY);  // Reuse the existing SELECT_QUERY, no need to redefine
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();

            if (res.next()) {
                user = extractUserFromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to verify password
    public boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(enteredPassword, storedHashedPassword);
    }
}
