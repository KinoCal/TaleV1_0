package com.example.talev1_0;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.adapters.InventoryAdapter;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.player.PlayerViewModel;

public class InventoryFragment extends Fragment {
    private TextView strengthStat, defenceStat, damageStat, armorStat, damageRange;
    private Button useButton, equipButton, unequipButton, equippedWeaponButton, equippedArmorButton;
    private TextView selectedItemName, selectedItemPrice, selectedItemDamageValue, SelectedItemArmorValue, selectedItemHealingValue;
    private PlayerViewModel playerViewModel;
    private InventoryManager inventoryManager;
    private RecyclerView inventoryRecyclerView;
    private int equipmentIndex;
    private FragmentChangeListener fragmentChangeListener;
    private ItemDialogFragment dialogFragment;

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
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        //inventoryItemTextView = view.findViewById(R.id.inventory_item_text_view);
        inventoryRecyclerView = view.findViewById(R.id.inventory_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3); // 3 columns
        inventoryRecyclerView.setLayoutManager(gridLayoutManager);

        equippedWeaponButton = view.findViewById(R.id.equipped_weapon);
        equippedArmorButton = view.findViewById(R.id.equipped_armor);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        inventoryManager = new InventoryManager();
        dialogFragment = new ItemDialogFragment(playerViewModel);
        equipmentIndex = 0;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Observe changes in the player's data
        playerViewModel.getPlayerLiveData().observe(getViewLifecycleOwner(), player -> {
            // Update the UI (e.g., display the current HP)
            InventoryAdapter inventoryAdapter = new InventoryAdapter(player.inventoryItems, this::setUiForSelectedInventoryItem);
            inventoryRecyclerView.setAdapter(inventoryAdapter);
            inventoryAdapter.notifyDataSetChanged();

            equippedWeaponButton.setText(playerViewModel.getEquippedItemAtIndex(0).getName());
            equippedArmorButton.setText(playerViewModel.getEquippedItemAtIndex(1).getName());
        });


        // EQUIPMENT BUTTONS
        equippedWeaponButton.setOnClickListener(v -> {
            System.out.println(playerViewModel.getPlayer().getUsername());
            playerViewModel.savePlayer();
            equipmentIndex = 0;
            SetupUiForSelectedEquipmentItem(equipmentIndex);
        });

        equippedArmorButton.setOnClickListener(v -> {
            equipmentIndex = 1;
            SetupUiForSelectedEquipmentItem(equipmentIndex);
        });


    }

    public void setUiForSelectedInventoryItem(Item inventoryItem, int index) {

        System.out.println(playerViewModel.getEquippedItemAtIndex(0).getName());
        dialogFragment.setSelectedItem(inventoryItem, false);
        dialogFragment.show(getParentFragmentManager(), "ItemDialog");
        playerViewModel.setPlayerItemIndex(index);
        playerViewModel.setPlayerEquipmentIndex(playerViewModel.getInventoryItemAtIndex(index).getItemIndex());
        //SetupUiForSelectedInventoryItem(index);
    }


    @SuppressLint("SetTextI18n")
    private void SetupUiForSelectedEquipmentItem(int index) {
        Item selectedItem = playerViewModel.getEquippedItemAtIndex(index);

        dialogFragment.setSelectedItem(selectedItem, true);
        dialogFragment.show(getParentFragmentManager(), "ItemDialog");
        playerViewModel.setPlayerItemIndex(index);
        playerViewModel.setPlayerEquipmentIndex(playerViewModel.getInventoryItemAtIndex(index).getItemIndex());


    }


}
