package com.example.talev1_0.NPCS;


import com.example.talev1_0.Factories.ItemFactories.Factories;

public class TavernShopKeeper extends SuperShopKeeper {

    Factories factories = new Factories();

    public TavernShopKeeper() {


        setShopKeeperName("tavernShop");

        AddItemToShop(0, factories.createItem("consumable", "Hp Potion", 1));
        AddItemToShop(1, factories.createItem("weapon", "Sword", 1));
        AddItemToShop(2, factories.createItem("armor", "Cloth Body", 1));
        AddItemToShop(3, factories.createItem("weapon", "Dagger", 1));

    }


}
