package com.stardewtracker.service;

import java.util.List;
import java.util.Optional;

import com.stardewtracker.model.Bundle;
import com.stardewtracker.repository.BundleRepository;

public class BundleService {
    private BundleRepository bundleRepository;

    public BundleService (BundleRepository bundleRepository){
        this.bundleRepository = bundleRepository;
    }

    public List<Bundle> getAllBundles(){
        return bundleRepository.getAll();
    }

    public Optional<Bundle> findBundleById(int id){
        return bundleRepository.findById(id);
    }

    public Optional<Bundle> findBundleByName(String name){
        return bundleRepository.findByName(name);
    }

}
