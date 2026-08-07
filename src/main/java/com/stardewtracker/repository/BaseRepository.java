package com.stardewtracker.repository;

import java.util.*;
import com.stardewtracker.utils.*;;

public abstract class BaseRepository<T extends Identifiable> implements Repository<T> {
    
    protected List<T>  entities;

    public BaseRepository(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public Optional<T> findById(int id) {
        return entities.stream()
        .filter(entity -> entity.getId() == id)
        .findFirst();
        
    }

    @Override
    public Optional<T> findByName(String name) {
        return entities.stream()
        .filter(entity -> entity.getName().equals(name))
        .findFirst();
    }

    

}
