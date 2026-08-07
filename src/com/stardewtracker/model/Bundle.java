package com.stardewtracker.model;

import java.util.*;

/**
 * 
 * Bundle
 * 
 * Bundle u koji se stavljaju Itemi.
 */

public class Bundle {
    private Integer id;
    private String name;
    private List<Item> requiredItems;
    
    public Bundle(Integer id, String name, List<Item> requiredItems) {
        this.id = id;
        this.name = name;
        this.requiredItems = requiredItems;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    

}
