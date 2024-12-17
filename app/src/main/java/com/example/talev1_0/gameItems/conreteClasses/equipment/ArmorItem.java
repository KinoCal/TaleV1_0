package com.example.talev1_0.gameItems.conreteClasses.equipment;


import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.interfaces.Armor;

public class ArmorItem extends Item implements Armor {
    private int armorValue;

    public ArmorItem(int itemIndex, String name, String type, String rarity, int price, int armorValue, int quantity) {
        super(itemIndex, name, type, rarity, price, quantity);
        this.armorValue = armorValue;
    }

    @Override
    public int getArmorValue() {
        return armorValue;
    }


}
