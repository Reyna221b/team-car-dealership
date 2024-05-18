package org.pluralsight.models;

public class SalesContract extends Contract
{
    // variables
    private double salesTax;
    private double recordingFee = 100.00;
    private double processingFee;
    private boolean finance;
    public static final double recordFee = 100.0;

    // constructor
    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.finance = finance;

    }

    // getters and setters
    public double getSalesTax() {
        return salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment()
    {
        if (finance) {

            double monthlyInterestRate = (getTotalPrice() > 10000) ? 0.0425 : 0.0525;

            int loanLength = (getTotalPrice() > 10000) ? 48 : 24;

            double monthlyPayment = getTotalPrice() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanLength)
                    / (Math.pow(1 + monthlyInterestRate, loanLength) - 1);
            return monthlyPayment;
        }
        else {
            return 0;
        }
    }


}
