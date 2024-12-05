package com.example.talev1_0.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlayerEntity.class, InventoryEntity.class, EquipmentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    public abstract InventoryDao inventoryDao();
    public abstract EquipmentDao equipmentDao();
}
