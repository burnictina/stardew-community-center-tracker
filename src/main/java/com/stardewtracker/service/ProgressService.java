package com.stardewtracker.service;

import java.util.List;

import com.stardewtracker.model.Bundle;
import com.stardewtracker.model.Item;
import com.stardewtracker.model.Room;
import com.stardewtracker.model.SaveFile;

public class ProgressService {


    public int getCompletedCount(Bundle bundle, SaveFile save) {
        int completedCount = 0;

        for(Item item : bundle.getRequiredItems()) {
            if (save.isItemCompleted(item)) {
                completedCount++;
            }
        }

        return Math.min(completedCount, bundle.getRequiredItemCount());
    }

    public boolean isBundleCompleted(Bundle bundle, SaveFile save) {
        return getCompletedCount(bundle, save) >= bundle.getRequiredItemCount();
    }

    public int getCompletedCount(Room room, SaveFile save) {
        int completedCount = 0;

        for (Bundle bundle : room.getRequiredBundles()) {
            completedCount += getCompletedCount(bundle, save);
        }

        return completedCount;
    }

    public int getRequiredCount(Room room ){
        int requiredCount = 0;

        for(Bundle bundle : room.getRequiredBundles()) {
            requiredCount += bundle.getRequiredItemCount();
        }

        return requiredCount;
    }

    public int getCompletedCount(List<Room> rooms, SaveFile save) {
        int completedCount = 0;

        for (Room room : rooms) {
            completedCount += getCompletedCount(room, save);
        }

        return completedCount;
    }

    public int getRequiredCount(List<Room> rooms) {
        int requiredCount = 0;

        for (Room room : rooms) {
            requiredCount += getRequiredCount(room);
        }

        return requiredCount;
    }
}
