package com.stardewtracker.repository;

import java.util.*;
import com.fasterxml.jackson.databind.*;

import com.stardewtracker.model.*;

public class RoomRepository extends BaseRepository<Room>{
    private BundleRepository bundleRepository;

    public RoomRepository(BundleRepository bundleRepository){
        super(new ArrayList<>());
        this.bundleRepository = bundleRepository;
        this.entities = loadRooms();
    }

    private List<Room> loadRooms(){
        String json = JsonReader.readJson("src/main/resources/room.json");
        return JsonRepositoryHelper.parseList(json, this::parseRoom);
    }
    
    private Room parseRoom(JsonNode object) {
        int id = object.get("id").asInt();
        String name = object.get("name").asText();

        List<Bundle> requiredBundles = new ArrayList<>();

        JsonNode requiredBundlesNode = object.get("requiredBundles");

        for(JsonNode bundleIdNode : requiredBundlesNode) {
            int bundleId = bundleIdNode.asInt();

            Bundle bundle = bundleRepository.findById(bundleId)
            .orElseThrow(()-> new RuntimeException("Bundle ne postoji: " + bundleId));

            requiredBundles.add(bundle);
        }

        return new Room(id, name, requiredBundles);
    }

    @Override
    protected int getId(Room room) {
        return room.getId();
    }

    @Override
    protected String getName(Room room) {
       return room.getName();
    }
}
