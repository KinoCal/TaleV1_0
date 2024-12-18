package com.example.talev1_0.monsters;

public class DragonMonster extends BaseMonster implements MonsterInterface {
    public DragonMonster() {
        super("Dragon", "Monster", 1000, 97, 300, 100);

        //Common
        addCommonDrop("weapon", "Dagger", 1);
        addCommonDrop("weapon", "Short Sword", 1);
        addCommonDrop("armor", "Cloth Body", 1);
        addCommonDrop("consumable", "Hp Potion", 1);
        //Rare
        addRareDrop("weapon", "Sword", 1);
        addRareDrop("weapon", "Long Sword", 1);
        addRareDrop("armor", "Goblin Armor", 1);
        //Super Rare
        addSuperRareDrop("weapon", "Mace", 1);
        addSuperRareDrop("weapon", "God Sword", 1);
    }
}
