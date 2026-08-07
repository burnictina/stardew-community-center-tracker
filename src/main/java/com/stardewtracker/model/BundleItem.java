package com.stardewtracker.model;

public class BundleItem {
    private Item item;
    private Boolean completed;

    public BundleItem(Item item, Boolean completed) {
        this.item = item;
        this.completed = completed;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
