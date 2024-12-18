package com.example.talev1_0.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory_table")
public class InventoryEntity {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String name;
    private String type;
    private int quantity;


    // Constructor
    public InventoryEntity(String username, String name, String type, int quantity) {

        this.username = username;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    public InventoryEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
