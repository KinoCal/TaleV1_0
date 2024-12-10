package com.example.talev1_0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.NPCS.TavernShopKeeper;
import com.example.talev1_0.adapters.InventoryAdapter;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.Consumables.ConsumableItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;
import com.example.talev1_0.gameItems.interfaces.Item_Empty;
import com.example.talev1_0.handlers.InventoryManager;
import com.example.talev1_0.player.PlayerViewModel;

public class ShopFragment extends Fragment {
    private Button[] shopButtons = new Button[4];
    private Button buyButton, sellButton;
    private TextView selectedItemName, selectedItemPrice, selectedItemDamageValue, SelectedItemArmorValue, selectedItemHealingValue;
    private TavernShopKeeper tavernShopKeeper;
    private RecyclerView inventoryRecyclerView;
    private int inventoryIndex, equipmentIndex;
    private InventoryManager inventoryManager;
    private PlayerViewModel playerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        shopButtons[0] = view.findViewById(R.id.shop_item_1);
        shopButtons[1] = view.findViewById(R.id.shop_item_2);
        shopButtons[2] = view.findViewById(R.id.shop_item_3);
        shopButtons[3] = view.findViewById(R.id.shop_item_4);

        buyButton = view.findViewById(R.id.buy_item_button);
        sellButton = view.findViewById(R.id.sell_item_button);

        inventoryRecyclerView = view.findViewById(R.id.inventory_shop_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3); // 3 columns
        inventoryRecyclerView.setLayoutManager(gridLayoutManager);

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
        tavernShopKeeper = new TavernShopKeeper();
        inventoryIndex = 0;
        equipmentIndex = 0;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Visibility Checks
        buyButton.setVisibility(View.INVISIBLE);
        sellButton.setVisibility(View.INVISIBLE);

        // Set up ui for shopkeeper
        for (int i = 0; i < 4; i++){
            shopButtons[i].setText(tavernShopKeeper.getShopItems(i).getName());
        }
        // Get the ViewModel
        // Observe changes in the player's data
        playerViewModel.getPlayerLiveData().observe(getViewLifecycleOwner(), player -> {
            // Update the UI (e.g., display the current HP)
            InventoryAdapter inventoryAdapter = new InventoryAdapter(player.inventoryItems, this::setPlayerIndexesForSelectedItem);
            inventoryRecyclerView.setAdapter(inventoryAdapter);

        });



        // SHOPKEEPER BUTTONS
        shopButtons[0].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(tavernShopKeeper.getShopItems(0), 0);
            SetupUiForSelectedShopItem(0);

        });

        shopButtons[1].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(tavernShopKeeper.getShopItems(0),1);
            SetupUiForSelectedShopItem(1);

        });

        shopButtons[2].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(tavernShopKeeper.getShopItems(0),2);
            SetupUiForSelectedShopItem(2);

        });

        shopButtons[3].setOnClickListener(v ->{
            setPlayerIndexesForSelectedItem(tavernShopKeeper.getShopItems(0),3);
            SetupUiForSelectedShopItem(3);

        });

        // BUY / SELL ITEM BUTTONS
        buyButton.setOnClickListener(v ->{
            if (inventoryManager.BuyItem(playerViewModel, tavernShopKeeper.getShopItems(playerViewModel.getPlayerShopItemIndex()))){
                System.out.println("item bought: " + tavernShopKeeper.getShopItems(playerViewModel.getPlayerShopItemIndex()).getName());
            }else {
                System.out.println("error buying item");
                Toast.makeText(getActivity().getApplicationContext(), "sorry not enough gold", Toast.LENGTH_SHORT).show();
            }
            buyButton.setVisibility(View.INVISIBLE);
        });

        sellButton.setOnClickListener(v -> {

            if (inventoryManager.SellItemToShop(playerViewModel, playerViewModel.getInventoryItemAtIndex(playerViewModel.getPlayerItemIndex()))){
                System.out.println("Sold Item: " + playerViewModel.getInventoryItemAtIndex(playerViewModel.getPlayerItemIndex()).getName());
            }else {
                System.out.println("error selling item");


            }

            sellButton.setVisibility(View.INVISIBLE);
        });

    }

    public void SetupUiForSelectedShopItem(int index){

        buyButton.setVisibility(View.VISIBLE);
        sellButton.setVisibility(View.INVISIBLE);
        Item selectedItem = tavernShopKeeper.getShopItems(index);

        if (selectedItem instanceof WeaponItem weaponItem) {

            selectedItemName.setText("Name: " + weaponItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(weaponItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + String.valueOf(weaponItem.getDamageValue()));
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + "0");
        } else if (selectedItem instanceof ArmorItem armorItem) {

            selectedItemName.setText("Name: " + armorItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(armorItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + String.valueOf(armorItem.getArmorValue()));
            selectedItemHealingValue.setText("Heals: " + "0");
        } else if (selectedItem instanceof ConsumableItem consumableItem) {

            selectedItemName.setText("Name: " + consumableItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(consumableItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + consumableItem.getHealingValue());
        }
    }

    public void setPlayerIndexesForSelectedItem(Item item, int index){
        inventoryIndex = index;
        playerViewModel.setPlayerItemIndex(index);
        playerViewModel.setPlayerEquipmentIndex(playerViewModel.getInventoryItemAtIndex(index).getItemIndex());
        playerViewModel.setPlayerShopItemIndex(index);
        SetupUiForSelectedInventoryItem(index);
    }

    @SuppressLint("SetTextI18n")
    private void SetupUiForSelectedInventoryItem(int index){

        buyButton.setVisibility(View.INVISIBLE);
        sellButton.setVisibility(View.VISIBLE);

        Item selectedItem = playerViewModel.getInventoryItemAtIndex(index);

        if (selectedItem instanceof WeaponItem weaponItem) {

            selectedItemName.setText("Name: " + weaponItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(weaponItem.getPrice()));
            selectedItemDamageValue.setText("Damage: " + String.valueOf(weaponItem.getDamageValue()));
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + "0");
        } else if (selectedItem instanceof ArmorItem armorItem) {

            selectedItemName.setText("Name: " + armorItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(armorItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + String.valueOf(armorItem.getArmorValue()));
            selectedItemHealingValue.setText("Heals: " + "0");
        } else if (selectedItem instanceof ConsumableItem consumableItem) {

            selectedItemName.setText("Name: " + consumableItem.getName());
            selectedItemPrice.setText("Price: " + String.valueOf(consumableItem.getPrice()));
            selectedItemDamageValue.setText("Damage" + "0");
            SelectedItemArmorValue.setText("Armor: " + "0");
            selectedItemHealingValue.setText("Heals: " + consumableItem.getHealingValue());
        }
    }


}


