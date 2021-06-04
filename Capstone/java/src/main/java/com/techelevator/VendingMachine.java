package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private Map<String, InventoryItem> inventoryItemMap = new TreeMap<>();
    private Transactions customer = new Transactions("0");
    private Transactions vendingMachineProfits = new Transactions("0");
    LocalDate today = LocalDate.now();


    public VendingMachine() {
        File source = new File("inventory.txt");
        try (Scanner userInput = new Scanner(source)) {
            while (userInput.hasNextLine()) {
                String line = userInput.nextLine();
                String[] parts = line.split("\\|");
                InventoryItem item = new InventoryItem(parts[0], parts[1], parts[2], parts[3]);
                inventoryItemMap.put(parts[0], item);

            }
        } catch (FileNotFoundException e) {

        }

    }

    public void purchaseItem(Scanner userInput) {

        if (customer.getBalance().equals("0")) {
            System.out.println("Balance is $0. Please feed money first");
        } else {

            System.out.println("Your current balance is: " + customer.getBalance() + ". What would you like to purchase?");
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

                        // Lower item stock by 1
                        inventoryItemMap.get(desiredItem).changeStock();
                        System.out.println("Your new balance is: " + customer.getBalance());


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
        double amountToReturn = Double.parseDouble(customer.getBalance());

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

                if (amountToReturn != 0) {
                    nickels = (int) Math.floor(amountToReturn / 5);
                }
            }
            customer.subtractFromBalance(customer.getBalance());
            System.out.println("Your total change is: " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickels.");
        } else {
            System.out.println("Balance is $0. No change returned.");
        }

    }


    public void writeLog() {

    }


    public Map<String, InventoryItem> getInventoryItemMap() {
        return inventoryItemMap;
    }

    public void deposit(String deposit) {
        customer.deposit(deposit);
    }


    /*purchase screen
    while (deposit =0  && they select item){
    return "insufficient funds" error - placed back into purchase screen

    getbalance - customerBalance.getbalnce(finalBalance)
    system.out.println("you currently have $"+getbalnce+" to spend. what would you like to purchase?")
    take in desired purchase
    verify if item is available - (stock>0)
    verify sufficient funds - (finalBalance>=itemCost)
        if (both are true){
        balanceAfterPurchase = balance - itemCost
        stock--
        vending machine balance gets updated ***need to create vendMachBalance
        print the correspond message with item
        return to purchase menu

        when returned they can still purchase items. Check for SOLD OUT or insuffients funds

     */
}
