package org.pluralsight.services;

import org.pluralsight.models.Contract;
import org.pluralsight.models.SalesContract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContractFileManager
{
    public void saveContract(Contract contract){

        private static final String contractCSV = "file/contracts.csv";


        try(FileWriter fileWriter = new FileWriter(contractCSV, true);
            PrintWriter writer = new PrintWriter(fileWriter)
        ) {
            String contractType;
            if(contract instanceof SalesContract)
            {
                contractType = "SALE";
                writer.write(String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f|\n",contractType,
                        contract.getDate(),contract.getCustomerName(),contract.getCustomerEmail(),
                        contract.getVehicleSold().getVin(),contract.getVehicleSold().getYear(),
                        contract.getVehicleSold().getMake(),contract.getVehicleSold().getModel(),
                        contract.getVehicleSold().getVehicleType(),contract.getVehicleSold().getColor(),
                        contract.getVehicleSold().getOdometer(),contract.getVehicleSold().getPrice(),
                        ((SalesContract) contract).getSalesTax(),((SalesContract) contract).getRecordingFee(),
                        ((SalesContract) contract).getProcessingFee(),contract.getTotalPrice(),((SalesContract) contract).isFinance(),
                        contract.getMonthlyPayment()
                ));

            }
            else {
                contractType = "LEASE";
                writer.write(String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f|\n",contractType,
                        contract.getDate(),contract.getCustomerName(),contract.getCustomerEmail(),
                        contract.getVehicleSold().getVin(),contract.getVehicleSold().getYear(),
                        contract.getVehicleSold().getMake(),contract.getVehicleSold().getModel(),
                        contract.getVehicleSold().getVehicleType(),contract.getVehicleSold().getColor(),
                        contract.getVehicleSold().getOdometer(),contract.getVehicleSold().getPrice(),
                        ((SalesContract) contract).getSalesTax(),((SalesContract) contract).getRecordingFee(),
                        ((SalesContract) contract).getProcessingFee(),contract.getTotalPrice(),((SalesContract) contract).isFinance(),
                        contract.getMonthlyPayment()
                ));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
