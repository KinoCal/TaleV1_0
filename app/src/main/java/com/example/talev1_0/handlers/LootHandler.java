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

    public Item determinePlayerLoot(MonsterViewModel monster){

        String itemRarity;
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        if (randomNumber < RarityValues.COMMON){
            System.out.println(randomNumber);
            itemRarity = "common";
                return rollForLoot(monster, itemRarity);

        } else if (randomNumber < RarityValues.RARE) {
            System.out.println(randomNumber);
            itemRarity = "rare";
                return rollForLoot(monster, itemRarity);

        } else if (randomNumber < RarityValues.EPIC) {
            System.out.println(randomNumber);
            itemRarity = "epic";
                return rollForLoot(monster, itemRarity);

        } else if (randomNumber < RarityValues.LEGENDARY) {
            System.out.println(randomNumber);
            itemRarity = "legendary";
                return rollForLoot(monster, itemRarity);

        }else {
            System.out.println(randomNumber);
            System.out.println("error in determineplayerloot logic");
        }
        return null;
    }

    public Item rollForLoot(MonsterViewModel monster, String itemRarity) {
        // Initialize tempList
        List<Item> tempList = new ArrayList<>();

        for (Item item : monster.getLootTable()) {
            if (item.getRarityName().equals(itemRarity)) {
                tempList.add(item); // Add item to the tempList

            }
        }

        int index = 1;
        for (Item item1 : tempList){
            System.out.print(index++ + ": ");
            System.out.println(item1.getName() + " temp list test");
        }

        // Roll randomly within the tempList
        if (!tempList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(tempList.size());
            return tempList.get(randomIndex);
        }

        return null; // No items match the rolledNumber
    }
}
