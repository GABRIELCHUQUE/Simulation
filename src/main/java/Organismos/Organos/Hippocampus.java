package Organismos.Organos;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Hippocampus {
    /// Atributos
    private final LinkedHashMap<String,Integer> experiences;

    /// Constructor
    public Hippocampus() {
        this.experiences = new LinkedHashMap<>();
    }

    /// Métodos
    public void remember(String key, int value) {
        if (key == null) return;
        experiences.put(key, value);
        while (experiences.size() > 64) {
            experiences.remove(experiences.keySet()
                    .iterator().next());}
    }

    public int recall(String key, int fallback) {
        Integer value = experiences.get(key);
        return value == null ? fallback : value;
    }

    public Map<String,Integer> snapshot(){
        return Collections.unmodifiableMap(new LinkedHashMap<String,Integer>(experiences));
    }
}
