package com.example.talev1_0.player;
//MAKE A GET INVENTORY FUNCTION THAT DISPLAYS THE PLAYERS INVO INFO TO THE UI UPDATE TEXT AREA
//

import com.example.talev1_0.Factories.ItemFactories.Factories;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;

import com.example.talev1_0.gameItems.interfaces.Item_Empty;
import com.example.talev1_0.gameItems.abstractClasses.Item;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Factories factory = new Factories();
    private WeaponItem currentWeapon;
    private ArmorItem currentArmor;
    private String enemyName;
    private int maxHp;
    private int currentHp;
    private int maxMp;
    private int currentMp;
    private int maxExp;
    private int currentExp;
    private int strengthStat;
    private int defenceStat;
    private int level;
    private int gold;
    private int damage;
    private int armor;
    private boolean playerAlive;
    private boolean inventoryFull;
    private int playerItemIndex;
    private int playerEquipmentIndex;
    private int playerInventoryIndex;
    private int shopItemIndex;
    private int inventorySize;
    public Item empty = new Item_Empty();
    public Item emptyWeapon = new WeaponItem(0,"empty", "weapon", "empty", 0, 0, 1, 0);
    public Item emptyArmour = new ArmorItem(1,"empty", "armor", "empty", 0, 0, 1, 0);


    public Item[] equippedItems = new Item[2];
    public List <Item> inventoryItems = new ArrayList<>();


    public Player() {

        equippedItems[0] = emptyWeapon;
        equippedItems[1] = emptyArmour;


        inventoryItems.add(empty);
        inventoryItems.add(empty);
        inventoryItems.add(empty);
        inventoryItems.add(empty);
        inventoryItems.add(empty);

        currentWeapon = (WeaponItem) equippedItems[0];
        currentArmor = (ArmorItem) equippedItems[1];

        level = 1;
        maxHp = 10;
        currentHp = maxHp;
        maxMp = 10;
        currentMp = maxMp;
        strengthStat = 1;
        defenceStat = 1;
        damage = currentWeapon.getDamageValue();
        armor = currentArmor.getArmorValue();
        currentExp = 0;
        maxExp = level * 10;
        gold = 100;
        inventorySize = 5;
        playerAlive = true;


        setPlayerItemIndex(-1);
        setPlayerInventoryIndex(-1);



    }


    public Item getEquippedItem(int index){
        return equippedItems[index];
    }

    public void setEquippedItems(Item item, int index){
        equippedItems[index] = item;
    }

    public Item getInventoryItem(int index){

        return inventoryItems.get(index);
    }

    public void setInventoryItem(Item item, int index){
        inventoryItems.set(index, item);
    }

    public boolean isInventoryFull(){
        Boolean check = false;
        for (Item item : inventoryItems){
             if (!item.getName().equals("empty")) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }
    public boolean IsPlayerHpZero(){
        boolean isZero = false;
        if (getCurrentHp() == 0){
            System.out.println("player is dead");
            setCurrentHp(0);
            isZero = true;
        }
        return isZero;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int currentMp) {
        this.currentMp += currentMp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void decreaseGold(int gold){this.gold -= gold;}

    public void increaseGold(int gold){this.gold += gold;}

    public boolean isPlayerAlive() {
        return playerAlive;
    }

    public void setPlayerAlive(boolean playerAlive) {
        this.playerAlive = playerAlive;
    }

    public void gainXp(int amount) {
        setCurrentExp(getCurrentExp() + amount);
        if (getCurrentExp() >= getMaxExp()) {
            levelUp();
        }
    }

    private void levelUp() {
        setLevel(getLevel() + 1);
        setCurrentExp(0);
        setMaxExp(getLevel() * 10);

    }

    public void takeDamage(int damage) {
        setCurrentHp(Math.max(0, getCurrentHp() - damage));
        if (getCurrentHp() == 0) {
            setPlayerAlive(false);
        }
    }

    public void healPlayer(int amount) {
        setCurrentHp(Math.min(getMaxHp(), getCurrentHp() + amount));
        if (currentHp > maxHp) {
            setCurrentHp(maxHp);
        }
    }

    public void restoreMp(int amount) {
        setCurrentMp(Math.min(getMaxMp(), getCurrentMp() + amount));

    }




    public int getPlayerItemIndex() {
        return playerItemIndex;
    }



    public void setPlayerItemIndex(int playerItemIndex) {
        this.playerItemIndex = playerItemIndex;
    }




    public boolean setInventoryFull(boolean inventoryFull) {
        return this.inventoryFull = inventoryFull;
    }

    public boolean getInventoryFull() {

        return inventoryFull;
    }



    public int getPlayerEquipmentIndex() {
        return playerEquipmentIndex;
    }



    public void setPlayerEquipmentIndex(int playerEquipmentIndex) {
        this.playerEquipmentIndex = playerEquipmentIndex;
    }



    public WeaponItem getCurrentWeapon() {
        return currentWeapon;
    }



    public void setCurrentWeapon(WeaponItem currentWeapon) {
        this.currentWeapon = currentWeapon;
    }



    public ArmorItem getCurrentArmor() {
        return currentArmor;
    }



    public void setCurrentArmor(ArmorItem currentArmor) {
        this.currentArmor = (ArmorItem) currentArmor;
    }



    public int getDamage() {
        return damage;
    }



    public void setDamage(int damage) {
        this.damage += damage;
    }



    public int getArmor() {
        return armor;
    }



    public void setArmor(int armor) {
        this.armor += armor;
    }





    public int getPlayerInventoryIndex() {
        return playerInventoryIndex;
    }



    public void setPlayerInventoryIndex(int playerInventoryIndex) {
        this.playerInventoryIndex = playerInventoryIndex;
    }



    public int getStrengthStat() {
        return strengthStat;
    }



    public void setStrengthStat(int strengthStat) {
        this.strengthStat = strengthStat;
    }



    public int getDefenceStat() {
        return defenceStat;
    }



    public void setDefenceStat(int defenceStat) {
        this.defenceStat = defenceStat;
    }

    @Override
    public String toString() {
        return "Player{" +
                "currentWeapon=" + currentWeapon.getName() +
                ", currentArmor=" + currentArmor.getName() +
                ", maxHp=" + maxHp +
                ", currentHp=" + currentHp +
                ", maxMp=" + maxMp +
                ", currentMp=" + currentMp +
                ", maxExp=" + maxExp +
                ", currentExp=" + currentExp +
                ", strengthStat=" + strengthStat +
                ", defenceStat=" + defenceStat +
                ", level=" + level +
                ", gold=" + gold +
                ", damage=" + getDamage() +
                ", armor=" + armor +
                ", playerAlive=" + playerAlive +
                ", inventoryFull=" + inventoryFull +
                ", playerItemIndex=" + playerItemIndex +
                ", playerEquipmentIndex=" + playerEquipmentIndex +
                ", playerInventoryIndex=" + playerInventoryIndex +
                '}';
    }


    public int getShopItemIndex() {
        return shopItemIndex;
    }

    public void setShopItemIndex(int shopItemIndex) {
        this.shopItemIndex = shopItemIndex;
    }


    public boolean isInventoryIndexEmpty(int index){
        Boolean isEmpty;
        if (inventoryItems.get(index).getName().isEmpty()){
            isEmpty = true;
        }else {
            isEmpty = false;
        }
        return isEmpty;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

}

