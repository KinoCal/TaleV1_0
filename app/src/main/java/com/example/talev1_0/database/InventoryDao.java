package com.example.talev1_0.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<InventoryEntity> inventoryItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInventory(InventoryEntity inventoryEntity);

    @Query("SELECT * FROM inventory_table WHERE id = :id LIMIT 1")
    InventoryEntity getInventoryById(int id);
}
