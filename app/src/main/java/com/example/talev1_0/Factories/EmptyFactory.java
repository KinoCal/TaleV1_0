package com.example.talev1_0.Factories;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.gameItems.interfaces.Item_Empty;

public class EmptyFactory implements ItemFactory {

    @Override
    public Item createItem(String itemName, int quantity) {
        return switch (itemName) {
            case "empty" -> new Item_Empty();
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
