package com.example.talev1_0;

import android.annotation.SuppressLint;
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

import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.Consumables.ConsumableItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.gameItems.interfaces.Item_Empty;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.player.PlayerViewModel;

public class InventoryFragment extends Fragment {
    private Button[] inventoryButtons = new Button[5];
    private Button  useButton, equipButton, unequipButton, equippedWeaponButton, equippedArmorButton;
    private TextView selectedItemName, selectedItemPrice, selectedItemDamageValue, SelectedItemArmorValue, selectedItemHealingValue;
    private PlayerViewModel playerViewModel;
    private InventoryManager inventoryManager;
    private int equipmentIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        inventoryButtons[0] = view.findViewById(R.id.inventory_item_1);
        inventoryButtons[1] = view.findViewById(R.id.inventory_item_2);
        inventoryButtons[2] = view.findViewById(R.id.inventory_item_3);
        inventoryButtons[3] = view.findViewById(R.id.inventory_item_4);
        inventoryButtons[4] = view.findViewById(R.id.inventory_item_5);

        useButton = view.findViewById(R.id.use_button);
        equipButton = view.findViewById(R.id.equip_button);
        unequipButton = view.findViewById(R.id.unequip_button);

        equippedWeaponButton = view.findViewById(R.id.equipped_weapon);
        equippedArmorButton = view.findViewById(R.id.equipped_armor);

        selectedItemName = view.findViewById(R.id.selected_item_name);
        selectedItemPrice = view.findViewById(R.id.selected_item_price);
        selectedItemDamageValue = view.findViewById(R.id.selected_item_damage_value);
        SelectedItemArmorValue = view.findViewById(R.id.selected_item_armor_value);
        selectedItemHealingValue = view.findViewById(R.id.selected_item_heal_value);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        inventoryManager = new InventoryManager();
        equipmentIndex = 0;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        useButton.setVisibility(View.INVISIBLE);
        equipButton.setVisibility(View.INVISIBLE);
        unequipButton.setVisibility(View.INVISIBLE);
        // Get the ViewModel


        // Observe changes in the player's data
        playerViewModel.getPlayer().observe(getViewLifecycleOwner(), player -> {
            // Update the UI (e.g., display the current HP)
            for (int i = 0; i < playerViewModel.getInventoryItems().size(); i++)
                if (playerViewModel.getInventoryItems().get(i).getType().equals("empty")){
                    inventoryButtons[i].setText("");
                }
                else {
                    inventoryButtons[i].setText(player.inventoryItems.get(i).ToString());

                }

            equippedWeaponButton.setText(playerViewModel.getEquippedItemAtIndex(0).getName());
            equippedArmorButton.setText(playerViewModel.getEquippedItemAtIndex(1).getName());
        });


        inventoryButtons[0].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(0);
            SetupUiForSelectedInventoryItem(0);

        });

        inventoryButtons[1].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(1);
            SetupUiForSelectedInventoryItem(1);

        });

        inventoryButtons[2].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(2);
            SetupUiForSelectedInventoryItem(2);

        });

        inventoryButtons[3].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(3);
            SetupUiForSelectedInventoryItem(3);

        });

        inventoryButtons[4].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(4);
            SetupUiForSelectedInventoryItem(4);

        });

        // EQUIPMENT BUTTONS
        equippedWeaponButton.setOnClickListener(v -> {
            equipmentIndex = 0;
            SetupUiForSelectedEquipmentItem(equipmentIndex);
        });

        equippedArmorButton.setOnClickListener(v -> {
            equipmentIndex = 1;
            SetupUiForSelectedEquipmentItem(equipmentIndex);
        });

        useButton.setOnClickListener(v ->{
            inventoryManager.useItem(playerViewModel.getPlayerItemIndex(), playerViewModel);
            useButton.setVisibility(View.INVISIBLE);
        });

        // EQUIP / UN-EQUIP ITEM BUTTONS
        equipButton.setOnClickListener(v ->{
            inventoryManager.equipItem(playerViewModel.getPlayerEquipmentIndex(), playerViewModel);
            equipButton.setVisibility(View.INVISIBLE);
        });

        unequipButton.setOnClickListener(v -> {
            inventoryManager.UnEquipItem(playerViewModel, playerViewModel.getEquippedItems()[equipmentIndex]);
            unequipButton.setVisibility(View.INVISIBLE);
        });

    }

    public void setPlayerIndexesForSelectedItem(int index){
        playerViewModel.setPlayerItemIndex(index);
        playerViewModel.setPlayerEquipmentIndex(playerViewModel.getInventoryItemAtIndex(index).getItemIndex());
        SetupUiForSelectedInventoryItem(index);
    }

    @SuppressLint("SetTextI18n")
    private void SetupUiForSelectedInventoryItem(int index){
        Item selectedItem = playerViewModel.getInventoryItemAtIndex(index);

        if (selectedItem instanceof WeaponItem weaponItem) {

            selectedItemName.setText("Name: " + weaponItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(weaponItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + String.valueOf(weaponItem.getDamageValue()));
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + "0");
            equipButton.setVisibility(View.VISIBLE);
            unequipButton.setVisibility(View.INVISIBLE);
            useButton.setVisibility(View.INVISIBLE);
        } else if (selectedItem instanceof ArmorItem armorItem) {

            selectedItemName.setText("Name: " + armorItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(armorItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + String.valueOf(armorItem.getArmorValue()));
            selectedItemHealingValue.setText("Heals: " + "0");
            equipButton.setVisibility(View.VISIBLE);
            unequipButton.setVisibility(View.INVISIBLE);
            useButton.setVisibility(View.INVISIBLE);
        } else if (selectedItem instanceof ConsumableItem consumableItem) {

            selectedItemName.setText("Name: " + consumableItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(consumableItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + consumableItem.getHealingValue());
            useButton.setVisibility(View.VISIBLE);
            equipButton.setVisibility(View.INVISIBLE);
            unequipButton.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void SetupUiForSelectedEquipmentItem(int index){
        Item selectedItem = playerViewModel.getEquippedItemAtIndex(index);

        if (selectedItem instanceof WeaponItem weaponItem) {

            selectedItemName.setText("Name: " + weaponItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(weaponItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + String.valueOf(weaponItem.getDamageValue()));
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + "0");
            unequipButton.setVisibility(View.VISIBLE);
            useButton.setVisibility(View.INVISIBLE);
            equipButton.setVisibility(View.INVISIBLE);
        } else if (selectedItem instanceof ArmorItem armorItem) {

            selectedItemName.setText("Name: " + armorItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(armorItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + "0");
            SelectedItemArmorValue.setText("Armor: " + String.valueOf(armorItem.getArmorValue()));
            selectedItemHealingValue.setText("Heals: " + "0");
            unequipButton.setVisibility(View.VISIBLE);
            useButton.setVisibility(View.INVISIBLE);
            equipButton.setVisibility(View.INVISIBLE);
        }
    }




}
