package com.example.talev1_0.player;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talev1_0.Factories.ItemFactories.Factories;
import com.example.talev1_0.database.DatabaseClient;
import com.example.talev1_0.database.EquipmentEntity;
import com.example.talev1_0.database.InventoryEntity;
import com.example.talev1_0.database.PlayerEntity;
import com.example.talev1_0.gameItems.abstractClasses.Item;
import com.example.talev1_0.gameItems.conreteClasses.equipment.ArmorItem;
import com.example.talev1_0.gameItems.conreteClasses.equipment.WeaponItem;

import java.util.ArrayList;
import java.util.List;

public class PlayerViewModel extends AndroidViewModel {
    private final MutableLiveData<Player> playerLiveData = new MutableLiveData<>();
    private Player player; // Local reference to avoid redundant calls
    private final Application application;
    private Factories factories;

    public PlayerViewModel(Application application) {
        super(application);
        player = new Player(); // Initialize the Player
        playerLiveData.setValue(player);
        factories = new Factories();
        this.application = application;

        initializePlayer(1);
    }

    // Method to initialize player's inventory in the database
    private void initializeInventoryInDatabase(int playerId) {
        new Thread(() -> {
            // Create a list of default inventory items
            List<InventoryEntity> inventoryItems = new ArrayList<>();

            // Add default items to the list
            inventoryItems.add(new InventoryEntity(playerId, "empty", "empty", 1));
            inventoryItems.add(new InventoryEntity(playerId, "empty", "empty", 1));
            inventoryItems.add(new InventoryEntity(playerId, "empty", "empty", 1));
            inventoryItems.add(new InventoryEntity(playerId, "empty", "empty", 1));
            inventoryItems.add(new InventoryEntity(playerId, "empty", "empty", 1));

            // Insert the items into the inventory table
            DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().insertAll(inventoryItems);

            // Optional: Post a message back to the UI thread
        }).start();
    }

    // Method to initialize player's equipment in the database
    private void initializeEquipmentInDatabase() {
        new Thread(() -> {
            // Create a list of default inventory items
            List<EquipmentEntity> equipmentItems = new ArrayList<>();

            // Add default items to the list
            equipmentItems.add(new EquipmentEntity("empty", "weapon"));
            equipmentItems.add(new EquipmentEntity("empty", "armor"));


            // Insert the items into the inventory table
            DatabaseClient.getInstance(application).getAppDatabase().equipmentDao().insertAll(equipmentItems);

            // Optional: Post a message back to the UI thread
        }).start();
    }

    // Helper to map InventoryEntity to players inventory
    // Helper to map PlayerEntity to Player
    private Player mapEntityToPlayer(PlayerEntity entity) {
        Player player = new Player();
        player.setLevel(entity.getLevel());
        player.setCurrentHp(entity.getCurrentHp());
        player.setMaxHp(entity.getMaxHp());
        player.setStrengthStat(entity.getStrengthStat());
        player.setDefenceStat(entity.getDefenceStat());
        player.setDamage(entity.getDamage());
        player.setArmor(entity.getArmor());
        player.setCurrentExp(entity.getCurrentExp());
        player.setMaxExp(entity.getMaxExp());
        player.setGold(entity.getGold());

        // Retrieve inventory items
        InventoryEntity inventoryEntity1 = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(1);
        InventoryEntity inventoryEntity2 = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(2);
        InventoryEntity inventoryEntity3 = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(3);
        InventoryEntity inventoryEntity4 = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(4);
        InventoryEntity inventoryEntity5 = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(5);

        // Correctly pass the correct inventory name for each item
        player.setInventoryItem(factories.createItem(inventoryEntity1.getType(), inventoryEntity1.getName()), 0);
        player.setInventoryItem(factories.createItem(inventoryEntity2.getType(), inventoryEntity2.getName()), 1);
        player.setInventoryItem(factories.createItem(inventoryEntity3.getType(), inventoryEntity3.getName()), 2);
        player.setInventoryItem(factories.createItem(inventoryEntity4.getType(), inventoryEntity4.getName()), 3);
        player.setInventoryItem(factories.createItem(inventoryEntity5.getType(), inventoryEntity5.getName()), 4);

        player.getInventoryItem(0).setQuantity(inventoryEntity1.getQuantity());
        player.getInventoryItem(1).setQuantity(inventoryEntity2.getQuantity());
        player.getInventoryItem(2).setQuantity(inventoryEntity3.getQuantity());
        player.getInventoryItem(3).setQuantity(inventoryEntity4.getQuantity());
        player.getInventoryItem(4).setQuantity(inventoryEntity5.getQuantity());

        // Retrieve equipment items
        EquipmentEntity equipmentEntity1 = DatabaseClient.getInstance(application).getAppDatabase().equipmentDao().getEquipmentById(1);
        EquipmentEntity equipmentEntity2 = DatabaseClient.getInstance(application).getAppDatabase().equipmentDao().getEquipmentById(2);

        // Pass database entities to the players equipment
        player.setEquippedItems(factories.createItem(equipmentEntity1.getType(), equipmentEntity1.getName()), 0);
        player.setEquippedItems(factories.createItem(equipmentEntity2.getType(), equipmentEntity2.getName()), 1);

        return player;
    }


