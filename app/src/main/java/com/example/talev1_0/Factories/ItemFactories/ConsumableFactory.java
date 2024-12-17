package com.example.talev1_0.Factories.ItemFactories;


import com.example.talev1_0.Factories.ItemFactory;
import com.example.talev1_0.gameItems.conreteClasses.Consumables.ConsumableItem;
import com.example.talev1_0.helpers.RarityValues;

public class ConsumableFactory implements ItemFactory {

    @Override
    public ConsumableItem createItem(String itemName) {
        return switch (itemName) {
            case "Hp Potion" -> new ConsumableItem(8, "Hp Potion", "consumable", "common", 2, 3, 1);
            case "Big Hp Potion" ->
                    new ConsumableItem(8, "Big Hp Potion", "consumable", "rare", 15, 10, 1);
            case "Large Hp Potion" ->
                    new ConsumableItem(8, "Large Hp Potion", "consumable", "epic", 50, 35, 1);
            default -> throw new IllegalArgumentException("Unknown item type: " + itemName);
        };
    }
}
