package com.example.talev1_0.Factories.ItemFactories;

import com.example.talev1_0.Factories.ItemFactory;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.helpers.RarityValues;


public class WeaponFactory implements ItemFactory {

    @Override
    public WeaponItem createItem(String itemName) {
        return switch (itemName) {
            case "empty" -> new WeaponItem(0, "empty", "weapon", "empty", 0, 0, 0);
            //Common
            case "Dagger" -> new WeaponItem(0, "Dagger", "weapon", "common", 2, 1, 1);
            case "Short Sword" -> new WeaponItem(0, "Short Sword", "weapon", "common", 3, 2, 1);
            //Rare
            case "Sword" -> new WeaponItem(0, "Sword", "weapon", "rare", 5, 6, 1);
            case "Long Sword" -> new WeaponItem(0, "Long Sword", "weapon", "rare", 7, 4, 1);
            //Epic
            case "Mace" -> new WeaponItem(0, "Mace", "weapon", "epic", 10, 6, 1);
            //Legendary
            case "God Sword" -> new WeaponItem(0, "God Sword", "weapon", "legendary", 22, 20, 1);
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
