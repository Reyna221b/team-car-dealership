package org.pluralsight.ui;

import org.pluralsight.models.*;
import org.pluralsight.services.ContractFileManager;
import org.pluralsight.services.Dealership;
import org.pluralsight.services.DealershipFileManager;
import java.rmi.dgc.Lease;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public AdminUI admin = new AdminUI();

    public void display(){
        init();
        while (true)
        {
            try{
                System.out.println(Colors.CYAN);
                // let the user know what type of app they are about to use
                System.out.println();
                System.out.println("Car dealership loading...  \uD83D\uDE97\uD83D\uDCA8");

                // prompt the user for their first name for a more personalized experience
                System.out.println();
                System.out.println("Enter your first name to log in: ");
                String userName = userInput.nextLine().trim().toUpperCase();

                // greet the user
                System.out.println(Colors.CYAN);
                System.out.println("_".repeat(65));
                System.out.println();
                System.out.println("                           Hi, " + userName + " \uD83D\uDC4B");
                System.out.println("              Welcome to \uD83D\uDC51 Queen's Motors! \uD83D\uDE99\uD83D\uDCA8");
                System.out.println();
                System.out.println("_".repeat(65));

                System.out.println();
                System.out.println("\t\t                  Home Screen");
                System.out.println();
                System.out.println("-".repeat(65));
                System.out.println("Find Vehicles by: ");
                System.out.println();
                System.out.println("[1] - Price Range");
                System.out.println("[2] - Make / Model");
                System.out.println("[3] - Year");
                System.out.println("[4] - Color");
                System.out.println("[5] - Mileage Range");
                System.out.println("[6] - Type(car, suv, truck, van)");
                System.out.println("[7] - List All Vehicles");
                System.out.println("[8] - Add a Vehicle");
                System.out.println("[9] - Remove a Vehicle");
                System.out.println("[10] - Sell / Lease a Vehicle");
                System.out.println("[11] - Admin Menu");
                System.out.println(Colors.RED + "[99] - Quit"+ Colors.RESET);
                System.out.println();
                System.out.println(Colors.CYAN+ "-".repeat(65));
                System.out.print(Colors.RESET);
                System.out.print(Colors.CYAN + "Enter your choice " + userName + ": ");
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
                    case 11:
                        admin.adminLogin();
                        break;
                    case 99:
                        System.out.println();
                        System.out.println("Goodbye for now " + userName + "❗\uFE0F \uD83D\uDC4B ");
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
        System.out.println();
        System.out.println("-".repeat(65));
        System.out.println();
        System.out.println("                      \uD83D\uDCB2 Sell or Lease");
        System.out.println();
        System.out.println("-".repeat(65));

        System.out.println("[1] - Sell a Vehicle");
        System.out.println("[2] - Lease a Vehicle");
        System.out.println();
        System.out.println("Enter your choice: ");
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
                System.out.println(Colors.RED + "Invalid selection!"+Colors.RESET);
                break;
        }
    }

    private void saleVehicle()
    {
        ContractFileManager contractManager = new ContractFileManager();
        processGetAllVehiclesRequest();

        while (true)
        {
            System.out.println();
            System.out.print("Enter the vin number of the Vehicle you would like to Sell: ");
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

            System.out.print("Enter Name: ");
            String customer = userInput.nextLine().strip();

            System.out.print("Enter your Email: ");
            String email = userInput.nextLine().strip();


            System.out.print("Want to Finance (Yes/No): ");
            boolean finance = false;
            try {
                String financed = userInput.nextLine().strip();
                if (financed.equalsIgnoreCase("yes")) {
                    finance = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            double recordFee = SalesContract.recordFee;
            double processingFee = (vehicleSold.getPrice() < 10000) ? 295.00 : 495.00;
            double salesTax = vehicleSold.getPrice() * 0.05;
            double totalPrice = vehicleSold.getPrice() + salesTax + processingFee;

            SalesContract sale = new SalesContract(getCurrentDate(), customer, email, vehicleSold,
                    totalPrice, salesTax, recordFee, processingFee, finance);

            contractManager.saveContract(sale);
            dealership.removeVehicle(vehicleSold);
            System.out.println("CONGRATS WE HAVE SOLD A VEHICLE❗\uFE0F");
            break;
        }
    }

    private void leaseVehicle()
    {
        ContractFileManager contractManager = new ContractFileManager();
        int yearMinusThree = getCurrentYearMinusThree();
        processGetAllVehiclesRequest();

        while (true)
        {
            System.out.println();
            System.out.print("Enter the vin number of the Vehicle you would like to Lease: ");
            int vin = Integer.parseInt(userInput.nextLine().strip());

            Vehicle vehicleLeased = null;

            for (Vehicle v : dealership.getAllVehicles())
            {
                if (v.getVin() == vin)
                {
                    if (v.getYear() > yearMinusThree)
                    {
                        vehicleLeased = v;
                        break;
                    } else
                    {
                        System.out.println("Sorry this Vehicle can not be Leased. \nReason: Car is older than 3 years. ");

                    }
                }
            }
            if (vehicleLeased == null)
            {
                System.out.print("Vin number: " + vin + " not found. \n");
                continue;
            }

            System.out.print("Enter Name: ");
            String customer = userInput.nextLine().strip();

            System.out.print("Enter your Email: ");
            String email = userInput.nextLine().strip();

            double endingValue = 0.05 * vehicleLeased.getPrice();

            double leaseFee = 0.07 * vehicleLeased.getPrice();

            LeaseContract leaseContract = new LeaseContract(getCurrentDate(), customer, email, vehicleLeased, leaseFee, endingValue);

            contractManager.saveContract(leaseContract);
            dealership.removeVehicle(vehicleLeased);
            System.out.println("YAY VEHICLE HAS BEEN LEASED❗\uFE0F");
            break;
        }
    }

    private int getCurrentYearMinusThree()
    {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeYearsAgo = currentDate.minusYears(3);
        return threeYearsAgo.getYear();
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
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                      Search by Price Range");
            System.out.println();
            System.out.println("-".repeat(65));

            System.out.print("Min. Price: ");
            min = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Max. Price: ");
            max = Integer.parseInt(userInput.nextLine().strip());

        }catch(Exception e){
            System.out.println(Colors.RED + "Invalid selection!" + Colors.RESET);
        }

        List<Vehicle> price = dealership.getVehicleByPrice(min, max);
        displayVehicles(price);
    }

    public void processGetByMakeModelRequest(){
        String make = null;
        String model = null;
        try {
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                      Search by Make/Model");
            System.out.println();
            System.out.println("-".repeat(65));

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
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                        Search by Year");
            System.out.println();
            System.out.println("-".repeat(65));

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
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                       \uD83C\uDFA8 Search by Color");
            System.out.println();
            System.out.println("-".repeat(65));

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
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                        Search by Mileage");
            System.out.println();
            System.out.println("-".repeat(65));

            System.out.print("Min. Mileage: ");
            min = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Max. Mileage: ");
            max = Integer.parseInt(userInput.nextLine().strip());
        }catch(Exception e){
            System.out.println(Colors.RED + "invalid selection!" + Colors.RESET);
        }

        List<Vehicle>range = dealership.getVehicleByMileage(min, max);
        displayVehicles(range);
    }

    public void processGetByVehicleTypeRequest()
    {
        String type = null;
        try {
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("              \uD83D\uDEFB Search by Car, SUV, Truck, Van \uD83D\uDE98");
            System.out.println();
            System.out.println("-".repeat(65));

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
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("                        ➕ Add a Vehicle");
            System.out.println();
            System.out.println("-".repeat(65));

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

            System.out.println("\nVehicle added successfully❗\uFE0F ");
        }
        catch (Exception e){
            System.out.println(Colors.RED + "invalid input!" + Colors.RESET);
        }
    }

    public void processRemoveVehicleRequest(){

        while(true){

            try{
                System.out.println();
                System.out.println("-".repeat(65));
                System.out.println();
                System.out.println("                      ➖ Remove a Vehicle");
                System.out.println();
                System.out.println("-".repeat(65));

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
                    System.out.println("\nVehicle removed successfully❗\uFE0F");
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

        System.out.println();
        System.out.println("-".repeat(125));
        System.out.println();
        System.out.println("                                                        All Vehicles");

        System.out.println("\n"+"-".repeat(125));
        System.out.printf("%-15s  %-14s  %-15s  %-15s  %-14s  %-15s  %-13s  %-15s\n",
                "Vin", "Year", "Make", "Model", "Type", "Color", "Odometer", "Price");
        System.out.println("-".repeat(125));
        if(vehicles.isEmpty()){
            System.out.println("Sorry no matches found!");
            return;
        }

        for(Vehicle v : vehicles){
            System.out.println(v);
        }
    }

    private void init()
    {
        fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    public void processGetAllVehiclesRequest()
    {
        vehicleList = dealership.getAllVehicles();
        displayVehicles(vehicleList);
    }
}
