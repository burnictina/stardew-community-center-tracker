package com.stardewtracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.fasterxml.jackson.databind.*;
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
