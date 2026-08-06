package com.stardewtracker.model;

import java.util.*;

public class Room {
    private String name;
    private ArrayList<Bundle> bundles;

    public Room(String name, ArrayList<Bundle> bundles) {
        this.name = name;
        this.bundles = bundles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Bundle> getBundles() {
        return bundles;
    }

    public void setBundles(ArrayList<Bundle> bundles) {
        this.bundles = bundles;
    }
    
    
}
