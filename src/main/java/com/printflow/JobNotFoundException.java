package com.printflow;

public class JobNotFoundException extends Exception {

    public JobNotFoundException(String jobId) {
        super("Job with ID " + jobId + " was not found");
    }
}

