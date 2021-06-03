package com.techelevator;

import com.techelevator.view.MenuDrivenCLI;

import java.util.Map;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	private final MenuDrivenCLI ui = new MenuDrivenCLI();

	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}

	public void run() {
		VendingMachine currentInven = new VendingMachine();
		while (true) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);

			if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items

				DisplayMenu(currentInven);


			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				currentInven.purchaseItem();

			}
		}
	}

	private void DisplayMenu(VendingMachine currentInven) {
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


}
