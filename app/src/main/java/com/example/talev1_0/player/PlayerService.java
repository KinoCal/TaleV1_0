package com.example.talev1_0.player;

import com.example.talev1_0.database.PlayerEntity;

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

    @GET("/api/ping")
    Call<String> ping();

    @PUT("/players/update")
    Call<Void> updatePlayer(@Body PlayerEntity player);

    @GET("/players/{username}")
    Call<PlayerEntity> getPlayer(@Path("username") String username);
}
