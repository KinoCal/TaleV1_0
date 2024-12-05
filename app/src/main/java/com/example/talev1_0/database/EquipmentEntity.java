package com.example.talev1_0.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "equipment_table")
public class EquipmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String type;

    public EquipmentEntity(String name, String type){
        this.name = name;
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
