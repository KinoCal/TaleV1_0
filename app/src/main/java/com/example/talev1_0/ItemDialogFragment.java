package com.example.talev1_0;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.Consumables.ConsumableItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.player.PlayerViewModel;

public class ItemDialogFragment extends DialogFragment {


    private int equipmentIndex;
    private Button useButton, equipButton, unequipButton, equippedWeaponButton, equippedArmorButton;
    private TextView selectedItemName, selectedItemPrice, selectedItemDamageValue, SelectedItemArmorValue, selectedItemHealingValue;
    Item selectedItem;
    PlayerViewModel playerViewModel;
    InventoryManager inventoryManager;
    private Boolean isEquippedItem;

    public ItemDialogFragment(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    public void setSelectedItem(Item item, Boolean equippedItem) {
        this.isEquippedItem = equippedItem;
        this.selectedItem = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_dialog, container, false);

        if (selectedItem == null) {
            throw new IllegalStateException("selected item must be set first.");
        }


        inventoryManager = new InventoryManager();

        useButton = view.findViewById(R.id.use_button);
        equipButton = view.findViewById(R.id.equip_button);
        unequipButton = view.findViewById(R.id.unequip_button);

        selectedItemName = view.findViewById(R.id.selected_item_name);
        selectedItemPrice = view.findViewById(R.id.selected_item_price);
        selectedItemDamageValue = view.findViewById(R.id.selected_item_damage_value);
        SelectedItemArmorValue = view.findViewById(R.id.selected_item_armor_value);
        selectedItemHealingValue = view.findViewById(R.id.selected_item_heal_value);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        useButton.setVisibility(View.INVISIBLE);
        equipButton.setVisibility(View.INVISIBLE);
        unequipButton.setVisibility(View.INVISIBLE);

        SetupUiForSelectedInventoryItem(selectedItem);

        useButton.setOnClickListener(v -> {
            inventoryManager.useItem(playerViewModel.getPlayerItemIndex(), playerViewModel);
            //useButton.setVisibility(View.INVISIBLE);
        });

        // EQUIP / UN-EQUIP ITEM BUTTONS
        useButton.setOnClickListener(v -> {
            inventoryManager.useItem(playerViewModel.getPlayerItemIndex(), playerViewModel);
            if (this != null && this.isVisible()) {
                this.dismissNow();
            }
        });

        // EQUIP / UN-EQUIP ITEM BUTTONS
        equipButton.setOnClickListener(v -> {
            inventoryManager.equipItem(playerViewModel.getPlayerEquipmentIndex(), playerViewModel);
            if (this != null && this.isVisible()) {
                this.dismissNow();
            }
        });

        unequipButton.setOnClickListener(v -> {
            inventoryManager.UnEquipItem(playerViewModel, playerViewModel.getEquippedItems()[equipmentIndex]);
            if (this != null && this.isVisible()) {
                this.dismissNow();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void SetupUiForSelectedInventoryItem(Item selectedItem) {


        if (selectedItem instanceof WeaponItem weaponItem) {

            selectedItemName.setText("Name: " + weaponItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(weaponItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + String.valueOf(weaponItem.getDamageValue()));
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + "0");
            equipButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.VISIBLE);
            unequipButton.setVisibility(isEquippedItem ? View.VISIBLE : View.INVISIBLE);
            useButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.INVISIBLE);
        } else if (selectedItem instanceof ArmorItem armorItem) {

            selectedItemName.setText("Name: " + armorItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(armorItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + String.valueOf(armorItem.getArmorValue()));
            selectedItemHealingValue.setText("Heals: " + "0");
            equipButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.VISIBLE);
            unequipButton.setVisibility(isEquippedItem ? View.VISIBLE : View.INVISIBLE);
            useButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.INVISIBLE);
        } else if (selectedItem instanceof ConsumableItem consumableItem) {

            useButton.setVisibility(View.VISIBLE);
            selectedItemName.setText("Name: " + consumableItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(consumableItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + consumableItem.getHealingValue());
            equipButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.INVISIBLE);
            unequipButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.INVISIBLE);
            useButton.setVisibility(isEquippedItem ? View.INVISIBLE : View.VISIBLE);
        }
    }
}