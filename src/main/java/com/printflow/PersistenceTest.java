package com.printflow;
public class PersistenceTest {

    public static void main(String[] args) {

        // Create a new queue using the same file
        PrintQueue queue = new PrintQueue("test-jobs.txt");

        // Check whether previously saved jobs were loaded
        System.out.println(
            "Jobs loaded: " + queue.size()
        );

        // Display the restored jobs
        for (PrintJob job : queue.getAllJobs()) {

            System.out.println(
                "Job ID: " + job.getJobId()
            );

            System.out.println(
                "Customer: " + job.getCustomer().getName()
            );

            System.out.println(
                "Status: " + job.getStatus()
            );

            System.out.println(
                "Cost: K" + job.getCost()
            );
        }
    }
}