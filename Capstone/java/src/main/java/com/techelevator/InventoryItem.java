package com.techelevator;

public class InventoryItem {
    private String button;
    private String itemName;
    private String itemPrice;
    private String itemType;
    private int stock;
    private String sound;

    public InventoryItem(String button, String itemName, String itemPrice, String itemType, String sound) {
        this.button = button;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.stock = 5;
        this.sound = sound;
    }





    public void changeStock(){
        stock--;

    }

    public int getStock(){
        return stock;
    }

    public String getSound(){
        return sound;
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
