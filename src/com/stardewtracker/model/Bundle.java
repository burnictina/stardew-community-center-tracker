package com.stardewtracker.model;

import java.util.*;

/**
 * 
 * Bundle
 * 
 * Bundle u koji se stavljaju Itemi.
 */

public class Bundle {
    private String name;
    private ArrayList<BundleItem> requiredItems;
    
    public Bundle(String name, ArrayList<BundleItem> requiredItems) {
        this.name = name;
        this.requiredItems = requiredItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BundleItem> getRequiredItems() {
        return requiredItems;
    }
    

    public void addItem(BundleItem newItem){
        requiredItems.add(newItem);
    }

    public void setRequiredItems(ArrayList<BundleItem> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public Boolean isCompleted(){
        int brojac = 0;
       for(BundleItem i : requiredItems){
        if (i.getCompleted()){
            brojac+=1;
        }
       }
        if(brojac == requiredItems.size()){
            return true;
        } else{
            return false;
        }
    }

}
