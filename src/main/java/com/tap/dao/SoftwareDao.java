package com.tap.dao;

import com.tap.model.Software;
import java.util.List;

public interface SoftwareDao {

    // Create a new software entry
    boolean addSoftware(Software software);

    // Get a software entry by ID


    // Get all software entries
    List<Software> getAllSoftware();

    // Update an existing software entry
    void updateSoftware(Software software);

    // Delete a software entry by ID
    void deleteSoftware(int id);

	Software getSoftware(int softwareId);
	Software getSoftwareByName(String name);
}
