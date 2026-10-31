package com.printflow;
// Used to test PrintQueue functionality
public class PrintQueueTest {

    public static void main(String[] args) {

        // Create a queue that stores jobs in a file
        PrintQueue queue = new PrintQueue("test-jobs.txt");

        // Create a customer
        Customer customer = new Customer(
            "Kondwani Zulu",
            "0970000001"
        );

        // Create printing jobs
        PrintJob job1 = new BlackWhiteJob(
            "P001", customer, 9
        );

        PrintJob job2 = new ColourJob(
            "P002", customer, 5
        );

        // TEST 1: Add jobs to the queue
        System.out.println("TEST 1: ADDING JOBS");

        queue.addJob(job1);
        queue.addJob(job2);

        System.out.println("Total jobs: " + queue.size());

        // TEST 2: Display all jobs
        System.out.println("\nTEST 2: DISPLAYING JOBS");

        for (PrintJob job : queue.getAllJobs()) {

            System.out.println(
                job.getJobId() +
                " | " + job.getCustomer().getName() +
                " | Pages: " + job.getPages() +
                " | Cost: K" + job.getCost() +
                " | Status: " + job.getStatus()
            );
        }

        // TEST 3: Check queue positions
        System.out.println("\nTEST 3: QUEUE POSITIONS");

        try {
            System.out.println(
                "P001 position: " + queue.getPosition("P001")
            );

            System.out.println(
                "P002 position: " + queue.getPosition("P002")
            );

        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // TEST 4: Update a job status
        System.out.println("\nTEST 4: UPDATING STATUS");

        try {
            queue.updateStatus("P001", "PRINTING");

            System.out.println(
                "P001 new status: " +
                queue.findById("P001").getStatus()
            );

        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // TEST 5: Find a job
        System.out.println("\nTEST 5: FINDING A JOB");

        try {
            PrintJob foundJob = queue.findById("P002");

            System.out.println(
                "Found job: " + foundJob.getJobId()
            );

        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // TEST 6: Remove a job
        System.out.println("\nTEST 6: REMOVING A JOB");

        try {
            queue.removeJob("P002");

            System.out.println(
                "Remaining jobs: " + queue.size()
            );

        } catch (JobNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // TEST 7: Save the queue
        queue.save();

        System.out.println("\nTEST 7: SAVING DATA");
        System.out.println("Jobs saved successfully.");

        System.out.println("\nTESTING COMPLETED.");
    }
}
