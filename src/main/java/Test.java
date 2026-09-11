import Mundo.Square;
import Mundo.World;
import Organismos.Lifeform;
import Organismos.Plantae.Plant;

public class Test {
    public static void main(String[] args) {
        World world = new World(5,5);
        Plant plant = new Plant();

        Square square = world.getSquare(2,2);

        world.insertLifeform(plant,2,2);


        world.printWorld(); System.out.println("-----------------");
        for (int i = 0; i < 200; i++) {
            if (i % 7 == 0) world.rain();
            System.out.println("Turno " + (i+1));
            System.out.println("Luz : " + world.getLight());
            System.out.println("Humedad : " + square.getHumidity());
            System.out.println(plant.getStatus());
            world.turn();
            System.out.println("-----------------");
        }
    }
}
