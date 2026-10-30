
package com.printflow;

// Represents a customer who submits printing jobs
public class Customer {

    // Private fields demonstrate encapsulation
    private String name;
    private String contactNumber;

    // Constructor initializes customer information
    public Customer(String name, String contactNumber) {

        // Validate customer name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Customer name cannot be empty."
            );
        }

        // Validate contact number
        if (contactNumber == null ||
            contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Contact number cannot be empty."
            );
        }

        this.name = name;
        this.contactNumber = contactNumber;
    }

    // Returns the customer's name
    public String getName() {
        return name;
    }

    // Returns the customer's contact number
    public String getContactNumber() {
        return contactNumber;
    }

    // Updates the customer's name
    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Customer name cannot be empty."
            );
        }

        this.name = name;
    }

    // Updates the customer's contact number
    public void setContactNumber(String contactNumber) {

        if (contactNumber == null ||
            contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Contact number cannot be empty."
            );
        }

        this.contactNumber = contactNumber;
    }

    // Returns customer information as a readable string
    @Override
    public String toString() {
        return "Customer{name='" + name +
               "', contactNumber='" + contactNumber + "'}";
    }
}
