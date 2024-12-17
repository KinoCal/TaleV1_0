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
    InventoryManager inventoryManager = new InventoryManager();


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
        // Get the corresponding consumable item based on position
        int actualIndex = getActualIndex(position);
        Item inventoryItem = inventoryItems.get(actualIndex);

        if (inventoryItem.getType().equals("consumable")) {
            holder.consumableItemTextView.setText(inventoryItem.getName() + " (" + inventoryItem.getQuantity() + ")");
        } else {
            System.out.println("Error: Non-consumable item passed to the adapter.");
        }

        // Set the click listener for the TextView
        holder.consumableItemTextView.setOnClickListener(v -> {
            if (inventoryItem.getQuantity() > 1) {
                inventoryManager.useItemInConsumableAdapter(inventoryItem, playerViewModel); // Use the item
            } else {
                inventoryManager.useItemInConsumableAdapter(inventoryItem, playerViewModel); // Use the last item
                holder.consumableItemTextView.setText("");
                playerViewModel.getInventoryItems().set(actualIndex, playerViewModel.getEmptyItem());
                notifyDataSetChanged(); // Refresh the RecyclerView
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) inventoryItems.stream().filter(item -> item.getType().equals("consumable")).count();
    }

    /**
     * Helper method to find the actual index in the inventory list for the given filtered position.
     */
    private int getActualIndex(int filteredPosition) {
        int count = 0;
        for (int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).getType().equals("consumable")) {
                if (count == filteredPosition) {
                    return i;
                }
                count++;
            }
        }
        throw new IllegalStateException("Filtered position out of bounds");
    }


    static class ConsumableViewHolder extends RecyclerView.ViewHolder {
        TextView consumableItemTextView;


        public ConsumableViewHolder(@NonNull View itemView) {
            super(itemView);
            consumableItemTextView = itemView.findViewById(R.id.consumable_item_textview);
        }
    }


}
