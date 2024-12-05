package com.example.talev1_0.handlers;


import com.example.talev1_0.player.PlayerViewModel;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.Consumables.ConsumableItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.player.Player;

public class InventoryManager {

	public void GivePlayerItem(PlayerViewModel player, Item item) {

		// First pass: check for an existing item with the same name
		for (Item tempItem : player.getInventoryItems()){

		if (!player.isInventoryFull() || tempItem.getName().equals(item.getName())) {
			for (int i = 0; i < 5; i++) {
				if (player.getInventoryItems()[i].getName().equals(item.getName())) {
					System.out.println("attempting to give player item..1 " + item.getName());
					player.getInventoryItems()[i].increaseQuantity(1);
					System.out.println("item given to player 1 " + item.getName());
					break;

				} else if (player.getInventoryItems()[i].getItemIndex() == 9) {
					System.out.println("attempting to give player item..2 " + item.getName());
					player.getInventoryItems()[i] = item; // Add the item to the empty slot
					System.out.println("item given to player 2 " + item.getName());
					break;
					 // Exit the loop after placing the item
				}
			}

		} else {
			System.out.println("sorry player inventory full");
			System.out.println("attempted to give player item: " + item.getName());
		}
			break;
		}
	}

	public boolean BuyItem(PlayerViewModel player, Item item) {
		boolean itemSold = false;

		// Check if the player has enough gold to buy item
		if (player.getPlayerGold() >= item.getPrice()){

		// First pass: check for an existing item with the same name
		for (int i = 0; i < 5; i++) {
			if (player.getInventoryItems()[i].getName().equals(item.getName())) {
				System.out.println("attempting to buy item..1");
				player.getInventoryItems()[i].increaseQuantity(1);
				player.decreaseGold(item.getPrice());
				itemSold = true;
				System.out.println("item sold " + item.getName());
				return itemSold; // Exit the method after increasing the quantity
			}
		}

		// Second pass: find the first empty slot to add the item
		for (int i = 0; i < 5; i++) {
			if (player.getInventoryItems()[i].getItemIndex() == 9) {
				System.out.println("attempting to buy item..2");
				player.getInventoryItems()[i] = item; // Add the item to the empty slot
				player.decreaseGold(item.getPrice());
				itemSold = true;
				System.out.println("item sold " + item.getName());
				break; // Exit the loop after placing the item
			}
		}


		} else {
			itemSold = false;
		}

		return itemSold;
	}




	public boolean SellItemToShop(PlayerViewModel player, Item item) {

		boolean itemSold;

		if(player.getPlayerItemIndex() >=0 || player.getPlayerItemIndex() <=4) {

			if (player.getInventoryItems()[player.getPlayerItemIndex()].getQuantity() < 2){

				System.out.println("Player sold: " + item.getName() + " for " + item.getPrice() + " Gold");
				player.getInventoryItems()[player.getPlayerItemIndex()] = player.getEmptyItem();
				player.increaseGold(item.getPrice());
				itemSold = true;

			}
			else {

				System.out.println("Player sold: " + item.getName() + " for " + item.getPrice() + " Gold");
				player.getInventoryItems()[player.getPlayerItemIndex()].increaseQuantity(-1);
				player.increaseGold(item.getPrice());
				itemSold = true;

			}


		}
		else {
			itemSold = false;
			System.out.println("item not sold to shop@@@@");
		}
		return itemSold;
	}

	public void useItem(int slotNumber, PlayerViewModel playerViewModel) {
		Item currentItem = playerViewModel.getInventoryItemAtIndex(slotNumber);

		if (currentItem instanceof ConsumableItem consumableItem) {
			// Heal the player
			playerViewModel.healPlayer(consumableItem.getHealingValue());
			System.out.println("Player healed for: " + consumableItem.getHealingValue());
			System.out.println("Player used item: " + consumableItem.getName());

			if (currentItem.getQuantity() > 1) {
				currentItem.increaseQuantity(-1);
			} else {
				// Replace with an empty item
				playerViewModel.getInventoryItems()[slotNumber] = playerViewModel.getEmptyItem();
			}
			// Trigger LiveData update
			playerViewModel.updatePlayerLiveData();
		}
	}


