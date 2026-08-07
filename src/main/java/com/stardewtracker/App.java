package com.stardewtracker;

import com.stardewtracker.repository.*;
import com.stardewtracker.model.*;;

public class App 
{
    public static void main( String[] args )
    {
        ItemRepository itemRepository = new ItemRepository();
        BundleRepository bundleRepository = new BundleRepository(itemRepository);
        RoomRepository roomRepository = new RoomRepository(bundleRepository);

        System.out.println("=== ITEMS ===");

        for(Item item : itemRepository.getAll()){
            System.out.println(item);
        }

        System.out.println();
        System.out.println("=== BUNDLES ===");

        for(Bundle bundle : bundleRepository.getAll()){
            System.out.println(bundle);
        }

        System.out.println();
        System.out.println("=== ROOMS ===");

        for(Room room : roomRepository.getAll()){
            System.out.println(room);
        }


    }
}
