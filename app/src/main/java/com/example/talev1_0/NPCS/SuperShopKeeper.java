package com.example.talev1_0.NPCS;

import com.example.talev1_0.gameItems.abstractClasses.Item;

public class SuperShopKeeper {
    private Item[] shopItems = new Item[4];
    private String shopKeeperName;

    public SuperShopKeeper() {

    }



    public void AddItemToShop(int index, Item item) {
        if (index >= 0 && index < shopItems.length) {
            shopItems[index] = item;
        } else {
            // Handle index out of bounds error
            System.out.println("Index out of bounds");
        }
    }

    public String SellMessage(Item item) {
        String string = "Item Bought: " + item.getName() + " -" + item.getPrice() + "gold";
        return string;
    }

    public Item getShopItems(int i) {
        return shopItems[i];
    }

    public void setShopItems(Item[] shopItems) {
        this.shopItems = shopItems;
    }


    public String getShopKeeperName() {
        return shopKeeperName;
    }

    public void setShopKeeperName(String shopKeeperName) {
        this.shopKeeperName = shopKeeperName;
    }
}
