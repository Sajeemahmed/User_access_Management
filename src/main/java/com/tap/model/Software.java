package com.tap.model;

import com.tap.model.Software.AccessLevel;

public class Software {

    private int id;
    private String name;
    private String description;
    private AccessLevel accessLevel;  // AccessLevel for different types of access

    // Enum for AccessLevel (matching values from the database table)
    public enum AccessLevel {
        Admin,  // Matches 'Admin' in the table
        Write,  // Matches 'Write' in the table
        Read    // Matches 'Read' in the table
    }

    // Constructor
    public Software() {}

    public Software(int id, String name, String description, AccessLevel accessLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accessLevel = accessLevel;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Additional methods for better clarity if needed
   
}
