package com.stardewtracker.model;

import com.stardewtracker.enums.*;
import com.stardewtracker.utils.Identifiable;


public class Item implements Identifiable{
    private int id;
    private String name;
    private ItemType type;
    private Season season;

    public Item(int id, String name, ItemType type, Season season) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.season = season;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
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
        Item other = (Item) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
