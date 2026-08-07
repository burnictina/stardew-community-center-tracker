package com.stardewtracker.repository;

import java.util.*;
import com.fasterxml.jackson.databind.*;

import com.stardewtracker.model.*;

public class BundleRepository extends BaseRepository<Bundle>{
    private ItemRepository itemRepository;

    public BundleRepository(ItemRepository itemRepository) {
        super(new ArrayList<>());
        this.itemRepository = itemRepository;
        this.entities = loadBundles();
    }

    private List<Bundle> loadBundles(){
        String json = JsonReader.readJson("src/main/resources/bundles.json");
        return JsonRepositoryHelper.parseList(json, this::parseBundle);
    }

    private Bundle parseBundle(JsonNode object){
        int id = object.get("id").asInt();
        String name = object.get("name").asText();
        
        List<Item> requiredItems = new ArrayList<>();

        JsonNode requiredItemsNode = object.get("requiredItems");

        for (JsonNode itemIdNode : requiredItemsNode) {
            int itemId = itemIdNode.asInt();

            Item item = itemRepository.findById(itemId)
            .orElseThrow(()-> 
                new RuntimeException("Item ne postoji: " + itemId)
            );
            
            requiredItems.add(item);
        }

        return new Bundle(id, name, requiredItems);
    }

    @Override
    protected int getId(Bundle bundle) {
        return bundle.getId();
    }




    @Override
    protected String getName(Bundle bundle) {
       return bundle.getName();
    }


}
