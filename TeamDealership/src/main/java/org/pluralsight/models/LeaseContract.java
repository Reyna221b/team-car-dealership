package org.pluralsight.models;

public class LeaseContract extends Contract
{
    private double endingValue;
    private double leaseFee;
    private final double price = getVehicleSold().getPrice();

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double leaseFee, double endingValue)
    {
        super(date, customerName, customerEmail, vehicleSold);
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
        return (price - getEndingValue()) + getLeaseFee() ;
    }

    @Override
    public double getMonthlyPayment()
    {
        double interestRate = 0.04; // monthly interest rate
        int loanLengthMonths = 36; // loan length in months


        // Monthly payment calculation using mortgage formula
        double monthlyPayment = getTotalPrice() * interestRate * Math.pow(1 + interestRate, loanLengthMonths) / (Math.pow(1 + interestRate, loanLengthMonths) - 1);

        return monthlyPayment;
    }

    public String toString() {
        return String.format("%-15s %-15s  %-20s %-35s  %-15d  %-15d %-15s  %-15s %-15s %-15s %-15s \t%-10.2f \t\t%.2f\t \t%.2f ",
                "LEASED",getDate(), getCustomerName(), getCustomerEmail(),
                getVehicleSold().getVin(), getVehicleSold().getYear(),
                getVehicleSold().getMake(), getVehicleSold().getModel(),
                getVehicleSold().getVehicleType(), getVehicleSold().getColor(),
                getVehicleSold().getOdometer(), getVehicleSold().getPrice(),
                leaseFee, endingValue);
    }
}

