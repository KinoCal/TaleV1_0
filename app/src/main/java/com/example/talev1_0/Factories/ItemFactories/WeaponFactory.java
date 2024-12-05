package com.example.talev1_0.Factories.ItemFactories;

import com.example.talev1_0.Factories.ItemFactory;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.helpers.RarityValues;


public class WeaponFactory implements ItemFactory {

    @Override
    public WeaponItem createItem(String itemName) {
        return switch (itemName) {
            case "empty" -> new WeaponItem(0, "empty", "weapon", "empty", 0, 0, 0, 0);
            //Common
            case "Dagger" -> new WeaponItem(0, "Dagger", "weapon", "common", 2, 1, 1, RarityValues.COMMON);
            case "ShortSword" -> new WeaponItem(0, "ShortSword", "weapon", "common", 3, 2, 1, RarityValues.COMMON);
            //Rare
            case "Sword" -> new WeaponItem(0, "Sword", "weapon", "rare", 5, 3, 1, RarityValues.RARE);
            case "LongSword" -> new WeaponItem(0, "LongSword", "weapon", "rare", 7, 4, 1, RarityValues.RARE);
            //Epic
            case "Mace" -> new WeaponItem(0, "mace", "weapon", "epic", 10, 6, 1, RarityValues.EPIC);
            //Legendary
            case "GodSword" -> new WeaponItem(0, "GodSword", "weapon", "legendary", 22, 20, 1, RarityValues.LEGENDARY);
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
