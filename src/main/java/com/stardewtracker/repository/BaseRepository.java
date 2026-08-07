package com.stardewtracker.repository;

import java.util.*;

public abstract class BaseRepository<T> implements Repository<T> {
    
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
        .filter(entity -> getId(entity) == id)
        .findFirst();
        
    }

    @Override
    public Optional<T> findByName(String name) {
        return entities.stream()
        .filter(entity -> getName(entity).equals(name))
        .findFirst();
    }

    protected abstract int getId(T entity);

    protected abstract String getName(T entity);
    

}
