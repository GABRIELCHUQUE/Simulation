import Mundo.Tile;
import Mundo.World;
import Organismos.Lifeform;
import Organismos.Plantas.Plant;

public class Test {
    public static void main(String[] args) {
        World world = World.getWorld(4,4);
        Plant plant = new Plant();

        world.insertLifeform(plant,2,2);
        world.printWorld(); System.out.println("-----------------");
        Tile tile = world.getTile(plant);

        for (int i = 0; i < 200; i++) {
            if (i % 7 == 0) {world.rain(); System.out.println("LLUVIA");}
            System.out.printf("FORMAS DE VIDA: %d\n", world.getPopulation());
            System.out.printf("Turno %d | Luz %d | Humedad %.2f\n",
                    (i+1),world.getLight(),world.averageHumidity());
            for (Lifeform lifeform: world.getLifeforms().keySet()) {
                System.out.println(lifeform.getStatus());
            }
            world.turn();
            System.out.println("-----------------");
        }

        world.printWorld(); System.out.println("-----------------");

        for (Lifeform lifeform: world.getLifeforms().keySet()) {
            System.out.println(lifeform.getStatus());
        }
    }
}
