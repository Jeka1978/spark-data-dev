package com.epam.data.spark.unsafe.infra;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Evgeny Borisov
 */
public class WordsMatcher {
    public static String findAndRemoveMatchingPieces(Set<String> options, List<String> pieces) {
        StringBuilder match = new StringBuilder(pieces.remove(0));
        List<String> remainingOptions = options.stream().filter(option -> option.toLowerCase().startsWith(match.toString().toLowerCase())).collect(Collectors.toList());
        if (remainingOptions.size() == 0) {
            return "";
        }
        while (remainingOptions.size() > 1) {
            match.append(pieces.remove(0));
            remainingOptions.removeIf(option -> !option.toLowerCase().startsWith(match.toString().toLowerCase()));
        }
        while (!remainingOptions.get(0).equalsIgnoreCase(match.toString())) {
            match.append(pieces.remove(0));
        }

        return Introspector.decapitalize(match.toString());
    }


    public static void main(String[] args) {
        Set<String> options = Set.of("name", "nameOfBabushka", "age", "lastName", "nameOfDedushka");
        List<String> pieces = new ArrayList<>(List.of("name", "Of", "Babushka", "Greater", "Than"));
        String match = findAndRemoveMatchingPieces(options, pieces);
        System.out.println("match = " + match);
        System.out.println(pieces);
    }
}
