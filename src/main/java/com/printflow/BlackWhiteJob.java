package com.printflow;

abstract class PrintJob {

    protected int pages;

    // Parent constructor
    public PrintJob(int pages) {
        this.pages = pages;
    }

    // Abstract method
    public abstract double getCost();
}

public class BlackWhiteJob extends PrintJob {

    private final double costPerPage = 2.00;

    // Constructor
    public BlackWhiteJob(int pages) {
        super(pages);
    }

    // Override parent method
    @Override
    public double getCost() {
        return pages * costPerPage;
    }
}