    // Initialize player from the database or create a new one
    private void initializePlayer(int playerId) {
        new Thread(() -> {
            PlayerEntity playerEntity = DatabaseClient.getInstance(application).getAppDatabase().playerDao().getPlayerById(playerId);

            if (playerEntity != null) {
                // Existing player found, map it to the Player object
                player = mapEntityToPlayer(playerEntity);
            } else {
                // No player found, create a new one
                player = new Player(); // Initialize new player data
                savePlayerToDatabase(player, playerId); // Save to database with the given ID

                initializeInventoryInDatabase(playerId);
                initializeEquipmentInDatabase();

            }

            playerLiveData.postValue(player); // Update LiveData
        }).start();
    }

    // Save player to the database
    public void savePlayerToDatabase(Player player, int playerId) {
        new Thread(() -> {
            PlayerEntity playerEntity = new PlayerEntity(
                    playerId, // Ensure the correct ID is assigned
                    player.getLevel(),
                    player.getCurrentHp(),
                    player.getMaxHp(),
                    player.getStrengthStat(),
                    player.getDefenceStat(),
                    player.getDamage(),
                    player.getArmor(),
                    player.getCurrentExp(),
                    player.getMaxExp(),
                    player.getGold()
            );


            DatabaseClient.getInstance(application).getAppDatabase().playerDao().insertPlayer(playerEntity);

            // Save the player's inventory data
            for (int i = 0; i < player.inventoryItems.length; i++) {
                // Retrieve the corresponding InventoryEntity from the database
                InventoryEntity inventoryEntity = DatabaseClient.getInstance(application)
                        .getAppDatabase()
                        .inventoryDao()
                        .getInventoryById(i + 1); // Assuming IDs start at 1 and match indices

                // Get the item from the player's inventory
                Item item = player.getInventoryItem(i);

                    // Update the InventoryEntity with the item's data
                    inventoryEntity.setPlayerId(playerId);
                    inventoryEntity.setName(item.getName());
                    inventoryEntity.setType(item.getType());
                    inventoryEntity.setQuantity(item.getQuantity()); // Assuming Item has a `getQuantity()` method

                // Update the database
                DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().insertInventory(inventoryEntity);
            }

            // Save the player's equipment data
            for (int j = 0; j < player.equippedItems.length; j++) {
                // Retrieve the corresponding InventoryEntity from the database
                EquipmentEntity equipmentEntity = DatabaseClient.getInstance(application)
                        .getAppDatabase()
                        .equipmentDao()
                        .getEquipmentById(j + 1); // Assuming IDs start at 1 and match indices

                // Get the item from the player's inventory
                Item item = player.getEquippedItem(j);

                // Update the InventoryEntity with the item's data
                equipmentEntity.setName(item.getName());
                equipmentEntity.setType(item.getType());


                // Update the database
                DatabaseClient.getInstance(application).getAppDatabase().equipmentDao().insertEquipment(equipmentEntity);
            }



        }).start();
    }


