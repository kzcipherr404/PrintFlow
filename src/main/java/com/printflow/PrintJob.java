
package com.printflow;

// Abstract parent class for all types of printing jobs
public abstract class PrintJob {

    // Private fields demonstrate encapsulation
    private final String jobId;
    private final Customer customer;
    private final int pages;
    private String status;

    // Constructor initializes a new printing job
    public PrintJob(String jobId, Customer customer, int pages) {

        // Validate the number of pages
        if (pages <= 0) {
            throw new IllegalArgumentException(
                "Pages must be greater than zero."
            );
        }

        // Validate the job ID
        if (jobId == null || jobId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Job ID cannot be empty."
            );
        }

        // A print job must belong to a customer
        if (customer == null) {
            throw new IllegalArgumentException(
                "Customer cannot be null."
            );
        }

        this.jobId = jobId;
        this.customer = customer;
        this.pages = pages;

        // Every new job starts in the QUEUED state
        this.status = "QUEUED";
    }

    // Returns the unique job ID
    public String getJobId() {
        return jobId;
    }

    // Returns the customer associated with this job
    public Customer getCustomer() {
        return customer;
    }

    // Returns the number of pages
    public int getPages() {
        return pages;
    }

    // Returns the current job status
    public String getStatus() {
        return status;
    }

    // Updates the status of a printing job
    public void setStatus(String status) {

        // Only allow recognized job statuses
        if (status == null ||
            !status.matches("QUEUED|PRINTING|READY|COLLECTED|CANCELLED")) {

            throw new IllegalArgumentException(
                "Invalid printing job status."
            );
        }

        this.status = status;
    }

    // Abstract method: subclasses calculate their own printing costs
    public abstract double getCost();
}
