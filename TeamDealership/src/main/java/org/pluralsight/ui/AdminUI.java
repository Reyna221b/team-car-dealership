package org.pluralsight.ui;

import org.pluralsight.models.Colors;
import org.pluralsight.models.Contract;

import org.pluralsight.models.LeaseContract;
import org.pluralsight.models.SalesContract;
import org.pluralsight.services.AdminLogger;
import org.pluralsight.services.ContractFileManager;
import java.util.List;
import java.util.Scanner;


public class AdminUI
{

    static final Scanner userInput = new Scanner(System.in);


    private ContractFileManager contractFile = new ContractFileManager();
    private List<Contract> allContracts = contractFile.getAllContracts();
    private final String adminPassword = "easyPassword";

    public void adminLogin(){
        boolean isNotLoggedIn = true;

        while(isNotLoggedIn){

            System.out.print("\nPlease Enter Admin Password: ");
            String password = userInput.nextLine().strip();
            AdminLogger.logMessage("Admin selected","Entering password");

            if (password.equalsIgnoreCase("X")) {
                System.out.println("Exiting...");
                AdminLogger.logMessage("Admin selected","pressed X to Exit");
                break;
            } else if (password.equals(adminPassword)) {
                AdminLogger.logMessage("Admin selected","Entered correct Password");
                System.out.println();
                adminDisplayMenu();
                isNotLoggedIn = false;
            } else {
                System.out.println("Incorrect Password: try again. Or press X to exit: ");
                AdminLogger.logMessage("Admin selected","Entered Incorrect Password");
            }
        }
    }

    private void adminDisplayMenu(){
        AdminLogger.logMessage("Admin Menu","In admin menu");
        boolean exitAdminMenu = false;
        while (!exitAdminMenu) {

            System.out.println("\n\t\t                  Admin Screen");
            System.out.println();
            System.out.println("-".repeat(65));
            System.out.println();
            System.out.println("[1] - List All Contracts");
            System.out.println("[2] - List All Sales Contracts");
            System.out.println("[3] - List All Leased Contracts");
            System.out.println("[4] - List First 5 Contracts");
            System.out.println("[5] - List Last 10 Contracts");
            System.out.println(Colors.PURPLE + "[6] - Back Home Screen" + Colors.RESET);
            System.out.println();
            System.out.println(Colors.CYAN + "-".repeat(65));
            System.out.print(Colors.RESET);
            System.out.print(Colors.CYAN + "Enter your choice: ");
            int choice;

            try {
                choice = Integer.parseInt(userInput.nextLine().strip());

                switch (choice) {
                    case 1:
                        AdminLogger.logMessage("Admin Menu","Displayed all contracts");
                        displayAllContracts();
                        break;
                    case 2:
                        displaySalesContracts(allContracts);
                        AdminLogger.logMessage("Admin Menu","Displayed all Sales contracts");
                        break;
                    case 3:
                        displayLeaseContracts(allContracts);
                        AdminLogger.logMessage("Admin Menu","Displayed all Leased contracts");
                        break;
                    case 4:
                        listFirst5Contracts(allContracts);
                        AdminLogger.logMessage("Admin Menu","Displayed first 5 contracts");
                        break;
                    case 5:
                        AdminLogger.logMessage("Admin Menu","Displayed Last 10 contracts");
                        listLast10Contract(allContracts);
                        break;
                    case 6:
                        AdminLogger.logMessage("Leaving Admin Menu","exited Admin menu");
                        System.out.println("\nBack to Home Screen!");
                        exitAdminMenu = true;
                        return;
                    default:
                        System.out.println("Invalid input. Please enter a valid option.");
                        adminDisplayMenu();
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid option.");
                userInput.next();
                adminDisplayMenu();
            }
        }
    }

    public void displaySalesContracts(List<Contract> contracts) {
        System.out.println("\nSales Contracts:");
        for (Contract contract : contracts) {
            if (contract instanceof SalesContract) {
                System.out.println(contract);
            }
        }
    }

    public void displayLeaseContracts(List<Contract> contracts) {
        System.out.println("\nLease Contracts:");
        for (Contract contract : contracts) {
            if (contract instanceof LeaseContract) {
                System.out.println(contract);
            }
        }
    }

    private void displayAllContracts()
    {
        System.out.println();
        System.out.println("-".repeat(160));
        System.out.println();
        System.out.println("                                                        All Contracts");

        System.out.println("\n"+"-".repeat(160));
        for(Contract c : allContracts){
            System.out.println(c);
        }
        if(allContracts.isEmpty()){
            System.out.println("Sorry no contracts");

        }
        System.out.println("-".repeat(160));

    }

    private void listFirst5Contracts(List<Contract> contracts){

        System.out.println();
        System.out.println("-".repeat(220));
        System.out.println();
        System.out.println("                                                        First 5 Contracts");

        System.out.println("\n"+"-".repeat(220));

        int totalContracts = contracts.size();

        System.out.println("First 5 contracts:");
        for (int i = 0; i < Math.min(5, totalContracts); i++) {
            System.out.println(contracts.get(i));
        }
        System.out.println("-".repeat(220));
    }

    private void listLast10Contract(List<Contract>contracts)
    {

        System.out.println();
        System.out.println("-".repeat(220));
        System.out.println();
        System.out.println("                                                        Last 10 Contracts");

        System.out.println("\n"+"-".repeat(220));
        int totalContracts = contracts.size();

        System.out.println("\nLast ten contracts:");
        if (totalContracts > 10) {
            for (int i = totalContracts - 10; i < totalContracts; i++) {
                System.out.println(contracts.get(i));
            }
        } else {
            for (int i = 0; i < totalContracts; i++) {
                System.out.println(contracts.get(i));
            }
        }
        System.out.println("-".repeat(220));
    }

}
