package com.stardewtracker;


import java.util.*;
import com.stardewtracker.enums.*;
import com.stardewtracker.model.*;


public class App {
    public static void main(String[] args) throws Exception {

       Item parsnip = new Item("Parsnip", ItemType.CROPS, Season.SPRING);

       BundleItem bundleItem = new BundleItem(parsnip, true);

       Bundle springsBundle = new Bundle("Spring corps bundle", new ArrayList<>());

       springsBundle.addItem(bundleItem);

       SaveFile newSaveFile = new SaveFile("Save1", new ArrayList<>());

       Room pantry = new Room("Pantry", new ArrayList<>());


       System.out.println("Farm: " + newSaveFile.getName());
       System.out.println("Room: " +pantry.getName() );
       System.out.println("Bundle: " + springsBundle.getName());
       System.out.println("Completed: " + springsBundle.isCompleted());
     
    }
}
