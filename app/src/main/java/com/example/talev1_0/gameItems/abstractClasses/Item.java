package com.example.talev1_0.gameItems.abstractClasses;


public abstract class Item implements com.example.talev1_0.gameItems.interfaces.Item {

    private int itemIndex;
    private String name;
    private String type;
    private String rarity;
    private int price;
    private int quantity;

    public Item(int itemIndex, String name, String type, String rarity, int price, int quantity) {
        this.itemIndex = itemIndex;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {

    }

    @Override
    public String ToString() {

        return this.name + " " + "(" + this.quantity + ")";
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public String getRarityName() {
        return rarity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getItemIndex() {
        return itemIndex;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
