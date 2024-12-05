package com.example.talev1_0.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(PlayerEntity playerEntity);

    @Query("SELECT * FROM player_table WHERE id = :id LIMIT 1")
    PlayerEntity getPlayerById(int id);
}

