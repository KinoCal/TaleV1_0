package com.example.talev1_0.Factories;

import com.example.talev1_0.gameItems.abstractClasses.Item;

public interface ItemFactory {
    Item createItem(String itemName);
}
