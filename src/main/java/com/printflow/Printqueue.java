
package com.printflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Manages all printing jobs and their queue positions
public class PrintQueue {

    // Collection that stores all printing jobs
    private final List<PrintJob> jobs = new ArrayList<>();

    // Location of the file used to save printing jobs
    private final String filePath;

    // Constructor initializes the queue and loads saved jobs
    public PrintQueue(String filePath) {

        // Validate the file path
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "File path cannot be blank."
            );
        }

        this.filePath = filePath;

        // Load previously saved jobs when the queue is created
        load();
    }

    // Adds a new printing job to the queue
    public void addJob(PrintJob job) {

        // Prevent null jobs from being added
        if (job == null) {
            throw new IllegalArgumentException(
                "Job cannot be null."
            );
        }

        // Prevent duplicate job IDs
        if (findJobOrNull(job.getJobId()) != null) {
            throw new IllegalArgumentException(
                "Job ID already exists: " + job.getJobId()
            );
        }

        // Add the job to the collection
        jobs.add(job);

        // Save the updated queue
        save();
    }

    // Returns all jobs currently in the queue
    public List<PrintJob> getAllJobs() {

        // Prevent external modification of the queue list
        return Collections.unmodifiableList(
            new ArrayList<>(jobs)
        );
    }

    // Finds a printing job using its unique ID
    public PrintJob findById(String jobId)
            throws JobNotFoundException {

        PrintJob job = findJobOrNull(jobId);

        // Throw an exception if the job doesn't exist
        if (job == null) {
            throw new JobNotFoundException(jobId);
        }

        return job;
    }

    // Internal method that searches for a job
    // Returns null if the job cannot be found
    private PrintJob findJobOrNull(String jobId) {

        for (PrintJob job : jobs) {

            if (job.getJobId().equals(jobId)) {
                return job;
            }
        }

        return null;
    }

    // Returns the position of a job in the queue
    public int getPosition(String jobId)
            throws JobNotFoundException {

        // Search through the queue
        for (int i = 0; i < jobs.size(); i++) {

            if (jobs.get(i).getJobId().equals(jobId)) {

                // Queue positions start at 1, not 0
                return i + 1;
            }
        }

        // Job was not found
        throw new JobNotFoundException(jobId);
    }

    // Updates the status of an existing printing job
    public void updateStatus(String jobId, String newStatus)
            throws JobNotFoundException {

        if (newStatus == null) {
            throw new IllegalArgumentException(
                "New status cannot be null."
            );
        }

        // Find the requested job
        PrintJob job = findById(jobId);

        // Update its status using the parent class method
        job.setStatus(newStatus);

        // Save the updated information
        save();
    }

    // Removes a printing job from the queue
    public void removeJob(String jobId)
            throws JobNotFoundException {

        // Find the job before removing it
        PrintJob job = findById(jobId);

        // Remove the job from the collection
        jobs.remove(job);

        // Save the updated queue
        save();
    }

    // Returns the total number of jobs
    public int size() {
        return jobs.size();
    }

    // Checks whether the queue is empty
    public boolean isEmpty() {
        return jobs.isEmpty();
    }

    // Saves all printing jobs to a file
    public void save() {

        try (PrintWriter writer =
                new PrintWriter(new FileWriter(filePath))) {

            // Loop through every job in the queue
            for (PrintJob job : jobs) {

                // Convert each job into a text record
                writer.println(serialize(job));
            }

        } catch (IOException e) {

            // Report file-saving errors
            throw new UncheckedIOException(
                "Failed to save jobs to " + filePath, e
            );
        }
    }

    // Loads previously saved printing jobs from a file
    public void load() {

        // Clear existing jobs before loading
        jobs.clear();

        File file = new File(filePath);

        // No saved file means there are no previous jobs
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            // Read each saved job record
            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {

                    // Convert the text record back into a job
                    jobs.add(deserialize(line));
                }
            }

        } catch (IOException e) {

            // Report file-loading errors
            throw new UncheckedIOException(
                "Failed to load jobs from " + filePath, e
            );
        }
    }

    // Converts a printing job into a text record for storage
    private String serialize(PrintJob job) {

        // Identify the type of printing job
        String type = (job instanceof BlackWhiteJob)
                ? "BW" : "COLOUR";

        Customer customer = job.getCustomer();

        // Store job information separated by |
        return String.join("|",
            job.getJobId(),
            customer.getName(),
            customer.getContactNumber(),
            String.valueOf(job.getPages()),
            type,
            job.getStatus()
        );
    }

    // Reconstructs a printing job from a saved text record
    private PrintJob deserialize(String line) {

        // Separate the stored information
        String[] data = line.split("\\|", -1);

        // Ensure the record has the expected fields
        if (data.length != 6) {
            throw new IllegalStateException(
                "Corrupt job record: " + line
            );
        }

        String id = data[0];
        String name = data[1];
        String contact = data[2];
        int pages = Integer.parseInt(data[3]);
        String type = data[4];
        String status = data[5];

        // Recreate the customer
        Customer customer = new Customer(name, contact);

        PrintJob job;

        // Recreate the correct printing job type
        if (type.equals("BW")) {

            job = new BlackWhiteJob(id, customer, pages);

        } else if (type.equals("COLOUR")) {

            job = new ColourJob(id, customer, pages);

        } else {

            throw new IllegalStateException(
                "Unknown job type: " + type
            );
        }

        // Restore the original job status
        job.setStatus(status);

        return job;
    }
}
