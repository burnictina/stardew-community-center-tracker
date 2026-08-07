package com.stardewtracker.model;

import java.util.*;


public class SaveFile {
    private String name;
    private List<BundleItem> bundleProgress;
    
    public SaveFile(String name, List<BundleItem> bundleProgress) {
        this.name = name;
        this.bundleProgress = bundleProgress != null
        ? bundleProgress
        : new ArrayList<>();
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

    public void addBundleItem(BundleItem newBundleItem){
        bundleProgress.add(newBundleItem);
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

    public void markItemUncompleted(Item item){
        BundleItem bundleItem = findBundleItem(item)
        .orElseThrow(() ->
            new IllegalArgumentException("Item nije pronađen")
        );
        bundleItem.setCompleted(false);
    }
   
    public boolean isItemCompleted(Item item){
        return findBundleItem(item)
        .map(BundleItem::getCompleted)
        .orElse(false);
    }

    public long getCompletedItemCount(){
        
        return bundleProgress
        .stream()
        .filter(BundleItem::getCompleted)
        .count();
    }

    public long getTotalItemCount(){
        return bundleProgress
        .stream()
        .count();
    }
}
