package com.stardewtracker.model;

import com.stardewtracker.enums.*;

/**
 * 
 * Item
 * 
 * predstavlja Item objekt koji je sam predmet.
 */

public class Item {
    private String name;
    private ItemType type;
    private Season season;

    public Item(String name, ItemType type, Season season) {
        this.name = name;
        this.type = type;
        this.season = season;
    }

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

    

    
}
