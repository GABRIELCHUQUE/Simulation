import Mundo.Tile;
import Mundo.World;

import Organismos.Lifeform;
import Organismos.Plant;


public class Test {
    public static void main(String[] args) {
        World world = World.getWorld(5,5);
        Plant plant = new Plant();

        world.insertLifeform(plant,2,2);
        world.printWorld(); System.out.println("-----------------");

        for (int i = 0; i < 200; i++) {
            System.out.printf("FORMAS DE VIDA: %d\n", world.getPopulation());
            System.out.printf("Turno %d | Luz %d | Humedad %.2f\n",
                    (i+1),world.getLightLv(),world.getHumidityLv());

            for (Lifeform lifeform: world.getLifeforms())
                {System.out.println(lifeform.getStatus());}

            world.turn();
            System.out.println("-----------------");
        }

        world.printWorld(); System.out.println("-----------------");

        for (Lifeform lifeform: world.getLifeforms())
            {System.out.println(lifeform.getStatus());}
    }
}
