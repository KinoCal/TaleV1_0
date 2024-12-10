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

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<Item> inventoryItems;
    private OnInventoryActionListener inventoryActionListener;

    public InventoryAdapter(List<Item> inventoryItems, OnInventoryActionListener inventoryActionListener){
        this.inventoryItems = inventoryItems;
        this.inventoryActionListener = inventoryActionListener;

    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        Item inventoryItem = inventoryItems.get(position);
        if (inventoryItem.getName().equals("empty")){
            holder.inventoryItemTextView.setText(" ");
        }else {
        holder.inventoryItemTextView.setText(inventoryItem.getName() + " (" + inventoryItem.getQuantity() + ")");

        }

        // Set the click listener for the TextView
        holder.inventoryItemTextView.setOnClickListener(v -> {
                inventoryActionListener.onAction(inventoryItem, position); // Pass the clicked item
        });

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView inventoryItemTextView;


        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            inventoryItemTextView = itemView.findViewById(R.id.inventory_item_text_view);
        }
    }

    public interface OnInventoryActionListener {
        void onAction(Item inventoryItem, int index);
    }

}
