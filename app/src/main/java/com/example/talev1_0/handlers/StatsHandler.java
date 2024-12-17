package com.example.talev1_0.handlers;

import com.example.talev1_0.helpers.SnackbarHelper;
import com.example.talev1_0.player.PlayerViewModel;

public class StatsHandler {

    public void increaseStat(PlayerViewModel player, String stat) {
        if (player.getPlayer().getStatPoints() >= 1) {
            player.increaseStatPoints(-1);
            switch (stat) {
                case "Strength" -> player.increaseStrengthStat(1);
                case "Defence" -> player.increaseDefenceStat(1);
            }

        } else {
            System.out.println("sorry not enough stat points available");
        }
    }
}
