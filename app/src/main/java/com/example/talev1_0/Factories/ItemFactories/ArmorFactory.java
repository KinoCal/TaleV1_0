package com.example.talev1_0.Factories.ItemFactories;

import com.example.talev1_0.Factories.ItemFactory;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.helpers.RarityValues;

public class ArmorFactory implements ItemFactory {

    @Override
    public ArmorItem createItem(String itemName) {
        return switch (itemName) {
            case "empty" -> new ArmorItem(1, "empty", "armor", "empty", 0, 0, 0);
            //Common
            case "Cloth Body" -> new ArmorItem(1, "Cloth Body", "armor", "common", 2, 1, 1);
            //Rare
            case "Goblin Armor" -> new ArmorItem(1, "Goblin Armor", "armor", "rare", 4, 2, 1);
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
