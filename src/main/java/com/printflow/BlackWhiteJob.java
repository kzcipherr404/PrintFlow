package com.printflow;

public class BlackWhiteJob extends PrintJob {

    private final double costPerPage = 2.00;

    public BlackWhiteJob(int pages) {
        super(pages);
    }

    @Override
    public double getCost() {
        return pages * costPerPage;
    }
}