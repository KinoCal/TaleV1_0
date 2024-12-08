package com.example.talev1_0.handlers;

import android.view.Gravity;
import android.view.View;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.helpers.SnackbarHelper;
import com.example.talev1_0.player.MonsterViewModel;
import com.example.talev1_0.player.PlayerViewModel;

public class FightHandler {

    InventoryManager inventoryManager;
    LootHandler lootHandler;
    Item currentLoot;
    SnackbarHelper snackbarHelper;

    public boolean attackMonster(View rootView, PlayerViewModel player, MonsterViewModel monster){

        boolean isMonsterAlive = true;
        int damage = player.getPlayer().calculateDamageDealt(monster.getMonster().getValue());
        player.setDamageDealt(damage);

        if (monster.getMonsterHp() > 0 ) {
            monster.decreaseMonsterHp(damage);


            if (monster.getMonsterHp() <= 0) {
                isMonsterAlive = false;
                snackbarHelper = new SnackbarHelper(rootView);
                inventoryManager = new InventoryManager();
                lootHandler = new LootHandler();
                currentLoot = lootHandler.determinePlayerLoot(monster);
                inventoryManager.GivePlayerItem(player, currentLoot);
                player.gainXp(monster.getMonsterXp());
                player.increaseGold(monster.getMonsterLevel());
                monster.setMonsterHp(monster.getMonsterMaxHp());

                if (player.isInventoryFull()){
                    snackbarHelper.show("Monster defeated! +" + monster.getMonsterXp() + " XP\n" + "Sorry, inventory is full. Please make space!");

                }else {
                    snackbarHelper.show("Monster defeated! +" + monster.getMonsterXp() + " XP\nLoot received: " + currentLoot.getName());

                }
            }
        }
        return isMonsterAlive;
    }

    public boolean attackPlayer(PlayerViewModel player, MonsterViewModel monster){
        int damage = monster.getMonsterDamage();
        monster.setDamageDealt(damage);
        boolean isPlayerAlive = true;

        if (player.getCurrentHp() > 0){
            player.reduceHp(damage);

            if (player.getCurrentHp() <=0 ){
                player.setCurrentHp(1);
                isPlayerAlive = false;
            }
        }
        return isPlayerAlive;
    }



}
