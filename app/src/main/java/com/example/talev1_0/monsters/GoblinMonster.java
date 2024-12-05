package com.example.talev1_0.monsters;

import com.example.talev1_0.Factories.ItemFactories.Factories;

public class GoblinMonster extends BaseMonster implements MonsterInterface {

    Factories factories = new Factories();

    public GoblinMonster() {
        super("Goblin", "Monster", 2, 2, 1, 10, 6);

        //Common
        addItemToLootTable(factories.createItem("weapon", "Dagger"));
        addItemToLootTable(factories.createItem("weapon", "ShortSword"));
        addItemToLootTable(factories.createItem("armor", "ClothBody"));
        addItemToLootTable(factories.createItem("consumable", "Hp Potion"));
        //Rare
        addItemToLootTable(factories.createItem("weapon", "Sword"));
        addItemToLootTable(factories.createItem("weapon", "LongSword"));
        addItemToLootTable(factories.createItem("armor", "GoblinArmor"));
        //Epic
        addItemToLootTable(factories.createItem("weapon", "Mace"));
        //Legendary
        addItemToLootTable(factories.createItem("weapon", "GodSword"));

    }
}
