package Estrategias.Recolección;

import Mundo.Tile;
import Mundo.World;
import Organismos.Animal;
import Organismos.Lifeform;
import Organismos.Plant;

import java.util.List;

public class Grazing implements FeedingStrategy {
    @Override
    public Plant selectFood(Animal animal, List<Lifeform> lifeforms) {
        World world = World.getWorld();
        Tile animalTile = world.getTile(animal);

        if (animalTile == null) return null;

        Plant nearest = null;
        double distance = Double.MAX_VALUE;

        for (Lifeform lifeform : lifeforms) {
            if (!(lifeform instanceof Plant plant)) continue;

            Tile aux = world.getTile(plant);
            if (aux == null) continue;

            double auxDistance = animal.distance(animalTile, aux);

            if (auxDistance < distance) {
                distance = auxDistance;
                nearest = plant;
            }
        } return nearest;
    }
}
