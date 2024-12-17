package com.example.talev1_0.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class PlayerEntity {

    @PrimaryKey
    private int id;

    private String username;
    private String password;
    private int level;
    private int currentHp;
    private int maxHp;
    private int strengthStat;
    private int defenceStat;
    private int damage;
    private int armor;
    private int currentExp;
    private int maxExp;
    private int gold;
    private int statPoints;

    // Constructor
    public PlayerEntity(
            int id,
            String username,
            String password,
            int level,
            int currentHp,
            int maxHp,
            int strengthStat,
            int defenceStat,
            int damage,
            int armor,
            int currentExp,
            int maxExp,
            int gold,
            int statPoints) {

        this.id = id;
        this.level = level;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.strengthStat = strengthStat;
        this.defenceStat = defenceStat;
        this.damage = damage;
        this.armor = armor;
        this.currentExp = currentExp;
        this.maxExp = maxExp;
        this.gold = gold;
        this.statPoints = statPoints;
        this.username = username;
        this.password = password;
    }

    // No-argument constructor (required for Retrofit)
    public PlayerEntity() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }


    public int getStrengthStat() {
        return strengthStat;
    }

    public void setStrengthStat(int strengthStat) {
        this.strengthStat = strengthStat;
    }

    public int getDefenceStat() {
        return defenceStat;
    }

    public void setDefenceStat(int defenceStat) {
        this.defenceStat = defenceStat;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }


    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
