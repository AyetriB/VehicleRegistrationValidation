package com.sample.ayetri;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExtractor {

    public static List<String> extractRegistrations(String input) {
        List<String> registrations = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // remove any space if it has
            registrations.add(matcher.group().trim().replaceAll("\\s+", ""));
        }

        return registrations;
    }
}
