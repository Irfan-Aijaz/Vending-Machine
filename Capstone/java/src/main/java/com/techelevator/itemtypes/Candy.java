package com.techelevator.itemtypes;

import com.techelevator.InventoryItem;
import com.techelevator.ItemSound;

public class Candy extends InventoryItem implements ItemSound {
    public Candy(String button, String itemName, String itemPrice, String itemType) {
        super(button, itemName, itemPrice, itemType);
    }


    @Override
    public String getSound() {
        return "Chew, Chew, Yum!";
    }
}
