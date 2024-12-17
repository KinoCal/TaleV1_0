package com.example.talev1_0.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(PlayerEntity player);

    @Query("SELECT * FROM player_table WHERE id = :playerId LIMIT 1")
    PlayerEntity getPlayerById(int playerId);
}