	public void equipItem(int slotNumber, PlayerViewModel player) {
		// Check if the slotNumber is valid
		if (slotNumber >= 0 && slotNumber < 2) {
			//set the item used = to a temporary currentItem variable
			Item currentItem = player.getInventoryItems()[player.getPlayerItemIndex()];

			System.out.println(currentItem.getName());
			if (player.getInventoryItems()[player.getPlayerItemIndex()].getName().equals(player.getEquippedItems()[currentItem.getItemIndex()].getName())){

				System.out.println("same item equipped");

			}

			else if(!currentItem.getName().equals(player.getEquippedItems()[currentItem.getItemIndex()].getName())){

				System.out.println("test1");
				if (currentItem.getQuantity() > 1){
					System.out.println("test2");
					currentItem.increaseQuantity(-1);
					UnEquipItem(player,player.getEquippedItems()[currentItem.getItemIndex()]);
					player.getEquippedItems()[currentItem.getItemIndex()] = currentItem;

				} else if (currentItem.getQuantity() < 2) {

					System.out.println("test3");
					player.getInventoryItems()[player.getPlayerItemIndex()] = player.getEmptyItem();
					UnEquipItem(player,player.getEquippedItems()[currentItem.getItemIndex()]);
					System.out.println("items name" + player.getEquippedItems()[currentItem.getItemIndex()].getName());
					player.getEquippedItems()[currentItem.getItemIndex()] = currentItem;

				}

			}
			System.out.println("test4");

			player.setCurrentWeapon((WeaponItem) player.getEquippedItems()[0]);
			player.setCurrentArmor((ArmorItem) player.getEquippedItems()[1]);
			player.setPlayerDamage(player.getCurrentWeapon().getDamageValue());
			player.setPlayerArmorValue(player.getCurrentArmor().getArmorValue());

		}
		else {
			System.out.println("No item found*");
		}
	}




	public void UnEquipItem(PlayerViewModel player, Item item) {


		for (int i = 0; i < player.getInventoryItems().length; i++){
			if (player.getInventoryItems()[i].getName().equals(item.getName()) &&
					player.getInventoryItems()[i].getType().equals(item.getType())){

				if (item instanceof WeaponItem){
					player.getEquippedItems()[item.getItemIndex()] = player.getEmptyWeaponItem();
					player.getInventoryItems()[i].increaseQuantity(1);

				}
				if (item instanceof ArmorItem){
					player.getEquippedItems()[item.getItemIndex()] = player.getEmptyArmorItem();
					player.getInventoryItems()[i].increaseQuantity(1);
				}
			} else if (player.getInventoryItems()[i] == player.getEmptyItem()) {

				if (item.getName().equals("empty") && item.getType().equals("weapon")){
					player.getEquippedItems()[item.getItemIndex()] = player.getEmptyWeaponItem();
					player.getInventoryItems()[i] = player.getEmptyItem();

				}
				if (item.getName().equals("empty") && item.getType().equals("armor")){
					player.getEquippedItems()[item.getItemIndex()] = player.getEmptyArmorItem();
					player.getInventoryItems()[i] = player.getEmptyItem();
				}
			}
		}

		int slotNumber = 0;
		while(!player.getInventoryItems()[slotNumber].getName().equals("empty") && slotNumber <4) {
			slotNumber++;
		}
		if(player.getInventoryItems()[slotNumber].getName().equals("empty")) {
			if(player.getEquippedItems()[item.getItemIndex()]==player.getEmptyWeaponItem() || player.getEquippedItems()[item.getItemIndex()]==player.getEmptyArmorItem()){

				player.getInventoryItems()[slotNumber] = player.getEmptyItem();
			}else {
				player.getInventoryItems()[slotNumber] = player.getEquippedItems()[item.getItemIndex()];

			}

			if (item instanceof WeaponItem){
				player.getEquippedItems()[item.getItemIndex()] = player.getEmptyWeaponItem();

			}
			if (item instanceof ArmorItem){
				player.getEquippedItems()[item.getItemIndex()] = player.getEmptyArmorItem();
			}

			System.out.println("test000");
			System.out.println(item.getName());
			System.out.println(player.getEquippedItems()[0].getName());
			System.out.println(player.getEquippedItems()[1].getName());

			player.setCurrentWeapon((WeaponItem) player.getEquippedItems()[0]);
			player.setCurrentArmor((ArmorItem) player.getEquippedItems()[1]);
			player.setPlayerDamage(player.getCurrentWeapon().getDamageValue());
			player.setPlayerArmorValue(player.getCurrentArmor().getArmorValue());

		}
		else if(!player.getInventoryItems()[slotNumber].getName().equals("empty")) {
			System.out.println("player inventory is full test");
		}
	}


	public String OutOfGoldMessage() {
		String string = "Sorry not enough gold...";
		return string;
	}
}