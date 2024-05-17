package org.pluralsight.services;

import org.pluralsight.models.Contract;
import org.pluralsight.models.SalesContract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ContractFileManager
{
    public static ArrayList<Contract> loadContract(){
        ArrayList<Contract> contracts = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File("file/contracts.csv")))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] tokens = line.split("\\|");

                String contractType = tokens[0];

                if(contractType.equalsIgnoreCase("sale"))
                {
                    String date = tokens[1];
                    String name = tokens[2];
                    String email = tokens[3];
                    int carId = Integer.parseInt(tokens[4]);
                    int year = Integer.parseInt(tokens[5]);
                    String make = tokens[6];
                    String model = tokens[7];
                    String vehicleType = tokens[8];
                    String color = tokens[9];
                    int odometer = Integer.parseInt(tokens[10]);
                    double price = Double.parseDouble(tokens[11]);
                    double salesTax = Double.parseDouble(tokens[12]);
                    double recordingFee = Double.parseDouble(tokens[13]);
                    double processFee = Double.parseDouble(tokens[14]);
                    double totalCost = Double.parseDouble(tokens[15]);
                    boolean isfinanced = Boolean.parseBoolean(tokens[16]);
                    double monthly = Double.parseDouble(tokens[17]);

                    Contract sale = new SalesContract(date,name,email,carId,)
                }
                else
                {

                }
            }
        }
        catch (IOException e)
        {

        }

        return contracts;
    }

}
