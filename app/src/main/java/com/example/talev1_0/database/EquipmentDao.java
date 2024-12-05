package com.example.talev1_0.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EquipmentEntity> equipmentItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipment(EquipmentEntity equipmentEntity);

    @Query("SELECT * FROM equipment_table WHERE id = :id LIMIT 1")
    EquipmentEntity getEquipmentById(int id);
}
