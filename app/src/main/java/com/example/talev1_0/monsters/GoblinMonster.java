package com.example.talev1_0.monsters;

import com.example.talev1_0.Factories.ItemFactories.Factories;

public class GoblinMonster extends BaseMonster implements MonsterInterface {

    public GoblinMonster() {
        super("Goblin", "Monster", 10, 2, 3, 1);

        //Common
        addCommonDrop("empty", "empty");
        addCommonDrop("empty", "empty");
        addCommonDrop("empty", "empty");
        addCommonDrop("weapon", "Dagger");
        addCommonDrop("weapon", "Short Sword");
        addCommonDrop("armor", "Cloth Body");
        addCommonDrop("consumable", "Hp Potion");
        //Rare
        addRareDrop("empty", "empty");
        addRareDrop("empty", "empty");
        addRareDrop("empty", "empty");
        addRareDrop("consumable", "Big Hp Potion");
        addRareDrop("consumable", "Large Hp Potion");
        //Super Rare
        addSuperRareDrop("empty", "empty");
        addSuperRareDrop("empty", "empty");
        addSuperRareDrop("empty", "empty");
        addSuperRareDrop("weapon", "Mace");
        addSuperRareDrop("weapon", "God Sword");


    }
}
