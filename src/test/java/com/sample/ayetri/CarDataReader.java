package com.sample.ayetri;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CarDataReader {

    public Map<String, CarInfo> loadExpectedCarInfo(String filePath) throws IOException {
        Map<String, CarInfo> carMap = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.startsWith("VARIANT_REG")) continue; // Skip header

            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                // better to replace all the space
                String reg = parts[0].trim().replaceAll("\\s+", ""); ;
                String make = parts[1].trim();
                String model = parts[2].trim();
                int year = Integer.parseInt(parts[3].trim());
                carMap.put(reg, new CarInfo(make, model, year));
            }
        }

        return carMap;
    }
}
