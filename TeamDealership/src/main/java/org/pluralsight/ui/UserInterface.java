package org.pluralsight.ui;


import org.pluralsight.models.*;
import org.pluralsight.services.ContractFileManager;
import org.pluralsight.services.Dealership;
import org.pluralsight.services.DealershipFileManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface
{
    static final Scanner userInput = new Scanner(System.in);
    public Dealership dealership;
    public DealershipFileManager fileManager;
    public List<Vehicle> vehicleList;
    public Vehicle vehicle;

    public void display(){
        init();
        while (true){
            try{
                System.out.println(Colors.CYAN);
                System.out.println("\t\tHome Screen");
                System.out.println("-".repeat(30));
                System.out.println("Find Vehicles by: ");
                System.out.println("[1] - Price Range");
                System.out.println("[2] - Make/Model");
                System.out.println("[3] - Year");
                System.out.println("[4] - Color");
                System.out.println("[5] - Mileage Range");
                System.out.println("[6] - Type(car, suv, truck, van)");
                System.out.println("[7] - List All Vehicles");
                System.out.println("[8] - Add a vehicle");
                System.out.println("[9] - Remove a Vehicle");
                System.out.println("[10] - Sell/Lease a Vehicle");
                System.out.println(Colors.RED + "[99] - Quit"+ Colors.RESET);
                System.out.println(Colors.CYAN+ "-".repeat(30));
                System.out.print(Colors.RESET);
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(userInput.nextLine().strip());

                switch (choice) {
                    case 1:
                        processGetByPriceRequest();
                        break;
                    case 2:
                        processGetByMakeModelRequest();
                        break;
                    case 3:
                        processGetByYearRequest();
                        break;
                    case 4:
                       processGetByColorRequest();
                        break;
                    case 5:
                        processGetByMileageRequest();
                        break;
                    case 6:
                        processGetByVehicleTypeRequest();
                        break;
                    case 7:
                        processGetAllVehiclesRequest();
                        break;
                    case 8:
                        processAddVehicleRequest();
                        break;
                    case 9:
                        processRemoveVehicleRequest();
                        break;
                    case 10:
                        sellOrLeaseOption();
                        break;
                    case 99:
                        return;
                    default:
                        System.out.println("Invalid Input.");
                }


            } catch (Exception ex) {
                System.out.println(Colors.RED + "invalid selection!"+Colors.RESET);
            }
        }

    }

    private void sellOrLeaseOption()
    {
        System.out.println("[1] - Sell a Vehicle");
        System.out.println("[2] - Lease a Vehicle");
        int choice = Integer.parseInt(userInput.nextLine().strip());

        switch (choice)
        {
            case 1:
                saleVehicle();
                break;
            case 2:
                leaseVehicle();
                break;
            default:
                System.out.println(Colors.RED + "invalid selection!"+Colors.RESET);
                break;
        }
    }

    private void saleVehicle()
    {
        ContractFileManager contractManager = new ContractFileManager();
        SalesContract sell = null;
        processGetAllVehiclesRequest();


        while (true)
        {
            System.out.println("Enter the vin number of the Vehicle you would like to Sell: ");
            int vin = Integer.parseInt(userInput.nextLine().strip());

            Vehicle vehicleSold = null;

            for (Vehicle v : dealership.getAllVehicles())
            {
                if (v.getVin() == vin)
                {
                    vehicleSold = v;
                    break;
                }

            }
            if (vehicleSold == null)
            {
                System.out.println("Vin number: " + vin + " not found. ");
                continue;
            }

            System.out.println("Enter name: ");
            String customer = userInput.nextLine().strip();

            System.out.println("Enter your Email: ");
            String email = userInput.nextLine().strip();


            System.out.println("Want to Finance (Yes/No): ");
            try{
                String financed = userInput.nextLine().strip();
                if(financed.equalsIgnoreCase("yes")){
                    sell.setFinance(true);
                }
                else {
                    sell.setFinance(false);
                }
            }catch(Exception e){
                System.out.println(Colors.RED + "invalid input!"+Colors.RESET);
            }



            double total = sell.getTotalPrice();
            double monthly = sell.getMonthlyPayment();
            double tax = sell.getSalesTax();

            SalesContract sale = new SalesContract( getCurrentDate(),customer,email,vehicleSold,
                    total,monthly,tax, sell.getRecordingFee(), sell.getProcessingFee(),sell.isFinance());

            contractManager.saveContract(sale);
            dealership.removeVehicle(vehicleSold);
            System.out.println("CONGRATS WE HAVE SOLD A VEHICLE!!!");
            break;
        }

    }

    private String getCurrentDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Date todayDate = new Date();
        return formatDate.format(todayDate);
    }

    public void processGetByPriceRequest(){
        int max =0;
        int min = 0;
        try{
            System.out.print("Min. Price: ");
            min = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Max. Price: ");
            max = Integer.parseInt(userInput.nextLine().strip());

        }catch(Exception e){
            System.out.println(Colors.RED + "invalid selection!"+Colors.RESET);
        }
        List<Vehicle> price = dealership.getVehicleByPrice(min, max);
        displayVehicles(price);
    }

    public void processGetByMakeModelRequest(){
        String make = null;
        String model = null;
        try {

            System.out.print("Vehicle Make:  ");
            make = userInput.nextLine().strip().toLowerCase();
            System.out.print("Vehicle Model:  ");
            model = userInput.nextLine().strip().toLowerCase();

        } catch (Exception e) {

            System.out.println(Colors.RED + "invalid input!" + Colors.RESET);
        }

        List<Vehicle> makeModel = dealership.getVehicleByMakeModel(make, model);
        displayVehicles(makeModel);
    }

    public void processGetByYearRequest(){
        int max =0;
        int min = 0;
        try{
            System.out.print("Min. Year: ");
            min = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Max. Year: ");
            max = Integer.parseInt(userInput.nextLine().strip());
        }catch(Exception e){
            System.out.println(Colors.RED + "invalid selection!"+Colors.RESET);
        }
        List<Vehicle> year = dealership.getVehicleByYear(min, max);
        displayVehicles(year);
    }

    public void processGetByColorRequest(){
        String carColor = null;
        try {

            System.out.print("Vehicle Color:  ");
            carColor = userInput.nextLine().strip();

        } catch (Exception e) {

            System.out.println(Colors.RED + "invalid input!" + Colors.RESET);
        }

        List<Vehicle> colors = dealership.getVehicleByColor(carColor);
        displayVehicles(colors);
    }

    public void processGetByMileageRequest(){
        int max =0;
        int min = 0;
        try{
            System.out.print("Min. Mileage: ");
            min = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Max. Mileage: ");
            max = Integer.parseInt(userInput.nextLine().strip());
        }catch(Exception e){
            System.out.println(Colors.RED + "invalid selection!"+Colors.RESET);
        }
        List<Vehicle>range = dealership.getVehicleByMileage(min, max);
        displayVehicles(range);

    }

    public void processGetByVehicleTypeRequest()
    {
        String type = null;
        try {

            System.out.print("Vehicle Type:  ");
            type = userInput.nextLine().strip();

        } catch (Exception e) {

            System.out.println(Colors.RED + "invalid input!" + Colors.RESET);
        }

        List<Vehicle> types = dealership.getVehicleByType(type);
        displayVehicles(types);

    }

    public void processAddVehicleRequest()
    {
        try{

            System.out.print("Please enter the vin: ");
            int vin = Integer.parseInt(userInput.nextLine().strip());

            System.out.print("Enter the year: ");
            int year = Integer.parseInt(userInput.nextLine().strip());

            System.out.print("Enter the make: ");
            String make = userInput.nextLine().strip();
            System.out.print("Enter the model: ");
            String model = userInput.nextLine().strip();
            System.out.print("Enter the vehicle type: ");
            String vehicleType = userInput.nextLine().strip();
            System.out.print("Enter the color: ");
            String color = userInput.nextLine().strip();


            System.out.print("Enter the odometer: ");
            int odometer = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Enter the price: ");
            double price = Double.parseDouble(userInput.nextLine().strip());

            vehicle = new Vehicle(vin,year,make,model,vehicleType,color,odometer,price);

            dealership.addVehicle(vehicle);
            fileManager.saveDealership(dealership);

            System.out.println("\nVehicle added successfully. ");

        }
        catch (Exception e){
            System.out.println(Colors.RED + "invalid input!" + Colors.RESET);
        }

    }

    public void processRemoveVehicleRequest(){

        while(true){

            try{
                System.out.print("Please enter the vin: ");
                int removeVin = Integer.parseInt(userInput.nextLine().strip());
                boolean removed = false;
                for (Vehicle vehicle : dealership.getAllVehicles()) {
                    if (vehicle.getVin() == removeVin) {
                        dealership.removeVehicle(vehicle);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    fileManager.saveDealership(dealership);
                    System.out.println("\nVehicle removed successfully!");
                } else {
                    System.out.println("Vehicle with VIN " + removeVin + " not found.");
                }
                break;
            } catch (Exception e) {
                System.out.println(Colors.RED + "Error: " + e.getMessage() + Colors.RESET);
                e.printStackTrace();
            }
        }


    }

    private void displayVehicles(List<Vehicle>vehicles){
        System.out.println("\nResults:");
        System.out.println("-".repeat(125));
        System.out.printf("%-15s  %-14s  %-15s  %-15s  %-14s  %-15s  %-13s  %-15s\n",
                "Vin", "Year", "Make", "Model", "Color", "Type", "Odometer", "Price");
        System.out.println("-".repeat(125));
        if(vehicles.isEmpty()){
            System.out.println("Sorry no matches found!");
            return;
        }

        for(Vehicle v : vehicles){
            System.out.println(v);
        }
    }

    private void init(){

        fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();

    }

    public void processGetAllVehiclesRequest(){
        vehicleList = dealership.getAllVehicles();
        displayVehicles(vehicleList);

    }
}
