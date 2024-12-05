package com.example.talev1_0.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.talev1_0.R;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import java.util.List;

public class LootAdapter extends RecyclerView.Adapter<LootAdapter.LootViewHolder> {

    private List<Item> lootItems;

    public LootAdapter(List<Item> lootItems) {
        this.lootItems = lootItems;
    }

    public void updateLootItems(List<Item> newLootItems) {
        this.lootItems = newLootItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loot, parent, false);
        return new LootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LootViewHolder holder, int position) {
        Item item = lootItems.get(position);
        holder.itemNameTextView.setText(item.getName() + ": " + item.getRarityName());
    }

    @Override
    public int getItemCount() {
        return lootItems.size();
    }

    static class LootViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;

        public LootViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.loot_item_name);
        }
    }
}
