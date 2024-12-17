package com.example.talev1_0.monsters;

public class DragonMonster extends BaseMonster implements MonsterInterface {
    public DragonMonster() {
        super("Dragon", "Monster", 1000, 97, 300, 100);

        //Common
        addCommonDrop("weapon", "Dagger");
        addCommonDrop("weapon", "Short Sword");
        addCommonDrop("armor", "Cloth Body");
        addCommonDrop("consumable", "Hp Potion");
        //Rare
        addRareDrop("weapon", "Sword");
        addRareDrop("weapon", "Long Sword");
        addRareDrop("armor", "Goblin Armor");
        //Super Rare
        addSuperRareDrop("weapon", "Mace");
        addSuperRareDrop("weapon", "God Sword");
    }
}
