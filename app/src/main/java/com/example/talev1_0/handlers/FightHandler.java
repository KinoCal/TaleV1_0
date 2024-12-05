package com.example.talev1_0.handlers;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.helpers.SnackbarHelper;
import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.player.MonsterViewModel;
import com.example.talev1_0.player.PlayerViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FightHandler {

    InventoryManager inventoryManager;
    LootHandler lootHandler;
    Item currentLoot;
    SnackbarHelper snackbarHelper;

    public void attackMonster(View rootView, PlayerViewModel player, MonsterViewModel monster){


        if (monster.getMonsterHp() >0 )
        {
        int damage = player.getPlayerDamage();
        monster.decreaseMonsterHp(damage);
            if (monster.getMonsterHp() <= 0) {
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


    }

    public void attackPlayer(PlayerViewModel player, MonsterViewModel monster){
        int damage = monster.getMonsterDamage();
        player.reduceHp(damage);
    }



}
