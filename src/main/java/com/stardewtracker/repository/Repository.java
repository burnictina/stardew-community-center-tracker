package com.stardewtracker.repository;

import java.util.*;
import java.util.Optional;

public interface Repository<T> {
    List<T> getAll();

    Optional<T> findById(int id);

    Optional<T> findByName(String name);
}
