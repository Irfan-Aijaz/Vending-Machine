package com.techelevator;

public class InventoryItem {
    private String button;
    private String itemName;
    private String itemPrice;
    private String itemType;
    private int stock;

    public InventoryItem(String button, String itemName, String itemPrice, String itemType) {
        this.button = button;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.stock = 5;
    }
    public int getStock(){
        return stock;
    }

    public String getButton() {
        return button;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemType() {
        return itemType;
    }


}
