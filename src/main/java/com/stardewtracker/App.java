package com.stardewtracker;

import com.stardewtracker.repository.*;

import java.util.*;

import com.stardewtracker.model.*;

public class App 
{
    public static void main( String[] args )
    {
        ItemRepository itemRepository = new ItemRepository();
        BundleRepository bundleRepository = new BundleRepository(itemRepository);
        RoomRepository roomRepository = new RoomRepository(bundleRepository);
        SaveFileRepository saveFileRepository = new SaveFileRepository(itemRepository);


        System.out.println("=== ITEMS ===");

        for(Item item : itemRepository.getAll()){
            System.out.println(item);
        }

        System.out.println();
        System.out.println("=== FIND ITEM BY ID ===");

        Item parsnip = itemRepository.findById(1).orElseThrow();
        Item greenBean = itemRepository.findById(2).orElseThrow();

        System.out.println(parsnip);


        System.out.println();
        System.out.println("=== BUNDLES ===");

        for(Bundle bundle : bundleRepository.getAll()){
            System.out.println(bundle);

            System.out.println("Required items: ");
            for(Item item : bundle.getRequiredItems()){
                System.out.println("- "+ item.getName());
            }
            System.out.println();
        }

       

        System.out.println();
        System.out.println("=== ROOMS ===");

        for(Room room : roomRepository.getAll()){
            System.out.println(room);

            for(Bundle bundle : room.getRequiredBundles()){
                System.out.println("Bundle: "+ bundle.getName());
            }
        }

        System.out.println();
        System.out.println("=== SAVE FILE TEST ===");
        
        SaveFile save = saveFileRepository.loadSave("Save 1");

        System.out.println("Prije: ");
        System.out.println("Completed: "+ save.getCompletedItemCount());

        Item item = itemRepository.findById(1)
        .orElseThrow();

        save.markItemCompleted(item);

        System.out.println("Poslije: ");
        System.out.println("Completed: " + save.getCompletedItemCount());

        saveFileRepository.save(save);

        System.out.println("Save spremljen!");

        System.out.println();
        System.out.println("=== NOVI SAVE ===");

        BundleItem parsnipProgress = new BundleItem(parsnip, true);
        BundleItem beanProgress = new BundleItem(greenBean, false);

        List<BundleItem> progress = new ArrayList<>();

        progress.add(parsnipProgress);
        progress.add(beanProgress);

        SaveFile savefile2 = new SaveFile("Save 2", progress);

        saveFileRepository.addSave(savefile2);

    }
}
