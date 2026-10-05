package Algoritmos;

public class Randomizer {
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
