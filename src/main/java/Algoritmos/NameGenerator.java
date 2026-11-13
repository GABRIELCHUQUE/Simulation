package Algoritmos;

import java.util.Arrays;

public class NameGenerator {

    private static final String[] PREFIXES = {
            "Al", "Vel", "Mor", "Zen", "Drak",
            "Kor", "Syl", "Vor", "Eri", "Thal"
    };

    private static final String[] MIDDLES = {
            "a", "e", "i", "o", "u",
            "ar", "en", "ir", "or", "ul",
            "an", "el", "is", "on"
    };

    private static final String[] SUFFIXES = {
            "a", "ia", "us", "is", "on",
            "ar", "eth", "or", "um", "ix"
    };

    public static String generateName() {
        StringBuilder name = new StringBuilder();

        name.append(Randomizer.choose(
                Arrays.asList(PREFIXES)
        ));

        int length = Randomizer.RANDOM.nextInt(2) + 1;

        for (int i = 0; i < length; i++) {
            name.append(Randomizer.choose(
                    Arrays.asList(MIDDLES)
            ));
        }

        name.append(Randomizer.choose(
                Arrays.asList(SUFFIXES)
        ));

        return name.toString();
    }
}
