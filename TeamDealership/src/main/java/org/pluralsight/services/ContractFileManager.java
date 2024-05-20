package org.pluralsight.services;

import org.pluralsight.models.Contract;
import org.pluralsight.models.LeaseContract;
import org.pluralsight.models.SalesContract;
import org.pluralsight.models.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContractFileManager
{
    public List<Contract> getAllContracts(){

        List<Contract> contracts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("files/contracts.csv"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");

                String contractType = fields[0];
                String date = fields[1];
                String customerName = fields[2];
                String customerEmail = fields[3];
                int vin = Integer.parseInt(fields[4]);
                int year = Integer.parseInt(fields[5]);
                String make = fields[6];
                String model = fields[7];
                String vehicleType = fields[8];
                String color = fields[9];
                int odometer = Integer.parseInt(fields[10]);
                double price = Double.parseDouble(fields[11]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                if (contractType.equals("LEASE")) {
                    double leaseFee = Double.parseDouble(fields[12]);
                    double endingValue = Double.parseDouble(fields[13]);
                    contracts.add(new LeaseContract(date, customerName, customerEmail, vehicle, leaseFee, endingValue));
                } else if (contractType.equals("SALE")) {
                    double totalPrice = Double.parseDouble(fields[12]);
                    double salesTax = Double.parseDouble(fields[13]);
                    double recordingFee = Double.parseDouble(fields[14]);
                    double processingFee = Double.parseDouble(fields[15]);
                    boolean finance = fields[16].equalsIgnoreCase("YES");
                    contracts.add(new SalesContract(date, customerName, customerEmail, vehicle, totalPrice, salesTax, recordingFee, processingFee, finance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contracts;
    }



    public void saveContract(Contract contract){

        try(FileWriter writer = new FileWriter("files/contracts.csv", true);

        ) {
            String contractType;
            if(contract instanceof SalesContract)
            {
                String financed = (((SalesContract) contract).isFinance()) ? "YES": "NO";
                contractType = "SALE";
                writer.write(String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f|\n",contractType,
                        contract.getDate(),contract.getCustomerName(),contract.getCustomerEmail(),
                        contract.getVehicleSold().getVin(),contract.getVehicleSold().getYear(),
                        contract.getVehicleSold().getMake(),contract.getVehicleSold().getModel(),
                        contract.getVehicleSold().getVehicleType(),contract.getVehicleSold().getColor(),
                        contract.getVehicleSold().getOdometer(),contract.getVehicleSold().getPrice(),
                        ((SalesContract) contract).getSalesTax(),((SalesContract) contract).getRecordingFee(),
                        ((SalesContract) contract).getProcessingFee(),contract.getTotalPrice(), financed,
                        contract.getMonthlyPayment()
                ));

            }
            else if (contract instanceof LeaseContract){
                contractType = "LEASE";
                writer.write(String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|\n",contractType,
                        contract.getDate(),contract.getCustomerName(),contract.getCustomerEmail(),
                        contract.getVehicleSold().getVin(),contract.getVehicleSold().getYear(),
                        contract.getVehicleSold().getMake(),contract.getVehicleSold().getModel(),
                        contract.getVehicleSold().getVehicleType(),contract.getVehicleSold().getColor(),
                        contract.getVehicleSold().getOdometer(),contract.getVehicleSold().getPrice(),
                        ((LeaseContract) contract).getEndingValue(), ((LeaseContract) contract).getLeaseFee(),
                        contract.getTotalPrice(), contract.getMonthlyPayment()
                ));

            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
