package org.pluralsight.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaseContractTest {

    @Test
    public void calculateTotalPrice_shouldReturnCorrectValue() {
        Vehicle vehicle = new Vehicle(111, 2023,"Honda", "Pilot", "Suv", "Red",20000, 9000);

        LeaseContract leaseContract = new LeaseContract("2024-05-17", "John Doe", "john@example.com", vehicle, 630.0, 4500.0);

        assertEquals(5130.0, leaseContract.getTotalPrice(), 0.01);
    }

    @Test
    public void calculateMonthlyPayment_shouldReturnCorrectValue() {
        Vehicle vehicle = new Vehicle(222, 2022,"Toyota", "Camry", "Sedan","Pink", 20000, 13000);

        LeaseContract leaseContract = new LeaseContract("2024-05-17", "John Doe", "john@example.com", vehicle, 500.0, 10000.0);

        assertEquals(591.97, leaseContract.getMonthlyPayment(), 0.01);

        // Monthly payment calculation
        double interestRate = 0.04;
        int loanLengthMonths = 36;
        double totalPrice = leaseContract.getTotalPrice();
        double expectedMonthlyPayment = totalPrice * interestRate * Math.pow(1 + interestRate, loanLengthMonths) / (Math.pow(1 + interestRate, loanLengthMonths) - 1);

        // Asserting that the calculated monthly payment matches the expected monthly payment
        assertEquals(expectedMonthlyPayment, leaseContract.getMonthlyPayment(), 0.01);
    }

}