package com.example.talev1_0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.adapters.LootAdapter;
import com.example.talev1_0.R;
import com.example.talev1_0.gameItems.abstractClasses.Item;

import java.util.List;

public class LootDialogFragment extends DialogFragment {

    private List<Item> lootTable;

    // Setter for lootTable
    public void setLootTable(List<Item> lootTable) {
        this.lootTable = lootTable;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loot_table, container, false);

        if (lootTable == null) {
            throw new IllegalStateException("Loot table must be set before showing the dialog.");
        }

        RecyclerView recyclerView = view.findViewById(R.id.loot_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new LootAdapter(lootTable));

        return view;
    }
}
