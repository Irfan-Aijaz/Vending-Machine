package com.techelevator;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {

    private Map<String, InventoryItem> inventoryItemMap = new TreeMap<>();
    private Transactions customer = new Transactions("0");
    private Transactions vendingMachineProfits = new Transactions("0");
    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    Locale currentLocale = Locale.US;
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
    File transactionLog = new File("C:\\Users\\Student\\workspace\\module1-capstone-java-team-6\\Capstone\\java\\AuditFile.txt");
    File salesReport = new File("C:\\Users\\Student\\workspace\\module1-capstone-java-team-6\\Capstone\\java\\SalesReport.txt");


    public VendingMachine() {
        // Reads each line of the inventory
        File source = new File("inventory.txt");
        try (Scanner userInput = new Scanner(source)) {
            while (userInput.hasNextLine()) {
                String line = userInput.nextLine();
                String[] parts = line.split("\\|");

                // Depending on the item type, adds a sound to the item's constructor

                if (parts[3].equals("Candy")) {
                    InventoryItem item = new InventoryItem(parts[0], parts[1], parts[2], parts[3], "Munch Munch, Yum!");
                    inventoryItemMap.put(parts[0], item);
                } else if (parts[3].equals("Chip")) {
                    InventoryItem item = new InventoryItem(parts[0], parts[1], parts[2], parts[3], "Crunch Crunch, Yum!");
                    inventoryItemMap.put(parts[0], item);
                } else if (parts[3].equals("Drink")) {
                    InventoryItem item = new InventoryItem(parts[0], parts[1], parts[2], parts[3], "Glug Glug, Yum!");
                    inventoryItemMap.put(parts[0], item);
                } else if (parts[3].equals("Gum")) {
                    InventoryItem item = new InventoryItem(parts[0], parts[1], parts[2], parts[3], "Chew Chew, Yum!");
                    inventoryItemMap.put(parts[0], item);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory.txt required: " + e);
        }

    }



    public void feedMoney(Scanner userInput) {

        // Adds cash to the customer's balance
        System.out.println("Please enter cash: ");
        String depositAmount = userInput.nextLine();
        customer.deposit(depositAmount);
        System.out.println("Money fed successfully: " + currencyFormatter.format(Double.parseDouble(customer.getBalance())));
        writeLog("FEED MONEY " + currencyFormatter.format(Double.parseDouble(depositAmount)) + " " + currencyFormatter.format(Double.parseDouble(customer.getBalance())));

    }



    public void purchaseItem(Scanner userInput) {

        if (customer.getBalance().equals("0")) {

            // Sends customer back to the menu to ask them to feed in money
            System.out.println("Balance is $0.00 Please feed money first");

        } else {

            System.out.println("Your current balance is: " + currencyFormatter.format(Double.parseDouble(customer.getBalance())) + ". What would you like to purchase?");
            String desiredItem = userInput.nextLine().toUpperCase();

            // Checks the itemMap for the correct button input
            if (inventoryItemMap.containsKey(desiredItem)) {

                // Checks the item for available stock
                if (inventoryItemMap.get(desiredItem).getStock() != 0) {

                    // Checks that the customer's balance is greater than the item price
                    if (Double.parseDouble(customer.getBalance()) > Double.parseDouble(inventoryItemMap.get(desiredItem).getItemPrice())) {

                        // Remove money from the customer's balance
                        customer.subtractFromBalance(inventoryItemMap.get(desiredItem).getItemPrice());

                        // Deposit money into the vending machine
                        vendingMachineProfits.deposit(inventoryItemMap.get(desiredItem).getItemPrice());

                        // Sound of eating the selected item
                        System.out.println(inventoryItemMap.get(desiredItem).getSound());

                        // Lower item stock by 1 and return new balance
                        inventoryItemMap.get(desiredItem).changeStock();
                        System.out.println("Your new balance is: " + currencyFormatter.format(Double.parseDouble(customer.getBalance())));

                        // Add line to audit file
                        String auditLine  = inventoryItemMap.get(desiredItem).getButton() + " " + inventoryItemMap.get(desiredItem).getItemName() +
                                " " + currencyFormatter.format(Double.parseDouble(inventoryItemMap.get(desiredItem).getItemPrice())) +
                                " " + currencyFormatter.format(Double.parseDouble(customer.getBalance()));
                        writeLog(auditLine);


                    } else {
                        System.out.println("Error! Insufficient Funds!!");
                    }

                } else {
                    System.out.println("All Sold Out Buddy! Come Back Tomorrow!!");
                }
            } else {
                System.out.println("Wrong Button Buddy! There's Nothing There!!");
            }
        }

    }

    public void completeTransaction() {
        // Converts the current balance into double to help calculate the change
        // Saves initial balance as amountToReturn to log in audit file
        double amountToReturn = Double.parseDouble(customer.getBalance());
        String auditReturn = currencyFormatter.format(Double.parseDouble(customer.getBalance()));

        // Sets each initial change type to 0
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;

        // If balance is not already 0, starts calculating change
        if (amountToReturn != 0.0) {
            amountToReturn *= 100;

            // Divides the total change into quarters. The change is carried over into dimes
            quarters = (int) Math.floor(amountToReturn / 25);
            amountToReturn %= 25;

            if (amountToReturn != 0) {

                // Divides the total change into dimes. The change is carried over into nickels
                dimes = (int) Math.floor(amountToReturn / 10);
                amountToReturn %= 10;

                // Divides remaing change into nickels.
                if (amountToReturn != 0) {
                    nickels = (int) Math.floor(amountToReturn / 5);
                }
            }

            // Return all the change and set customer's balance to $0.00
            customer.subtractFromBalance(customer.getBalance());
            System.out.println("Your total change is: " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickels.");

        } else {
            // If balance was already $0.00
            System.out.println("Balance is $0.00. No change returned.");
        }

        // Add line to audit file
        String auditLine  = "GIVE CHANGE " + auditReturn + " " + currencyFormatter.format(Double.parseDouble(customer.getBalance()));
        writeLog(auditLine);

    }

    public void writeLog(String auditLine) {

        // Writes the audit file. If it already exists, adds to it
        try (PrintWriter writer = new PrintWriter(new FileWriter(transactionLog,true))){
            LocalDateTime now = LocalDateTime.now();
            String formatDateTime = now.format(formatter);

            writer.println(formatDateTime + " " + auditLine);

        } catch (IOException e){
            System.out.println("Error File not here buddy!");
        }

    }

    public void salesReport() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(salesReport,false))) {
            for (Map.Entry<String, InventoryItem> entry : inventoryItemMap.entrySet()) {
                writer.println(entry.getValue().getItemName() + "|" + (5-entry.getValue().getStock()));
            }

            writer.println("\n**TOTAL SALES** " + currencyFormatter.format(Double.parseDouble(vendingMachineProfits.getBalance())));
            System.out.println("SUPER SECRET SALES REPORT UPDATED");
        } catch (IOException e){
            System.out.println("Error File not here buddy!");
        }
    }


    public Map<String, InventoryItem> getInventoryItemMap() {
        return inventoryItemMap;
    }

}
