package org.pluralsight.services;

import org.pluralsight.models.Contract;
import org.pluralsight.models.LeaseContract;
import org.pluralsight.models.SalesContract;

import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager
{
    public void saveContract(Contract contract){

        //String contractCSV = "file/contracts.csv";

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
