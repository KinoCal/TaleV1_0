package com.example.talev1_0.player;

import com.example.talev1_0.database.InventoryEntity;
import com.example.talev1_0.database.PlayerEntity;

import java.util.List; // Import List

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlayerService {

    @POST("/players/register")
    Call<Void> registerPlayer(@Body PlayerEntity player);

    @POST("/players/login")
    Call<PlayerEntity> loginPlayer(@Body PlayerEntity player);

    @PUT("/players/update")
    Call<Void> updatePlayer(@Body PlayerEntity player);

    @POST("/inventories/register")
    Call<Void> registerInventory(@Body InventoryEntity inventory);

    @GET("/inventories/load/{username}")
        // Updated to match the new endpoint
    Call<List<InventoryEntity>> loadInventory(@Path("username") String username);

    @PUT("/inventories/update")
    Call<Void> updateInventory(@Body List<InventoryEntity> updatedInventoryList);


    @GET("/players/{username}")
    Call<PlayerEntity> getPlayer(@Path("username") String username);
}
