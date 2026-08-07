package com.stardewtracker.repository;

import java.util.*;
import com.fasterxml.jackson.databind.*;

import com.stardewtracker.enums.*;
import com.stardewtracker.model.*;

public class ItemRepository extends BaseRepository<Item> {

    public ItemRepository() {
        super(loadItems());
    }

    @Override
    protected int getId(Item item){
        return item.getId();
    }

    @Override
    protected String getName(Item item){
        return item.getName();
    }

    private static List<Item> loadItems(){

        String json = JsonReader.readJson("src/main/resources/items.json");
        
        return JsonRepositoryHelper.parseList(json, ItemRepository::parseItem);
    }

    private static Item parseItem(JsonNode object){
        int id = object.get("id").asInt();
        String name = object.get("name").asText();
        String seasonString = object.get("season").asText();
        String typeString = object.get("type").asText();

        Season season = Season.valueOf(seasonString);

        ItemType type = ItemType.valueOf(typeString);

        return new Item(id,name,type,season);
    }


}
