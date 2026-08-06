package com.stardewtracker.model;

import java.util.*;

/**
 * 
 * SaveFile
 * 
 * Save file koji pamti jedan save za bundle
 */

public class SaveFile {
    private String name;
    private List<BundleItem> bundleProgress;
    
    public SaveFile(String name, List<BundleItem> bundleProgress) {
        this.name = name;
        this.bundleProgress = bundleProgress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BundleItem> getBundleProgress() {
        return bundleProgress;
    }

    public void setBundleProgress(List<BundleItem> bundleProgress) {
        this.bundleProgress = bundleProgress;
    }

    private Optional<BundleItem> findBundleItem(Item item){
        return bundleProgress.stream()
        .filter(i->i.getItem().equals(item))
        .findFirst();
    }
   
    public void markItemCompleted(Item item){
        findBundleItem(item)
        .ifPresent(i->i.setCompleted(true));
    }
   
    public boolean isItemCompleted(){
        return true;
    }

    public BundleItem getCompletedItem(){
        return bundleProgress.getFirst();
    }
    
}
