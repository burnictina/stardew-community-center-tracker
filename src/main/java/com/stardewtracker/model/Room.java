package com.stardewtracker.model;

import java.util.*;

import com.stardewtracker.utils.Identifiable;

public class Room implements Identifiable {
    private int id;
    private String name;
    private List<Bundle> requiredBundles;

    public Room(int id, String name, List<Bundle> requiredBundles) {
        this.id = id;
        this.name = name;
        this.requiredBundles = requiredBundles;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Bundle> getRequiredBundles() {
        return requiredBundles;
    }

    public void setRequiredBundles(List<Bundle> requiredBundles) {
        this.requiredBundles = requiredBundles;
    }
}
