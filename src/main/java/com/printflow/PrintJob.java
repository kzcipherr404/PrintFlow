
package com.printflow;

public abstract class PrintJob {

    private String jobId;
    private Customer customer;
    private int pages;
    private String status;

    public PrintJob(String jobId, Customer customer, int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException(
                "Pages must be greater than zero."
            );
        }

        this.jobId = jobId;
        this.customer = customer;
        this.pages = pages;
        this.status = "QUEUED";
    }

    public String getJobId() {
        return jobId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getPages() {
        return pages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract double getCost();
}
