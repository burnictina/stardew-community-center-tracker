package com.stardewtracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stardewtracker.model.*;

public class SaveFileRepository {
    private List<SaveFile> saves;
    private ItemRepository itemRepository;

    public SaveFileRepository(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
        this.saves = loadSaves();
    }

    public List<SaveFile> getAllSaves(){
        return new ArrayList<>(saves);
    }

    public Optional<SaveFile> findSaveByName(String name){
        return saves.stream()
        .filter(save->save.getName().equals(name))
        .findFirst();
    }

    public void addSave(SaveFile save) {

        if(findSaveByName(save.getName()).isPresent()){
            throw new RuntimeException("Save već postoji: " + save.getName());
        }
        saves.add(save);
    }

    public void deleteSave(String name) {
        saves.removeIf(save->save.getName().equals(name));
    }

    public SaveFile loadSave(String name){
        return findSaveByName(name)
        .orElseThrow(()-> new RuntimeException("Save ne postoji: "+ name));

    }

    public void save(SaveFile saveFile ){
       saves.removeIf(
        save->save.getName().equals(saveFile.getName())
       );

       saves.add(saveFile);

       writeSaveFile(saveFile);
    }

    private ObjectNode saveToJson(SaveFile saveFile) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode saveNode = mapper.createObjectNode();

        saveNode.put("name", saveFile.getName());

        ArrayNode progressArray = mapper.createArrayNode();

        for(BundleItem bundleItem : saveFile.getBundleProgress()) {
            ObjectNode bundleItemNode = mapper.createObjectNode();

            bundleItemNode.put("itemId", bundleItem.getItem().getId());
            bundleItemNode.put("completed",bundleItem.getCompleted());

            progressArray.add(bundleItemNode);
        }

        saveNode.set("bundleProgress", progressArray);

        return saveNode;

    }

    private void writeSaveFile(SaveFile saveFile){
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode saveNode = saveToJson(saveFile);

        ArrayNode savesArray = mapper.createArrayNode();
        savesArray.add(saveNode);

        try{
            mapper.writerWithDefaultPrettyPrinter()
            .writeValue(getSavePath(saveFile).toFile(), savesArray);
        }catch(IOException e){
            throw new RuntimeException("Greška kod spremanja save filea", e);
        }
    }

    private Path getSavePath(SaveFile saveFile){
        String fileName = saveFile.getName()
        .toLowerCase()
        .replace(" ", "") + ".json";

        return Path.of("src/main/resources/saves", fileName);
    }

    private List<SaveFile> loadSaves(){
        List<SaveFile> saveList = new ArrayList<>();

        try{
            Path folder = Path.of("src/main/resources/saves");

            if(!Files.exists(folder)){
                return saveList;
            }
            ObjectMapper mapper = new ObjectMapper();

            try (var files = Files.list(folder)){
                files.filter(path->path.toString().endsWith(".json"))
                .forEach(path -> {
                    try{
                        JsonNode node = mapper.readTree(path.toFile());

                        SaveFile save = parseSave(node);

                        saveList.add(save);
                    }catch(IOException e){
                        throw new RuntimeException("Greška kod učitavanja save filea", e);
                    }
                });
            }
            
        }catch(IOException e){
            throw new RuntimeException("Greška kod učitavanja save foldera", e);
        }

        return saveList;
    }

    private SaveFile parseSave(JsonNode object) {
        String name = object.get("name").asText();

        List<BundleItem> bundleProgress = new ArrayList<>();

        JsonNode bundleItems = object.get("bundleProgress");

        for(JsonNode bundleItemNode : bundleItems) {
            BundleItem bundleItem = parseBundleItem(bundleItemNode);

            bundleProgress.add(bundleItem);
        }

        return new SaveFile(name, bundleProgress);
    }

    private BundleItem parseBundleItem(JsonNode object){
        int itemId = object.get("itemId").asInt();

        boolean completed = object.get("completed").asBoolean();

        Item item = itemRepository.findById(itemId)
        .orElseThrow(()-> new RuntimeException("Item ne postoji: "+ itemId));


        return new BundleItem(item, completed);
    }
}
