package com.stardewtracker.model;

import java.util.*;

public class Room {
    private Integer id;
    private String name;
    private List<Bundle> requiredBundles;

    public Room(Integer id, String name, List<Bundle> requiredBundles) {
        this.id = id;
        this.name = name;
        this.requiredBundles = requiredBundles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Bundle> getRequiredBundles() {
        return requiredBundles;
    }

    public void setRequiredBundles(List<Bundle> requiredBundles) {
        this.requiredBundles = requiredBundles;
    }
}
