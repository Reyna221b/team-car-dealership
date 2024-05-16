package org.pluralsight;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DealershipFileManager
{

    public Dealership getDealership()
    {
        Dealership dealership = null;
        File file = new File("files/inventory.csv");

        try (FileReader fileReader = new FileReader(file);
             Scanner scanner = new Scanner(fileReader)
        ) {

            String dealerLine = scanner.nextLine();
            Scanner lineScanner1 = new Scanner(dealerLine);
            lineScanner1.useDelimiter("\\|");


            String dealershipName = lineScanner1.next();
            String dealershipAddress = lineScanner1.next();
            String dealershipPhone = lineScanner1.next();
            dealership = new Dealership(dealershipName, dealershipAddress, dealershipPhone);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Vehicle vehicleEntry = getVehicle(line);

                dealership.addVehicle(vehicleEntry);
            }

        } catch (IOException ex) {
            System.out.println("Error reading file or somethin");
        }
        return dealership;
    }

    private static Vehicle getVehicle(String line)
    {
        Scanner lineScanner = new Scanner(line);

        lineScanner.useDelimiter("\\|");

        int vin = lineScanner.nextInt();
        int year = lineScanner.nextInt();
        String make = lineScanner.next();
        String model = lineScanner.next();
        String vehicleType = lineScanner.next();
        String color = lineScanner.next();
        int odometer = lineScanner.nextInt();
        double price = lineScanner.nextDouble();

        Vehicle vehicleEntry = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        return vehicleEntry;
    }


    public void saveDealership (Dealership dealership)
        {
            try (FileWriter writer = new FileWriter("files/inventory.csv")) {
                writer.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone() + "\n");

                for (Vehicle v : dealership.getAllVehicles()) {
                    writer.write(v.getVin() + "|" + v.getYear() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getVehicleType() + "|" + v.getColor() + "|" + v.getOdometer() + "|" + v.getPrice() + "\n");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


