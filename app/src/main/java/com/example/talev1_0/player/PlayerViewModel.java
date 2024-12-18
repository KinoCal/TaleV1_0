package com.example.talev1_0.player;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerViewModel extends AndroidViewModel {
    private final MutableLiveData<Player> playerLiveData = new MutableLiveData<>();
    private Player player; // Local reference to avoid redundant calls
    private final Application application;
    private Factories factories;
    private MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    PlayerService playerService;

    public PlayerViewModel(Application application) {
        super(application);
        player = new Player(); // Initialize the Player
        playerLiveData.setValue(player);
        factories = new Factories();
        this.application = application;
        playerService = RetrofitClient.getInstance().create(PlayerService.class);

        //initializePlayer(1);
    }
/*
    @Override
    protected void onCleared() {
        super.onCleared();
        savePlayerToDatabase(player, 1); // Save to Room when ViewModel is cleared
    }

 */

    public void registerPlayer(String username, String password) {
        new Thread(() -> {
            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setUsername(username);
            playerEntity.setPassword(password);

            playerService.registerPlayer(playerEntity).enqueue(new Callback<>() {

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        messageLiveData.postValue("Registration successful you may login now!");
                        System.out.println("Registration successful");
                        registerInventory();
                    } else {
                        messageLiveData.postValue("Registration failed: Username already exists");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    messageLiveData.postValue("Registration failed: " + t.getMessage());
                }
            });
        }).start();
        Log.d("PlayerDebug", "Username during registration: " + player.getUsername());

    }

    public void loginPlayer(String username, String password) {
        new Thread(() -> {
            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setUsername(username);
            playerEntity.setPassword(password);

            playerService.loginPlayer(playerEntity).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<PlayerEntity> call, Response<PlayerEntity> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        messageLiveData.postValue("Login successful");

                        // Convert response body to Player object
                        player = mapEntityToPlayer(response.body());

                        loadInventory();
                        // Save to local Room database (overwriting existing data)
                        savePlayerToDatabase(player, 1);

                        // Update LiveData to notify UI observers
                        playerLiveData.postValue(player);
                    } else {
                        messageLiveData.postValue("Login failed: Incorrect username or password");
                    }
                }

                @Override
                public void onFailure(Call<PlayerEntity> call, Throwable t) {
                    messageLiveData.postValue("Login failed: " + t.getMessage());
                }
            });
        }).start();
        Log.d("PlayerDebug", "Username after login: " + player.getUsername());

    }

    public void registerInventory() {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setUsername(player.getUsername());

        playerService.registerInventory(inventoryEntity).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("successfully registered inventory");
                } else {
                    System.out.println("inventory failed to register");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage() + "failed inventory**");
            }
        });
    }

    public void loadInventory() {

        playerService.loadInventory(player.getUsername()).enqueue(new Callback<List<InventoryEntity>>() {
            @Override
            public void onResponse(Call<List<InventoryEntity>> call, Response<List<InventoryEntity>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < 30; i++) {
                        player.inventoryItems.set(i, factories.createItem(response.body().get(i).getType(), response.body().get(i).getName(), response.body().get(i).getQuantity()));

                    }
                    System.out.println("inside load inventory");
                    saveInventoryToDatabase(player);
                    playerLiveData.postValue(player);
                } else {
                    Log.d("Player_inventory", "error loading inventory in method");
                }
            }

            @Override
            public void onFailure(Call<List<InventoryEntity>> call, Throwable t) {

            }
        });
    }


    // Initialize player from the database or create a new one
    public void initializePlayer(int playerId) {
        new Thread(() -> {
            PlayerEntity playerEntity = DatabaseClient.getInstance(application).getAppDatabase().playerDao().getPlayerById(playerId);
            List<InventoryEntity> inventoryEntities = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getEntireInventory(player.getUsername());

            if (playerEntity != null) {
                // Existing player found, map it to the Player object
                System.out.println("existing player found initializing player");
                player = mapEntityToPlayer(playerEntity);
                loadInventory();

                //loadInventory();
            } else {
                // No player found, create a new one
                System.out.println("no player found initializing player");
                player = new Player(); // Initialize new player data
                //initializeEquipmentInDatabase();
                //initializeInventoryInDatabase();
                savePlayerToDatabase(player, 1); // Save to database with the given ID


            }

            playerLiveData.postValue(player); // Update LiveData
        }).start();
    }


    private void mapEntityToInventory(List<InventoryEntity> inventoryEntities, Player player) {

        List<Item> tempInventory = new ArrayList<>();
        for (int i = 0; i < inventoryEntities.size(); i++) {

            player.inventoryItems.set(i, factories.createItem(inventoryEntities.get(i).getType(), inventoryEntities.get(i).getName(), inventoryEntities.get(i).getQuantity()));

        }

    }

    // Helper to map InventoryEntity to players inventory, playerEntity to player and equipmentEntity to player
    private Player mapEntityToPlayer(PlayerEntity entity) {

        Player player = new Player();
        if (entity.getUsername() != null && entity.getPassword() != null) {
            player.setUsername(entity.getUsername());
            player.setPassword(entity.getPassword());
        } else {
            Log.e("MAP_ENTITY", "PlayerEntity has null username or password");
        }
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
        player.setStatPoints(entity.getStatPoints());

        return player;
    }

    public void saveInventoryToDatabase(Player player) {
        System.out.println("inside save inventory to database");
        new Thread(() -> {
            List<InventoryEntity> inventoryEntities = new ArrayList<>();

            // Save the player's inventory data
            for (int i = 0; i < 30; i++) {
                InventoryEntity tempEntity = new InventoryEntity(); // Create a new object for each item
                tempEntity.setUsername(player.getUsername());
                tempEntity.setName(player.getInventoryItem(i).getName());
                tempEntity.setType(player.getInventoryItem(i).getType());
                tempEntity.setQuantity(player.getInventoryItem(i).getQuantity());
                inventoryEntities.add(tempEntity);
            }

            System.out.println(inventoryEntities.get(0).getUsername());
            System.out.println(inventoryEntities.get(0).getName());

            // Update the database with each inventory item
            DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().insertAll(inventoryEntities);

            System.out.println(inventoryEntities.size() + " inventory entity size");
            // Push updated data to Spring Boot backend
            playerService.updateInventory(inventoryEntities).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("SaveInventory", "Inventory data saved to Spring");
                    } else {
                        Log.e("SaveInventory", "Failed to save inventory to Spring");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("SaveInventory", "Error: saving inventory - " + t.getMessage());
                }
            });
        }).start();
    }


    // Save player to the database
    public void savePlayerToDatabase(Player player, int id) {
        Log.d("PlayerInstance", "Player object: " + player);
        Log.d("PlayerInstance", "Username: " + player.getUsername());

        new Thread(() -> {
            PlayerEntity playerEntity = new PlayerEntity(
                    id,
                    player.getUsername(),
                    player.getPassword(),// Ensure the correct ID is assigned
                    player.getLevel(),
                    player.getCurrentHp(),
                    player.getMaxHp(),
                    player.getStrengthStat(),
                    player.getDefenceStat(),
                    player.getDamage(),
                    player.getArmor(),
                    player.getCurrentExp(),
                    player.getMaxExp(),
                    player.getGold(),
                    player.getStatPoints()
            );
            DatabaseClient.getInstance(application).getAppDatabase().playerDao().insertPlayer(playerEntity);

            // Push updated data to Spring Boot backend
            playerService = RetrofitClient.getInstance().create(PlayerService.class);
            playerService.updatePlayer(playerEntity).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("SavePlayer", "Player data saved to spring");
                    } else {
                        Log.e("SavePlayer", "Failed to save player to spring");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("SavePlayer", "Error: saving player" + t.getMessage());
                }
            });

            /*
            // Save the player's inventory data
            for (int i = 0; i < player.inventoryItems.size(); i++) {
                // Retrieve the corresponding InventoryEntity from the database
                InventoryEntity inventoryEntity = DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().getInventoryById(i + 1); // Assuming IDs start at 1 and match indices
                // Get the item from the player's inventory
                Item item = player.getInventoryItem(i);

                // Update the InventoryEntity with the item's data
                inventoryEntity.setName(item.getName());
                inventoryEntity.setType(item.getType());
                inventoryEntity.setQuantity(item.getQuantity());

                // Update the database with each inventory item
                DatabaseClient.getInstance(application).getAppDatabase().inventoryDao().insertInventory(inventoryEntity);
            }

            // Save the player's equipment data
            for (int j = 0; j < player.equippedItems.length; j++) {
                // Retrieve the corresponding InventoryEntity from the database
                EquipmentEntity equipmentEntity = DatabaseClient.getInstance(application)
                        .getAppDatabase()
                        .equipmentDao()
                        .getEquipmentById(j + 1); // Assuming IDs start at 1 and match indices

                // Get the item from the player's equipment inventory
                Item item = player.getEquippedItem(j);

                System.out.println(item.getName() + "database test");
                // Update the InventoryEntity with the item's data
                equipmentEntity.setName(item.getName());
                equipmentEntity.setType(item.getType());

                // Update the database
                DatabaseClient.getInstance(application).getAppDatabase().equipmentDao().insertEquipment(equipmentEntity);
            }
*/

        }).start();
    }


    // Save player data to the database
    public void savePlayer() {
        Player currentPlayer = playerLiveData.getValue();
        System.out.println(currentPlayer);
        savePlayerToDatabase(currentPlayer, 1);


    }


    public LiveData<Player> getPlayerLiveData() {
        return playerLiveData;
    }

    public int getCurrentHp() {
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

    public void healPlayer(int amount) {
        player.healPlayer(amount);
        playerLiveData.setValue(player);
    }

    public int getCurrentExp() {
        return player.getCurrentExp();
    }

    public int getMaxExp() {
        return player.getMaxExp();
    }

    public void setCurrentExp(int amount) {
        player.setCurrentExp(amount);
        playerLiveData.setValue(player);
    }

    public void setMaxExp(int amount) {
        player.setMaxExp(amount);
        playerLiveData.setValue(player);
    }

    public void gainXp(int amount) {
        player.gainXp(amount);
        playerLiveData.setValue(player);
    }


    public int getCurrentLevel() {
        return player.getLevel();
    }

    public void setPlayerGold(int value) {
        player.setGold(value);
        playerLiveData.setValue(player);
    }

    public void increasePlayerGold(int amount) {
        player.increaseGold(amount);
        playerLiveData.setValue(player);
    }

    public int getPlayerGold() {
        return player.getGold();
    }

    public Item getInventoryItemAtIndex(int index) {

        return playerLiveData.getValue().getInventoryItem(index);
    }

    public Boolean isInventoryFull() {
        return player.isInventoryFull();
    }

    public Item getEquippedItemAtIndex(int index) {

        return player.equippedItems[index];
    }

    public Item[] getEquippedItems() {
        return player.equippedItems;
    }

    public List<Item> getInventoryItems() {
        return playerLiveData.getValue().inventoryItems;
    }

    public Item getEmptyItem() {
        return player.empty;
    }

    public Item getEmptyWeaponItem() {
        return player.emptyWeapon;
    }

    public Item getEmptyArmorItem() {
        return player.emptyArmour;
    }

    public WeaponItem getCurrentWeapon() {
        return player.getCurrentWeapon();
    }

    public ArmorItem getCurrentArmor() {
        return player.getCurrentArmor();
    }

    public void setCurrentWeapon(WeaponItem currentWeapon) {
        player.setCurrentWeapon(currentWeapon);
        playerLiveData.setValue(player);
    }

    public void setCurrentArmor(ArmorItem currentArmor) {
        player.setCurrentArmor(currentArmor);
        playerLiveData.setValue(player);
    }

    public int getPlayerArmorValue() {
        return player.getArmor();
    }

    public void setPlayerDamage(int value) {
        player.setDamage(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerDamage() {
        return player.getDamage();
    }

    public void setPlayerArmorValue(int value) {
        player.setArmor(value);
        playerLiveData.setValue(player);
    }

    public void setPlayerItemIndex(int value) {
        player.setPlayerItemIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerItemIndex() {
        return player.getPlayerItemIndex();
    }

    public void setPlayerEquipmentIndex(int value) {
        player.setPlayerEquipmentIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerEquipmentIndex() {
        return player.getPlayerEquipmentIndex();
    }

    public void setPlayerShopItemIndex(int value) {
        player.setShopItemIndex(value);
        playerLiveData.setValue(player);
    }

    public int getPlayerShopItemIndex() {
        return player.getShopItemIndex();
    }

    public void decreaseGold(int gold) {
        player.decreaseGold(gold);
        playerLiveData.setValue(player);
    }


    public void increaseGold(int gold) {
        player.increaseGold(gold);
        playerLiveData.setValue(player);
    }

    public void increaseStatPoints(int amount) {
        player.increaseStatPoints(amount);
        playerLiveData.setValue(player);
    }

    public void increaseStrengthStat(int amount) {
        player.increaseStrengthStat(amount);
        playerLiveData.setValue(player);
    }

    public void increaseDefenceStat(int amount) {
        player.increaseDefenceStat(amount);
        playerLiveData.setValue(player);
    }

    public void setEnemyName(String name) {
        player.setEnemyName(name);
        playerLiveData.setValue(player);
    }

    public void updatePlayerLiveData() {
        playerLiveData.setValue(player); // Notify observers that the player data has changed
    }


    public String getEnemyName() {
        return player.getEnemyName();
    }

    public String getUserName() {
        return player.getUsername();
    }

    public void setUserName(String username) {
        player.setUsername(username);
        playerLiveData.setValue(player);
    }

    public String getPassword() {
        return player.getPassword();
    }

    public void setPassword(String password) {
        player.setPassword(password);
        playerLiveData.setValue(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setDamageDealt(int amount) {
        player.setDamageDealt(amount);
        playerLiveData.setValue(player);
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }
/*
    public void increaseInventorySize(int additionalSlots) {
        new Thread(() -> {
            if (player != null) {
                int currentSize = player.getInventorySize();
                int newSize = currentSize + additionalSlots;

                // Update inventory size
                player.setInventorySize(newSize);

                // Add empty items to the inventory list
                for (int i = currentSize; i < newSize; i++) {
                    player.inventoryItems.add(player.empty);
                    DatabaseClient.getInstance(application).getAppDatabase().inventoryDao()
                            .insertInventory(new InventoryEntity("empty", "empty", 1));
                }

                // Notify observers
                playerLiveData.postValue(player);
            }
        }).start();
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

 */

}

