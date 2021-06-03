package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private Map<String, InventoryItem> inventoryItemMap = new TreeMap<>();
//    Map<String, Integer> blah = new


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


    public Map<String, InventoryItem> getInventoryItemMap() {
        return inventoryItemMap;
    }


    public void purchaseItem() {
        Scanner userInput = new Scanner(System.in);
        CustomerTransactions customer = new CustomerTransactions();
        System.out.println("Your current balance is: "+ customer.getBalance() Please enter cash: ");
        String deposit = userInput.nextLine();
        VendingMachineProfits vendingMachineProfits = new VendingMachineProfits("0");
//        while ( customer.getBalance().equals("0")){
//            System.out.println("Insufficient Funds please deposit more money");
//            String deposit = userInput.nextLine();
//            customer.deposit(deposit);
//        }
        System.out.println("you currently have $" + customer.getBalance() + " to spend. what would you like to purchase?");
        String desiredItem = userInput.nextLine();
        if (inventoryItemMap.containsKey(desiredItem)) {
            if (inventoryItemMap.get(desiredItem).getStock() != 0) {
                if (Double.parseDouble(customer.getBalance()) > Double.parseDouble(inventoryItemMap.get(desiredItem).getItemPrice())) {
                    customer.makeChange(inventoryItemMap.get(desiredItem).getItemPrice());
                    vendingMachineProfits.deposit(inventoryItemMap.get(desiredItem).getItemPrice());
                    inventoryItemMap.get(desiredItem).changeStock();


                }else {System.out.println("Error! Insufficient Funds!!");}

            }else{System.out.println("All Sold Out Buddy! Come Back Tomorrow!!");}
        }else{System.out.println("Wrong Button Buddy! There's Nothing There!!");}

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
