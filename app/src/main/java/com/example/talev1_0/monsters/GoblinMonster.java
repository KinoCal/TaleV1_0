package com.example.talev1_0.monsters;

import com.example.talev1_0.Factories.ItemFactories.Factories;

public class GoblinMonster extends BaseMonster implements MonsterInterface {

    public GoblinMonster() {
        super("Goblin", "Monster", 10, 2, 3, 1);

        //Common
        addCommonDrop("empty", "empty", 1);
        addCommonDrop("empty", "empty", 1);
        addCommonDrop("empty", "empty", 1);
        addCommonDrop("weapon", "Dagger", 1);
        addCommonDrop("weapon", "Short Sword", 1);
        addCommonDrop("armor", "Cloth Body", 1);
        addCommonDrop("consumable", "Hp Potion", 1);
        //Rare
        addRareDrop("empty", "empty", 1);
        addRareDrop("empty", "empty", 1);
        addRareDrop("empty", "empty", 1);
        addRareDrop("consumable", "Big Hp Potion", 1);
        addRareDrop("consumable", "Large Hp Potion", 1);
        //Super Rare
        addSuperRareDrop("empty", "empty", 1);
        addSuperRareDrop("empty", "empty", 1);
        addSuperRareDrop("empty", "empty", 1);
        addSuperRareDrop("weapon", "Mace", 1);
        addSuperRareDrop("weapon", "God Sword", 1);


    }
}
