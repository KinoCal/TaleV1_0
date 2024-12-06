package com.example.talev1_0.monsters;

import com.example.talev1_0.gameItems.abstractClasses.Item;

import java.util.ArrayList;
import java.util.List;

public interface MonsterInterface {

    public String getName();
    public void setName();
    public String getType();
    public void setType();
    public int getLevel();
    public void setLevel(int level);
    public int getDamageValue();
    public void setDamageValue(int damageValue);
    public int getArmorValue();
    public void setArmorValue(int armorValue);
    public int getHpValue();
    public void setHpValue(int hpValue);
    public int getMaxHpValue();
    public void decreaseHp(int amount);
    public int getXpValue();
    public void setXpValue(int xpValue);
    public List<Item> getLootTable();
    public void addItemToLootTable(String type, String name);
    public Item getItemFromLootTable(int index);

}
