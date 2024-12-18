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
    private double attackSpeed;
    private int damageDealt;
    private List<Item> lootTable;
    private Factories factories;
    private final List<Item> commonDropTable = new ArrayList<>();
    private final List<Item> rareDropTable = new ArrayList<>();
    private final List<Item> superRareDropTable = new ArrayList<>();

    public BaseMonster(String name, String type, int hp, int level, int damage, int armor) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.damage = damage;
        this.armor = armor;
        this.hp = hp;
        this.maxHp = hp;
        this.xp = level * 3;
        this.attackSpeed = 3.5;
        this.damageDealt = 0;
        this.lootTable = new ArrayList<>(); // Initialize as an empty ArrayList
        this.factories = new Factories();
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
    public double getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public void setAttackSpeed(double amount) {
        this.attackSpeed = amount;
    }

    @Override
    public int getDamageDealt() {
        return damageDealt;
    }

    @Override
    public void setDamageDealt(int amount) {
        this.damageDealt = amount;
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


    public void addCommonDrop(String type, String name, int quantity) {
        commonDropTable.add(factories.createItem(type, name, quantity));

    }

    public void addRareDrop(String type, String name, int quantity) {
        rareDropTable.add(factories.createItem(type, name, quantity));

    }

    public void addSuperRareDrop(String type, String name, int quantity) {
        superRareDropTable.add(factories.createItem(type, name, quantity));

    }

    public List<Item> getCommonDropTable() {
        return commonDropTable;
    }

    public List<Item> getRareDropTable() {
        return rareDropTable;
    }

    public List<Item> getSuperRareDropTable() {
        return superRareDropTable;
    }

    public List<Item> getAllDropTables() {
        List<Item> tempList = new ArrayList<>();

        for (int i = 0; i < getCommonDropTable().size(); i++) {
            if (!getCommonDropTable().get(i).getName().equals("empty")) {
                tempList.add(getCommonDropTable().get(i));

            } else {

            }

        }

        for (int i = 0; i < getRareDropTable().size(); i++) {
            if (!getRareDropTable().get(i).getName().equals("empty")) {
                tempList.add(getRareDropTable().get(i));

            } else {

            }

        }

        for (int i = 0; i < getSuperRareDropTable().size(); i++) {
            if (!getSuperRareDropTable().get(i).getName().equals("empty")) {
                tempList.add(getSuperRareDropTable().get(i));

            } else {

            }

        }
        return tempList;
    }

}
