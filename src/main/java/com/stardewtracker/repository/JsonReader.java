package com.stardewtracker.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static String readJson(String resourcePath) {
        try (InputStream inputStream =
                JsonReader.class.getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException(
                    "JSON resource nije pronađen: " + resourcePath
                );
            }

            return new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            throw new RuntimeException(
                "Ne mogu učitati JSON resource: " + resourcePath,
                e
            );
        }
    }
}