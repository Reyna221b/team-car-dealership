package org.pluralsight.models;

public class SalesContract extends Contract
{
    // variables
    private double salesTax;
    private double recordingFee = 100.00;
    private double processingFee;
    private boolean finance;

    // constructor
    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice, double monthlyPayment, double salesTax, double recordingFee, double processingFee, boolean finance) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.finance = finance;
    }

    // getters and setters
    public double getSalesTax() {
        return salesTax = getVehicleSold().getPrice() * 0.05;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {

        if(getVehicleSold().getPrice() < 10000)
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
    public double getTotalPrice()
    {
        double price = getVehicleSold().getPrice() + getSalesTax() + getRecordingFee() + getProcessingFee();
        return price;
    }

    @Override
    public double getMonthlyPayment()
    {
        if(finance)
        {
            double interestRate = (getTotalPrice() > 10000) ? 0.0425 : 0.0525;
            double loanLength = (getTotalPrice() > 10000) ?  48 : 24;
            return (getTotalPrice() * interestRate * (Math.pow(1 + interestRate, loanLength)) / (Math.pow(1 + interestRate, loanLength) - 1));
        }
        else{
            return 0;
        }
    }

}
