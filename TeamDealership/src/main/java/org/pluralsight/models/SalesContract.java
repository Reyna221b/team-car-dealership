package org.pluralsight.models;

import org.pluralsight.services.Dealership;

public class SalesContract extends Contract
{
    // variables
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean finance;
    public Vehicle v;

    // constructor
    public SalesContract(String date, String customerName, String customerEmail, int vehicleSold, double totalPrice, double monthlyPayment, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.finance = finance;
    }

    // getters and setters
    public double getSalesTax() {
        return salesTax = v.getPrice() * 0.05;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {

        if(v.getPrice() > 1000)
        {
            return processingFee = 295.00;
        }
        else
        {
            return processingFee = 495.00;
        }

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
