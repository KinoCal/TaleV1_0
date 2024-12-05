package com.example.talev1_0.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.monsters.BaseMonster;

import java.util.List;

public class MonsterViewModel extends ViewModel {
    private final MutableLiveData<BaseMonster> currentMonster = new MutableLiveData<>();

    // Method to set a monster
    public void setMonster(BaseMonster monster) {
        currentMonster.setValue(monster);
    }

    // LiveData to observe the monster
    public LiveData<BaseMonster> getMonster() {
        return currentMonster;
    }

    // Method to update the monster's health
    public void decreaseMonsterHp(int hpChange) {
        BaseMonster monster = currentMonster.getValue();
        if (monster != null)
        {
            monster.decreaseHp(hpChange);
            currentMonster.setValue(monster); // Trigger observers
        }
    }

    public List<Item> getLootTable() {
        return currentMonster.getValue().getLootTable();
    }

    public int getMonsterLevel(){
        return currentMonster.getValue().getLevel();
    }

    public int getMonsterHp(){
        return currentMonster.getValue().getHpValue();
    }

    public int getMonsterXp(){
        return currentMonster.getValue().getXpValue();
    }

    public int getMonsterMaxHp(){
        return currentMonster.getValue().getMaxHpValue();
    }

    public void setMonsterHp(int amount){
        BaseMonster monster = currentMonster.getValue();
        currentMonster.getValue().setHpValue(amount);
        currentMonster.setValue(monster);

    }

    public int getMonsterDamage(){
        return currentMonster.getValue().getDamageValue();
    }

    // Other update methods (damage, armor, etc.) can be added similarly
}
