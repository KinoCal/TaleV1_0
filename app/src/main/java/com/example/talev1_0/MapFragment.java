package com.example.talev1_0;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.monsters.GoblinMonster;
import com.example.talev1_0.player.PlayerViewModel;
import com.example.talev1_0.gameItems.abstractClasses.Item;

import java.util.List;

public class MapFragment extends Fragment {

    private TextView monsterNameTextView;
    private Button dropTableButton, fightButton;
    private Button goblinCaveButton;
    private ConstraintLayout goblinCaveLayout;
    private FragmentChangeListener fragmentChangeListener;
    private PlayerViewModel playerViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentChangeListener) {
            fragmentChangeListener = (FragmentChangeListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement FragmentChangeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        goblinCaveLayout = view.findViewById(R.id.goblin_cave_layout);
        goblinCaveButton = view.findViewById(R.id.goblin_cave_button);
        monsterNameTextView = view.findViewById(R.id.monster_name_textview);
        dropTableButton = view.findViewById(R.id.drop_table_button);
        fightButton = view.findViewById(R.id.fight_button);

        // Initialize PlayerViewModel
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goblinCaveButton.setOnClickListener(v ->{
            if (goblinCaveLayout.getVisibility() == View.VISIBLE) {
                goblinCaveLayout.setVisibility(View.GONE); // Hide the layout
            } else {
                goblinCaveLayout.setVisibility(View.VISIBLE); // Show the layout
            }
        });

        dropTableButton.setOnClickListener(v -> {
            // Simulate selecting a monster (e.g., Goblin)
            GoblinMonster goblinMonster = new GoblinMonster();
            showMonsterDropTable(goblinMonster);
        });

        fightButton.setOnClickListener(v -> {
            if (fragmentChangeListener != null) {
                fragmentChangeListener.onFragmentChange("BattleArea");
                playerViewModel.setEnemyName("Goblin");
            }
        });
    }

    public void showMonsterDropTable(BaseMonster monster){
        List<Item> lootTable = monster.getLootTable(); // List is sufficient

        LootDialogFragment dialogFragment = new LootDialogFragment();
        dialogFragment.setLootTable(lootTable); // Pass the lootTable directly
        dialogFragment.show(getParentFragmentManager(), "LootDialog");
    }

}