    // Save player data to the database
    public void savePlayer() {
        savePlayerToDatabase(player,1);
    }


    public LiveData<Player> getPlayer() {
        return playerLiveData;
    }

    public int getCurrentHp(){
        return player.getCurrentHp();
    }

    public void setCurrentHp(int hp) {
        if (player != null) {
            player.setCurrentHp(hp);
            playerLiveData.setValue(player); // Notify observers
        }
    }

    public void reduceHp(int amount) {
        if (player != null) {
            player.setCurrentHp(Math.max(player.getCurrentHp() - amount, 0));
            playerLiveData.setValue(player);
        }
    }

    public void healPlayer(int amount){
        player.healPlayer(amount);
        playerLiveData.setValue(player);
    }

    public int getCurrentExp(){
        return player.getCurrentExp();
    }

    public int getMaxExp(){
        return player.getMaxExp();
    }

    public void setCurrentExp(int amount){
        player.setCurrentExp(amount);
        playerLiveData.setValue(player);
    }

    public void setMaxExp(int amount){
        player.setMaxExp(amount);
        playerLiveData.setValue(player);
    }

    public void gainXp(int amount){
        player.gainXp(amount);
        playerLiveData.setValue(player);
    }

    public int getCurrentLevel(){
        return player.getLevel();
    }

    public void setPlayerGold(int value){
        player.setGold(value);
        playerLiveData.setValue(player);
    }

    public void increasePlayerGold(int amount){
        player.increaseGold(amount);
        playerLiveData.setValue(player);
    }

    public int getPlayerGold(){
        return player.getGold();
    }

    public Item getInventoryItemAtIndex(int index){

        return playerLiveData.getValue().getInventoryItem(index);
    }

    public Boolean isInventoryFull(){
        return player.isInventoryFull();
    }

    public Item getEquippedItemAtIndex(int index){

        return player.equippedItems[index];
    }

    public Item[] getEquippedItems(){
        return player.equippedItems;
    }

    public Item[] getInventoryItems(){
        return playerLiveData.getValue().inventoryItems;
    }

    public Item getEmptyItem(){
        return player.empty;
    }

    public Item getEmptyWeaponItem(){
        return player.emptyWeapon;
    }

    public Item getEmptyArmorItem(){
        return player.emptyArmour;
    }

    public WeaponItem getCurrentWeapon(){
        return player.getCurrentWeapon();
    }

    public ArmorItem getCurrentArmor(){
        return player.getCurrentArmor();
    }

    public void setCurrentWeapon(WeaponItem currentWeapon){
        player.setCurrentWeapon(currentWeapon);
        playerLiveData.setValue(player);
    }

    public void setCurrentArmor(ArmorItem currentArmor){
        player.setCurrentArmor(currentArmor);
        playerLiveData.setValue(player);
    }

    public int getPlayerArmorValue(){
        return player.getArmor();
    }

    public void setPlayerDamage(int value){
        player.setDamage(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerDamage(){
        return player.getDamage();
    }

    public void setPlayerArmorValue(int value){
        player.setArmor(value);
        playerLiveData.setValue(player);
    }

    public void setPlayerItemIndex(int value){
        player.setPlayerItemIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerItemIndex(){
        return player.getPlayerItemIndex();
    }

    public void setPlayerEquipmentIndex(int value){
        player.setPlayerEquipmentIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerEquipmentIndex(){
        return player.getPlayerEquipmentIndex();
    }

    public void setPlayerShopItemIndex(int value){
        player.setShopItemIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerShopItemIndex(){
        return player.getShopItemIndex();
    }

    public void decreaseGold(int gold){
        player.decreaseGold(gold);
        playerLiveData.setValue(player);
    }


    public void increaseGold(int gold){
        player.increaseGold(gold);
        playerLiveData.setValue(player);
    }

    public void setEnemyName(String name){
        player.setEnemyName(name);
        playerLiveData.setValue(player);
    }

    public void updatePlayerLiveData() {
        playerLiveData.setValue(player); // Notify observers that the player data has changed
    }


    public String getEnemyName(){
        return player.getEnemyName();
    }
}

