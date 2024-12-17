package com.example.talev1_0.handlers;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.helpers.RarityValues;
import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.player.MonsterViewModel;
import com.example.talev1_0.player.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootHandler {

    final Random random = new Random();

    public Item rollForLoot(MonsterViewModel monster) {
        int roll = random.nextInt(555); // Roll a number between 0 and 554

        if (roll < 500) {
            System.out.println("Player rolled a: " + roll + " ON THE DROP TABLE");
            // Common loot
            return rollForLoot(monster.getCommonDropTable());

        } else if (roll < 550) {
            System.out.println("Player rolled a: " + roll + " ON THE DROP TABLE");
            // Rare loot
            return rollForLoot(monster.getRareDropTable());
        } else if (roll <= 555) {
            System.out.println("Player rolled a: " + roll + " ON THE DROP TABLE");
            // Super rare loot
            return rollForLoot(monster.getSuperRareDropTable());
        } else {
            System.out.println("error in rollforloot logic***");
            return null;
        }

    }

    private Item rollForLoot(List<Item> dropTable) {
        if (dropTable.isEmpty()) {
            return null; // No items in this drop table
        }
        int index = random.nextInt(dropTable.size()); // Random index from 0 to size-1
        return dropTable.get(index);
    }

}
