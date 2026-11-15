package Estrategias.Recolección;

import Organismos.Animal;
import Organismos.Lifeform;

import java.util.List;

public interface FeedingStrategy {
    Lifeform selectFood(Animal animal, List<Lifeform> lifeforms);
}
