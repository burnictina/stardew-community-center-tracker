package com.stardewtracker.service;

import java.util.List;
import java.util.Optional;

import com.stardewtracker.model.Item;
import com.stardewtracker.repository.ItemRepository;

public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems(){
        return itemRepository.getAll();
    }

    public Optional<Item> findItemById(int id){
        return itemRepository.findById(id);
    }

    public Optional<Item> findItemByName(String name){
        return itemRepository.findByName(name);
    }

}
