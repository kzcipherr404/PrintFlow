
package com.printflow;

// Black-and-white printing job inherits from PrintJob
public class BlackWhiteJob extends PrintJob {

    // Fixed cost for printing one black-and-white page
    private static final double COST_PER_PAGE = 2.00;

    // Constructor passes job details to the parent class
    public BlackWhiteJob(String jobId, Customer customer, int pages) {
        super(jobId, customer, pages);
    }

    // Overrides the parent's abstract getCost() method
    @Override
    public double getCost() {

        // Calculate cost using the number of pages
        return getPages() * COST_PER_PAGE;
    }
}
