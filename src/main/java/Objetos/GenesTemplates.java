package Objetos;

import java.util.HashMap;
import java.util.Map;

public class GenesTemplates {
    public static Map<Gen,Integer> lucaTemplate() {
        Map<Gen,Integer> LUCA = new HashMap<>();
        LUCA.put(Gen.MAX_ENERGY,20);
        LUCA.put(Gen.QUOTA_ENERGY,1);
        LUCA.put(Gen.MAX_HEALTH,50);
        LUCA.put(Gen.CURATION_FACTOR,1);
        LUCA.put(Gen.GROW_FACTOR,1);
        LUCA.put(Gen.MATURITY,30);
        LUCA.put(Gen.OLD_AGE_START,80);
        return LUCA;
    }

    public static Map<Gen,Integer> plantTemplate() {
        Map<Gen,Integer> plantGenes = lucaTemplate();
        plantGenes.put(Gen.QUOTA_WATER,1);
        plantGenes.put(Gen.PHOTO_EFFICIENCY,4);
        plantGenes.put(Gen.LIGHT_RESISTANCE,90);
        plantGenes.put(Gen.RESISTANCE_WATER,75);
        return plantGenes;
    }
}


