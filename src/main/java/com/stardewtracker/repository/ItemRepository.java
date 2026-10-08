package com.stardewtracker.repository;

import java.util.*;
import com.fasterxml.jackson.databind.*;

import com.stardewtracker.enums.*;
import com.stardewtracker.model.*;

public class ItemRepository extends BaseRepository<Item> {

    public ItemRepository() {
        super(loadItems());
    }


    private static List<Item> loadItems(){

        String json = JsonReader.readJson("/data/items.json");
        
        return JsonRepositoryHelper.parseList(json, ItemRepository::parseItem);
    }

    private static Item parseItem(JsonNode object){
        int id = object.get("id").asInt();
        String name = object.get("name").asText();
        String typeString = object.get("type").asText();

        ItemType type = ItemType.valueOf(typeString);

        List<Season> seasons = new ArrayList<>();

        JsonNode seasonsNode = object.get("seasons");

        for(JsonNode seasonNode : seasonsNode) {
            String seasonString = seasonNode.asText();
            Season season = Season.valueOf(seasonString);

            seasons.add(season);
        }

        return new Item(id,name,type,seasons);
    }


}
