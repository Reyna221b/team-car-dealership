package org.pluralsight.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalesContractTest {

    @Test
    public void calculateMonthlyPayment_whenFinanceTrue_shouldReturnCorrectValue() {
        // Creating a Vehicle object
        Vehicle vehicle = new Vehicle(111, 2023,"Honda", "Pilot", "Suv", "Red",20000, 9000);

        // Creating a PurchaseContract object with finance set to true
        SalesContract salesContract = new SalesContract("2024-05-17", "John Doe", "john@example.com", vehicle, 1000.0, 50.0, 25.0, 295.00, true);

        // Monthly payment calculation
        double totalPrice = salesContract.getTotalPrice();
        double monthlyInterestRate = (totalPrice > 10000) ? 0.0425 : 0.0525;
        int loanLength = (totalPrice > 10000) ? 48 : 24;
        double expectedMonthlyPayment = totalPrice * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanLength)
                / (Math.pow(1 + monthlyInterestRate, loanLength) - 1);

        // Asserting that the calculated monthly payment matches the expected monthly payment
        assertEquals(expectedMonthlyPayment, salesContract.getMonthlyPayment(), 0.01);
    }

    @Test
    public void calculateMonthlyPayment_whenFinanceFalse_shouldReturnZero() {
        // Creating a Vehicle object
        Vehicle vehicle = new Vehicle(222, 2022,"Toyota", "Camry", "Sedan","Pink", 20000, 13000);

        // Creating a PurchaseContract object with finance set to false
        SalesContract salesContract = new SalesContract("2024-05-17", "John Doe", "john@example.com", vehicle, 1000.0, 50.0, 25.0, 295.00, false);

        // Asserting that the monthly payment is zero when finance is false
        assertEquals(0, salesContract.getMonthlyPayment(), 0.01);
    }
}