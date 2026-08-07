package com.stardewtracker.service;

import java.util.List;

import com.stardewtracker.model.*;
import com.stardewtracker.repository.*;

public class SaveService {
    private SaveFileRepository saveRepository;
    private ItemRepository itemRepository;

    public SaveService(SaveFileRepository saveRepository, ItemRepository itemRepository){
        this.saveRepository = saveRepository;
        this.itemRepository = itemRepository;
    }

    public List<SaveFile> getAllSaves(){
        return saveRepository.getAllSaves();
    }

    public SaveFile loadSave(String name) {
        return saveRepository.loadSave(name);
    }

    public void completeItem(SaveFile save, int itemId){
        Item item = itemRepository.findById(itemId)
        .orElseThrow(()-> new RuntimeException("Item ne postoji"));

        save.markItemCompleted(item);
    }

    public void uncompleteItem(SaveFile save, int itemId){
        Item item = itemRepository.findById(itemId)
        .orElseThrow(()-> new RuntimeException("Item ne postoji"));

        save.markItemUncompleted(item);
    }

    public void save(SaveFile save){
        saveRepository.save(save);
    }
}
