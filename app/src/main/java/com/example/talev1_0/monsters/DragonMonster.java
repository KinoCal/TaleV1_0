package com.example.talev1_0.monsters;

public class DragonMonster extends BaseMonster implements MonsterInterface{
    public DragonMonster() {
        super("Dragon", "Monster", 1000, 97, 300, 100);

        //Common
        addItemToLootTable("weapon", "Dagger");
        addItemToLootTable("weapon", "Short Sword");
        addItemToLootTable("armor", "Cloth Body");
        addItemToLootTable("consumable", "Hp Potion");
        //Rare
        addItemToLootTable("weapon", "Sword");
        addItemToLootTable("weapon", "Long Sword");
        addItemToLootTable("armor", "Goblin Armor");
        //Epic
        addItemToLootTable("weapon", "Mace");
        //Legendary
        addItemToLootTable("weapon", "God Sword");
    }
}
