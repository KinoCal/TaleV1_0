package com.example.talev1_0.monsters;

import com.example.talev1_0.Factories.ItemFactories.Factories;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMonster implements MonsterInterface {

    private String name;
    private String type;
    private int level;
    private int damage;
    private int armor;
    private int hp;
    private int maxHp;
    private int xp;
    private List<Item> lootTable;
    private Factories factories;

    public BaseMonster(String name, String type, int hp, int level, int damage, int armor) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.damage = damage;
        this.armor = armor;
        this.hp = hp;
        this.maxHp = hp;
        this.xp = level *3;
        this.lootTable = new ArrayList<>(); // Initialize as an empty ArrayList
        this.factories = new Factories();
    }

    @Override
    public List<Item> getLootTable() {
        return lootTable;
    }

    @Override
    public void addItemToLootTable(String type, String name) {

        lootTable.add(factories.createItem(type,name));
    }

    @Override
    public Item getItemFromLootTable(int index) {
        if (index >= 0 && index < lootTable.size()) {
            return lootTable.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void removeItemFromLootTable(int index) {
        if (index >= 0 && index < lootTable.size()) {
            lootTable.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

@Override
    public String getName() {
        return name;
    }

    @Override
    public void setName() {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType() {

    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int levelValue) {
        level += levelValue;
    }

    @Override
    public int getDamageValue() {
        return damage;
    }

    @Override
    public void setDamageValue(int damageValue) {
        damage += damageValue;
    }

    @Override
    public int getArmorValue() {
        return armor;
    }

    @Override
    public void setArmorValue(int armorValue) {
        armor += armorValue;
    }

    @Override
    public int getHpValue() {
        return hp;
    }

    @Override
    public void setHpValue(int hpValue) {
        hp = hpValue;
    }

    @Override
    public int getMaxHpValue() {
        return maxHp;
    }

    @Override
    public void decreaseHp(int amount) {
        this.hp -= amount;
    }

    @Override
    public int getXpValue() {
        return xp;
    }

    @Override
    public void setXpValue(int xpValue) {
        xp = xpValue;
    }

}
