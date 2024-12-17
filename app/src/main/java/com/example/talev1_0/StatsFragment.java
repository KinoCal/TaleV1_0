package com.example.talev1_0;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.talev1_0.handlers.StatsHandler;
import com.example.talev1_0.player.PlayerViewModel;


public class StatsFragment extends Fragment {

    private TextView strengthStat, defenseStat, damageStat, armorStat, damageRange, statPoints;
    private Button increaseStr, increaseDef;

    private PlayerViewModel playerViewModel;
    private StatsHandler statsHandler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        increaseStr = view.findViewById(R.id.increase_strength_button);
        increaseDef = view.findViewById(R.id.increase_defense_button);

        statPoints = view.findViewById(R.id.player_stat_points);
        damageRange = view.findViewById(R.id.damage_range_textview);
        armorStat = view.findViewById(R.id.player_armor_textview);
        damageStat = view.findViewById(R.id.player_damage_textview);
        strengthStat = view.findViewById(R.id.player_strength_textview);
        defenseStat = view.findViewById(R.id.player_defense_textview);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        statsHandler = new StatsHandler();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Observe changes in the player's data
        playerViewModel.getPlayerLiveData().observe(getViewLifecycleOwner(), player -> {

            statPoints.setText("Available Stat Points: " + player.getStatPoints());
            damageRange.setText("Damage Range: " + player.getStrengthStat() + "-" + player.getDamage());
            armorStat.setText("Armor: " + player.getArmor());
            damageStat.setText("Damage: " + player.getDamage());
            strengthStat.setText("Strength: " + player.getStrengthStat());
            defenseStat.setText("Defence: " + player.getDefenceStat());
        });

        increaseStr.setOnClickListener(v -> {
            statsHandler.increaseStat(playerViewModel, "Strength");
        });


    }
}