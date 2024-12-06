package com.example.talev1_0.monsters;

import com.example.talev1_0.Factories.ItemFactories.Factories;

public class GoblinMonster extends BaseMonster implements MonsterInterface {

    public GoblinMonster() {
        super("Goblin", "Monster", 10, 2, 3, 1);

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
