package com.stardewtracker.model;

import java.util.*;

import com.stardewtracker.utils.Identifiable;

public class Bundle implements Identifiable{
    private int id;
    private String name;
    private List<Item> requiredItems;
    
    public Bundle(int id, String name, List<Item> requiredItems) {
        this.id = id;
        this.name = name;
        this.requiredItems = requiredItems;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }
    

    public void addItem(Item newItem){
        requiredItems.add(newItem);
    }

    public void setRequiredItems(List<Item> requiredItems) {
        this.requiredItems = requiredItems;
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
