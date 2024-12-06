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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.adapters.MonsterAdapter;
import com.example.talev1_0.monsters.BaseMonster;
import com.example.talev1_0.monsters.DragonMonster;
import com.example.talev1_0.monsters.GoblinMonster;
import com.example.talev1_0.player.PlayerViewModel;
import com.example.talev1_0.gameItems.abstractClasses.Item;

import java.util.List;

public class MapFragment extends Fragment {

    private RecyclerView goblinCaveRecyclerView;
    private Button goblinCaveButton;
    private RecyclerView dragonDenRecyclerView;
    private Button dragonDenButton;

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

        goblinCaveButton = view.findViewById(R.id.goblin_cave_button);
        goblinCaveRecyclerView = view.findViewById(R.id.goblin_cave_recycler_view);
        goblinCaveRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dragonDenButton = view.findViewById(R.id.dragon_den_button);
        dragonDenRecyclerView = view.findViewById(R.id.dragon_den_recycler_view);
        dragonDenRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize PlayerViewModel
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Goblin Cave
        List<BaseMonster> goblinCaveMonsters = List.of(new GoblinMonster(), new GoblinMonster());
        MonsterAdapter goblinCaveAdapter = new MonsterAdapter(goblinCaveMonsters, this::showMonsterDropTable, this::startFight);
        goblinCaveRecyclerView.setAdapter(goblinCaveAdapter);
        goblinCaveButton.setOnClickListener(v -> toggleRecyclerViewVisibility(goblinCaveRecyclerView));

        // Setup Dragon Den
        List<BaseMonster> dragonDenMonsters = List.of(new DragonMonster(), new DragonMonster());
        MonsterAdapter dragonDenAdapter = new MonsterAdapter(dragonDenMonsters, this::showMonsterDropTable, this::startFight);
        dragonDenRecyclerView.setAdapter(dragonDenAdapter);
        dragonDenButton.setOnClickListener(v -> toggleRecyclerViewVisibility(dragonDenRecyclerView));
    }

    private void toggleRecyclerViewVisibility(RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showMonsterDropTable(BaseMonster monster) {
        List<Item> lootTable = monster.getLootTable();

        LootDialogFragment dialogFragment = new LootDialogFragment();
        dialogFragment.setLootTable(lootTable);
        dialogFragment.show(getParentFragmentManager(), "LootDialog");
    }

    private void startFight(BaseMonster monster) {
        if (fragmentChangeListener != null) {
            fragmentChangeListener.onFragmentChange("BattleArea");
            playerViewModel.setEnemyName(monster.getName());
        }
    }
}
