package Algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Randomizer {
    public static <T> T choose(Set<T> set) {
        return choose(new ArrayList<>(set));
    }

    public static <T> T choose(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        int i = (int) (Math.random() * list.size());
        return list.get(i);
    }

    public static Object choose(Object[] array) {
        if (array.length == 0) return 0;
        int i = (int) (Math.random() * array.length);
        return array[i];
    }

    public static boolean probability(double p) {
        double n = Math.random();
        return n <= p;
    }

    public static int randomRange(int min, int max) {
        return  (int) (Math.random() * (max - min + 1)) + min;
    }
}
