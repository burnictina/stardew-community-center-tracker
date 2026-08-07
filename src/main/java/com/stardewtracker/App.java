package com.stardewtracker;

import com.stardewtracker.repository.*;

import java.util.ArrayList;

import com.stardewtracker.model.*;;

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

        SaveFile save = new SaveFile("Save 2", new ArrayList<>());

        save.addBundleItem(new BundleItem(parsnip, false));

        System.out.println("Save name: " + save.getName());

        System.out.println("Completed items: " + save.getCompletedItemCount());

        save.markItemCompleted(parsnip);

        System.out.println("After completing: ");

        System.out.println("Completed items: "+ save.getCompletedItemCount());

        System.out.println();
        System.out.println("=== SAVE REPOSITORY ===");

        saveFileRepository.addSave(save);

        for(SaveFile saveFile : saveFileRepository.getAllSaves()){
            System.out.println(saveFile.getName());

            System.out.println("Progress: "+ saveFile.getCompletedItemCount() + "/"+saveFile.getTotalItemCount());
        }


    }
}
