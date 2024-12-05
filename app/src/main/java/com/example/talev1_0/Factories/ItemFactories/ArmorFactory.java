package com.example.talev1_0.Factories.ItemFactories;

import com.example.talev1_0.Factories.ItemFactory;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.helpers.RarityValues;

public class ArmorFactory implements ItemFactory {

    @Override
    public ArmorItem createItem(String itemName) {
        return switch (itemName) {
            case "empty" -> new ArmorItem(1, "empty", "armor", "empty", 0, 0, 0, 1);
            //Common
            case "ClothBody" -> new ArmorItem(1, "ClothBody", "armor", "common", 2, 1, 1, RarityValues.COMMON);
            //Rare
            case "GoblinArmor" -> new ArmorItem(1, "GoblinArmor", "armor", "rare", 4, 2, 1, RarityValues.RARE);
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
