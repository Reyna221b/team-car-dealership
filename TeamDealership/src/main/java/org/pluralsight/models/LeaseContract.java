package org.pluralsight.models;

public class LeaseContract extends Contract
{
    private double endingValue;
    private double leaseFee;
    private double price = getVehicleSold().getPrice();

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice, double monthlyPayment, double leaseFee, double endingValue)
    {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.leaseFee = leaseFee;
        this.endingValue = endingValue;
    }

    public double getEndingValue()
    {
        return endingValue = price * 0.5;
    }

    public void setEndingValue(double endingValue)
    {
        this.endingValue = endingValue;
    }

    public double getLeaseFee()
    {
        return leaseFee = price * 0.07;
    }

    public void setLeaseFee(double leaseFee)
    {
        this.leaseFee = leaseFee;
    }
    @Override
    public double getTotalPrice()
    {
        return (price-getEndingValue()) + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment()
    {
        double interestRate = 0.04;
        double loanLength = 36;
        return (getTotalPrice() * interestRate * (Math.pow(1 + interestRate, loanLength)) / (Math.pow(1 + interestRate, loanLength) - 1));
    }
}
