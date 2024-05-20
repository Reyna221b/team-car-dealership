# Welcome to the Advanced Team Dealership Project 🚗

In this repository you will find the code written in the Java language, this is a follow up of the original Car Dealership project that built a mini console-based application for a dealership. According to these instructions, the application additions are to be built with these specifications in mind:

A Sales and Leasing option is to be added to the original dealership menu. (e.g. SELL/LEASE A VEHICLE)

When the user inputs a sale or a lease, the application will be able to:
- Prompt the user with questions to collect basic sales information from the user
- Add the vehicle information to the contract file
- Ask the user if the transaction is a Sale or a Lease
- Calculate the pricing

~ Workbook 5 Car Dealership V2.0

# Screens
When you initially run the code, the console displays a message to the user letting them know what type of application they are about to use. To make the user experience a bit more personalized, the application starts with asking the user to enter their name:

![Screenshot 2024-05-19 at 6 19 29 PM](https://github.com/Reyna221b/team-car-dealership/assets/99916123/97ae3180-0028-43fc-bced-761f1fb17adc)

# Greeting / Home Screen
The user is then greeted by their name, followed by a display of the Menu screen and the different screen options you can navigate into. The user can input a number from 1 - 10 & 99 to make a selection to go into a next page as shown below:

![Screenshot 2024-05-19 at 6 17 12 PM](https://github.com/Reyna221b/team-car-dealership/assets/99916123/5db5a307-5a5a-478b-a0b8-4db893c59de0)

# Sell or Lease Screen
The user is prompted with two choices to choose from:

![Screenshot 2024-05-19 at 6 42 13 PM](https://github.com/Reyna221b/team-car-dealership/assets/99916123/71ed2c08-78d6-45b6-b2df-83798d6ab39c)

Option 1 will display the current vehicle inventory to the user, followed by a prompt telling the user to enter information about the vehicle they want to sell:

![Screenshot 2024-05-19 at 6 47 10 PM](https://github.com/Reyna221b/team-car-dealership/assets/99916123/353a9b1b-84ff-4489-b3b9-87639801e2e4)

Option 2 will ////// left off here **************************


# Input Errors!
In the case the user enters an input that is invalid, the application will throw an error message as shown below:

![Screenshot 2024-05-19 at 7 14 16 PM](https://github.com/Reyna221b/team-car-dealership/assets/99916123/b002ec87-d07a-4bfb-a262-5831afe497a1)

# Interesting Piece of the Code
one interesting piece of code we added was the Admin UI and Admin Logger. The admin ui can only be displayed if the user enters the correct password! Otherwise they can't view the sensitive information. If they did get the password then everything they do is recorded in the Admin Logger and they are able to view the contract information.

```java
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
```
