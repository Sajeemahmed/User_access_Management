package com.tap.dao;

import java.util.List;

import com.tap.model.User;

public interface UserDao {
    
    // Add a new user
    void addUser(User user);
    
    // Retrieve a user by their ID
    User getUser(int userId);
    
    // Retrieve a user by their username
    User getUserByUsername(String username);
    
    // Retrieve all users
    List<User> getAllUsers();
    
    // Update an existing user
    void updateUser(User user);
    
    // Delete a user by their ID
    void deleteUser(int userId);
    
    // Optionally, add a method to get users by their role
    List<User> getUsersByRole(User.Role role); 
    
    // Get users by their role (Employee, Manager, Admin)
    User getUserById(int id);
}
