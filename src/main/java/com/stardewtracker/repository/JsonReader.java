package com.stardewtracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonReader {
    public static String readJson(String filePath){
        try{
            return Files.readString(Path.of(filePath));
        } catch (IOException e){
            throw new RuntimeException("Ne mogu učitati JSON datoteku", e);
        }
    }
}
