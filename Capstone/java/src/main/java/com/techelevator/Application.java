package com.techelevator;

import com.techelevator.view.MenuDrivenCLI;

import java.util.Map;
import java.util.Scanner;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_ITEMS_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_ITEMS_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_ITEMS_MENU_COMPLETE_TRANSACTION = "Complete Transaction";
	private static final String[] PURCHASE_ITEMS_MENU_OPTIONS = {PURCHASE_ITEMS_MENU_FEED_MONEY, PURCHASE_ITEMS_MENU_SELECT_PRODUCT, PURCHASE_ITEMS_MENU_COMPLETE_TRANSACTION};

	private final MenuDrivenCLI ui = new MenuDrivenCLI();
	VendingMachine currentInven = new VendingMachine();
	Scanner userInput = new Scanner(System.in);


	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}

	public void run() {


		while (true) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);

			if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

				// display vending machine items

				DisplayItemsMenu(currentInven);

			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {

				// do purchase

				DisplayPurchaseItemsMenu();

			} else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {

				// Exits the vending machine
				break;
			}
		}
	}


	private void DisplayItemsMenu(VendingMachine currentInven) {
		// Loop through item map, printing each button, name, price, and if its sold out
		Map<String, InventoryItem> currentInventoryMap = currentInven.getInventoryItemMap();
		for(Map.Entry<String,InventoryItem> entry : currentInventoryMap.entrySet()){
			System.out.print(entry.getKey() + " " + entry.getValue().getItemName() +
					" $" + entry.getValue().getItemPrice());
			if (entry.getValue().getStock()==0) {
				System.out.println(" SOLD OUT");
			} else {
				System.out.println("");
			}
		}
	}

	private void DisplayPurchaseItemsMenu(){
		while (true) {
			String selection = ui.promptForSelection(PURCHASE_ITEMS_MENU_OPTIONS);

			if (selection.equals(PURCHASE_ITEMS_MENU_FEED_MONEY)) {

				// Calls feedMoney();

				feedMoney();

			} else if (selection.equals(PURCHASE_ITEMS_MENU_SELECT_PRODUCT)) {

				// Calls the item to be purchased, based on user's input

				currentInven.purchaseItem(userInput);

			} else if (selection.equals(PURCHASE_ITEMS_MENU_COMPLETE_TRANSACTION)) {

				// Returns change and returns to the previous menu

				currentInven.completeTransaction();

				break;
			}
		}

	}

	public void feedMoney() {
		// Adds cash to the customer's balance
		System.out.println("Please enter cash: ");
		String deposit = userInput.nextLine();
		currentInven.deposit(deposit);
		System.out.println("Money fed successfully.");
	}


}
