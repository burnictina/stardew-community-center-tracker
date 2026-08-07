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
        Room other = (Room) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Room [id = " + id + ", name = " + name + "]";
    }

    
    
}
