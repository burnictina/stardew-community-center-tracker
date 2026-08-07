package com.stardewtracker;


import java.util.*;
import com.stardewtracker.enums.*;
import com.stardewtracker.model.*;


public class App {
    public static void main(String[] args) throws Exception {

        Item parsnip = new Item(1, "Parsnip", ItemType.CROPS, Season.SPRING);

        BundleItem bundleItem = new BundleItem(parsnip, false);

        SaveFile farma1 = new SaveFile("Farma 1", new ArrayList<>());

        farma1.addBundleItem(bundleItem);

        System.out.println("Prije završavanja: " +
            farma1.isItemCompleted(parsnip)
        );

        farma1.markItemCompleted(parsnip);

        System.out.println("Nakon završavanja: "
            + farma1.isItemCompleted(parsnip)
        );
      
    }
}
