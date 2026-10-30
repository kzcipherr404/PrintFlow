package com.printflow;

import java.util.Scanner;

public class ColourJob extends PrintJob {

    private final double costPerPage = 5.00;

    // Constructor
    public ColourJob(int pages) {
        super(pages);
    }

    // Override parent method
    @Override
    public double getCost() {
        return pages * costPerPage;
    }

    // Main application
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Enter black-and-white pages
        System.out.print("Enter number of black-and-white pages: ");
        int bwPages = input.nextInt();

        // Enter colour pages
        System.out.print("Enter number of colour pages: ");
        int colourPages = input.nextInt();

        // Create printing objects
        PrintJob blackWhite = new BlackWhiteJob(bwPages);
        PrintJob colour = new ColourJob(colourPages);

        // Display printing costs
        System.out.println("\n----- PRINTING COSTS -----");

        System.out.println("Black-and-white printing cost: K"
                + blackWhite.getCost());

        System.out.println("Colour printing cost: K"
                + colour.getCost());

        // Calculate total cost
        double totalCost = blackWhite.getCost()
                + colour.getCost();

        System.out.println("--------------------------");
        System.out.println("Total printing cost: K" + totalCost);

        input.close();
    }
}
