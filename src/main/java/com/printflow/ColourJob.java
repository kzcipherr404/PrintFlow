package com.printflow;
    
import java.util.Scanner;

public class ColourJob {

    private int pages;
    private double costPerPage = 5.00;

    public void getInput() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of colour pages: ");
        pages = input.nextInt();
    }

    public double calculateCost() {
        return pages * costPerPage;
    }

    public void displayCost() {
        System.out.println("Colour printing cost: K"
                + calculateCost());
    }

    public static void main(String[] args) {

        // Create BlackWhiteJob object
        BlackWhiteJob bw = new BlackWhiteJob();

        // Create ColourJob object
        ColourJob colour = new ColourJob();

        // Each class collects its own input
        bw.getInput();
        colour.getInput();

        // Display individual costs
        bw.displayCost();
        colour.displayCost();

        // Calculate total cost
        double total = bw.calculateCost()
                + colour.calculateCost();

        System.out.println("Total printing cost: K" + total);
    }
}


