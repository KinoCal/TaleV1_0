package com.example.talev1_0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.talev1_0.Factories.ItemFactories.MonsterFactory;
import com.example.talev1_0.NPCS.TavernShopKeeper;
import com.example.talev1_0.handlers.FightHandler;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.monsters.GoblinMonster;
import com.example.talev1_0.player.MonsterViewModel;
import com.example.talev1_0.player.PlayerViewModel;

public class BattleAreaFragment extends Fragment {
    private MonsterViewModel monsterViewModel;
    private TextView monsterNameTextView;
    private TextView monsterHpTextView;
    private Button attackButton;
    private PlayerViewModel playerViewModel;
    private BaseMonster currentMonster;
    private MonsterFactory monsterFactory;
    private FightHandler fightHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle_area, container, false);

        monsterNameTextView = view.findViewById(R.id.monsterName);
        monsterHpTextView = view.findViewById(R.id.monsterHp);
        attackButton = view.findViewById(R.id.attack_button);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ALWAYS Initialize ViewModels and handlers in onCreate
        monsterViewModel = new ViewModelProvider(this).get(MonsterViewModel.class);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        fightHandler = new FightHandler();
        monsterFactory = new MonsterFactory();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe monster data
        monsterViewModel.getMonster().observe(getViewLifecycleOwner(), monster -> {
            if (monster != null) {
                monsterNameTextView.setText(monster.getName() + " Lvl: " + monster.getLevel());
                monsterHpTextView.setText("Hp: " + monster.getHpValue());
            }
        });

        // Initialize the current monster using playerViewModel
        String enemyName = playerViewModel.getEnemyName();
        if (enemyName != null) {
            currentMonster = monsterFactory.createMonster(enemyName);
            monsterViewModel.setMonster(currentMonster);
        } else {
            // Handle the case where enemyName is null
            currentMonster = null;
        }



        // Set attack button listener
        attackButton.setOnClickListener(v -> {
            if (currentMonster != null) {

                fightHandler.attackMonster(view, playerViewModel, monsterViewModel);
            }
        });
    }
}
