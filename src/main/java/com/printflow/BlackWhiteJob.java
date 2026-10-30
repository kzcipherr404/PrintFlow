package com.printflow;

import java.util.Scanner;

public class BlackWhiteJob {

    private int pages;
    private double costPerPage = 2.00;

    public void getInput() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of black-and-white pages: ");
        pages = input.nextInt();
    }

    public double calculateCost() {
        return pages * costPerPage;
    }

    public void displayCost() {
        System.out.println("Black-and-white printing cost: K"
                + calculateCost());
    }
}
