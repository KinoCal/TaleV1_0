package com.example.talev1_0.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.R;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.player.PlayerViewModel;

import java.util.List;

public class ConsumableAdapter extends RecyclerView.Adapter<ConsumableAdapter.ConsumableViewHolder> {

    private List<Item> inventoryItems;
    private PlayerViewModel playerViewModel;


    public ConsumableAdapter(List<Item> inventoryItems, PlayerViewModel playerViewModel) {
        this.inventoryItems = inventoryItems;
        this.playerViewModel = playerViewModel;


    }

    @NonNull
    @Override
    public ConsumableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consumable, parent, false);
        return new ConsumableViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ConsumableViewHolder holder, int position) {
        Item inventoryItem = inventoryItems.get(position);
        if (inventoryItem.getType().equals("consumable")) {
            holder.consumableItemTextView.setText(inventoryItem.getName() + " (" + inventoryItem.getQuantity() + ")");
        } else {
            System.out.println("error in consumable adapter viewholder");
        }

        // Set the click listener for the TextView
        holder.consumableItemTextView.setOnClickListener(v -> {
            InventoryManager inventoryManager = new InventoryManager();
            inventoryManager.useItem(position, playerViewModel);// Pass the clicked item
        });

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    static class ConsumableViewHolder extends RecyclerView.ViewHolder {
        TextView consumableItemTextView;


        public ConsumableViewHolder(@NonNull View itemView) {
            super(itemView);
            consumableItemTextView = itemView.findViewById(R.id.consumable_item_textview);
        }
    }


}
