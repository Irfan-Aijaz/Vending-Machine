package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private Map<String,InventoryItem> inventoryItemMap = new TreeMap<>();
//    Map<String, Integer> blah = new


    public VendingMachine() {
        File source = new File("inventory.txt");
        try (Scanner userInput = new Scanner(source)) {
            while (userInput.hasNextLine()) {
                String line = userInput.nextLine();
                String[] parts = line.split("\\|");
                InventoryItem item = new InventoryItem(parts[0],parts[1], parts[2],parts[3]);
                inventoryItemMap.put(parts[0],item);

            }
        } catch (FileNotFoundException e) {

        }

    }

    public Map<String, InventoryItem> getInventoryItemMap() {
        return inventoryItemMap;
    }
}
