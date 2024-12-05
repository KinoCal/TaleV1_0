package com.example.talev1_0.Factories.ItemFactories;

import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.monsters.GoblinMonster;

public class MonsterFactory {

    public BaseMonster createMonster(String name){

        return switch (name){
            case "Goblin" -> new GoblinMonster();

            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
