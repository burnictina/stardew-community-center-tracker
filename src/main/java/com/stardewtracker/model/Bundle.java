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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bundle other = (Bundle) obj;
        if (id != other.id)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Bundle [id = " + id + ", name = " + name + "]";
    }

    
    
}
