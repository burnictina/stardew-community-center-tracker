package com.stardewtracker.repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

import com.stardewtracker.enums.*;
import com.stardewtracker.model.*;

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

        String json = readJson("src/main/resources/items.json");
        
        return parseItems(json);
    }

    private List<Item> parseItems(String json){
        List<Item> itemList = new ArrayList<>();

        try{
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonArray = mapper.readTree(json);

            for(JsonNode obJect : jsonArray){
                Item item = parseItem(obJect);
                itemList.add(item);
            }
        } catch(IOException e) {
            throw new RuntimeException("Greška kod čitanja JSON podataka", e);
        }

        return itemList;
    }

    private Item parseItem(JsonNode object){
        int id = object.get("id").asInt();
        String name = object.get("name").asText();
        String seasonString = object.get("season").asText();
        String typeString = object.get("type").asText();

        Season season = Season.valueOf(seasonString);

        ItemType type = ItemType.valueOf(typeString);

        return new Item(id,name,type,season);
    }

    private static String readJson(String filePath){
        try{
            return Files.readString(Path.of(filePath));
        } catch (IOException e){
            throw new RuntimeException("Ne mogu učitati JSON datoteku", e);
        }
    }

}
