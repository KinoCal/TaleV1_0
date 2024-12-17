package com.example.talev1_0.gameItems.conreteClasses.Consumables;


import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.interfaces.Consumable;

public class ConsumableItem extends Item implements Consumable {
    private int healingValue;

    public ConsumableItem(int itemIndex, String name, String type, String rarity, int price, int healingValue, int quantity) {
        super(itemIndex, name, type, rarity, price, quantity);
        this.healingValue = healingValue;
    }

    @Override
    public int getHealingValue() {
        return healingValue;
    }

    @Override
    public int setHealingValue(int healingValue) {
        return 0;
    }


}
