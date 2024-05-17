package org.pluralsight.pluralsight.models;

public class SalesContract extends Contract
{
    // variables
    private final double salesTax;
    private final double recordingFee;
    private double processingFee;
    private boolean finance;

    // constructor
    public SalesContract(String date, String customerName, String customerEmail, String vehicleSold, double totalPrice, double monthlyPayment, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
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

        if(Vehicle.getPrice)
        {

        }
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
    public double getPrice()
    {

    }

    @Override
    public double getMonthlyPayment()
    {

    }
}
