package com.stardewtracker.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

import javax.management.RuntimeErrorException;

import com.stardewtracker.enums.ItemType;
import com.stardewtracker.enums.Season;
import com.stardewtracker.model.*;

/**
 * Repository odgovaran za učitavanje i dohvat Item objekata.
 * 
 * ItemRepository
 */

public class ItemRepository {
    private List<Item> items; 

    public ItemRepository() {
        this.items = loadItems();
    }

    public List<Item> getAllItems(){
        return new ArrayList<>(items);
    }

    public Optional<Item> findItemById(Item item){
        return items.stream()
        .filter(i -> i.equals(item))
        .findFirst();
    }

    public Optional<Item> findItemByName(String name){
        return items.stream()
        .filter(i->i.getName().equals(name))
        .findFirst();
    }

    private List<Item> loadItems(){

        String json = readJson("resources/items.json");
        
        return parseItems(json);
    }

    private List<Item> parseItems(String json){
        JsonArray jsonArray = JsonParser.parseString(json)
        .getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            Item item = parseItem(object);
            items.add(item);
        }
    }

    private Item parseItem(JsonObject object){
        int id = object.get("id").getAsInt();
        String name = object.get("name").getAsString();
        String seasonString = object.get("season").getAsString();
        String typeString = object.get("type").getAsString();

        Season season = Season.valueOf(seasonString);

        ItemType type = ItemType.valueOf(typeString);

        return new Item(id,name,type,season);
    }

    private static String readJson(String filePath){
        try{
            return Files.readString(Path.of(filePath));
        } catch (IOException e){
            throw new RuntimeErrorException("Ne mogu učitati JSON datoteku", e);
        }
    }


}
