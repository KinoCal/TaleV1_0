package com.example.talev1_0.gameItems.conreteClasses.equipment;


import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.interfaces.Weapon;

public class WeaponItem extends Item implements Weapon {
    private int damageValue;

    public WeaponItem(int itemIndex, String name, String type, String rarity, int price, int damageValue, int quantity) {
        super(itemIndex, name, type, rarity, price, quantity);
        this.damageValue = damageValue;
    }

    @Override
    public int getDamageValue() {
        return damageValue;
    }

    @Override
    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }


}
