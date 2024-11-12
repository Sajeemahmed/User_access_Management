package com.tap.model;

import java.time.LocalDateTime;

public class Request {
    private int id;                  // Request ID
    private User user_id;               // The user who made the request
    private Software software_id;       // The software being requested
    private String accessType;       // The type of access (Read, Write, Admin)
    private String reason;           // Reason for the request
    private Status status;           // Status of the request (Pending, Approved, Rejected)
    // Timestamp for the request

    // Enum for request status (Pending, Approved, Rejected)
    public enum Status {
        Pending, Approved, Rejected
    }
    public Request() {
    	
    }
	public Request(int id, User user_id, Software software_id, String accessType, String reason, Status status) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.software_id = software_id;
		this.accessType = accessType;
		this.reason = reason;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public Software getSoftware_id() {
		return software_id;
	}
	public void setSoftware_id(Software software_id) {
		this.software_id = software_id;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", user_id=" + user_id + ", software_id=" + software_id + ", accessType="
				+ accessType + ", reason=" + reason + ", status=" + status + "]";
	}

    // Constructor with parameters
   // Set the current timestamp
    

}