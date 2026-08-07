package com.stardewtracker.repository;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRepositoryHelper {
    public static <T> List<T> parseList(String json, Function<JsonNode, T> parser) {
        List<T> result = new ArrayList<>();

        try{
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonArray = mapper.readTree(json);
            for(JsonNode object : jsonArray) {
                T entity = parser.apply(object);
                result.add(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException("Greška kod čitanja JSON podataka", e);
        }

        return result;
    }
}
