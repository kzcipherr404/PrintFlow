
package com.printflow;

// Colour printing job inherits from PrintJob
public class ColourJob extends PrintJob {

    // Fixed cost for printing one colour page
    private static final double COST_PER_PAGE = 5.00;

    // Constructor passes job details to the parent class
    public ColourJob(String jobId, Customer customer, int pages) {
        super(jobId, customer, pages);
    }

    // Overrides the parent's abstract getCost() method
    @Override
    public double getCost() {

        // Calculate cost using the number of pages
        return getPages() * COST_PER_PAGE;
    }
}
