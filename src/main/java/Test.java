import Mundo.Tile;
import Mundo.World;
import Organismos.Plantas.Plant;

public class Test {
    public static void main(String[] args) {
        World world = World.getWorld(4,4);
        Plant plant = new Plant();

        world.insertLifeform(plant,2,2);
        world.printWorld(); System.out.println("-----------------");
        Tile tile = world.getTile(plant);

        for (int i = 0; i < 200; i++) {
            if (i % 7 == 0) world.rain();
            System.out.println("Turno " + (i+1));
            System.out.println("Luz : " + world.getLight());
            System.out.println("Humedad : " + tile.getHumidity());
            System.out.println(plant.getStatus());
            world.turn();
            System.out.println("-----------------");
        }
    }
}
