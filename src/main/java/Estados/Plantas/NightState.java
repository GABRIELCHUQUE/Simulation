package Estados.Plantas;

import Mundo.World;
import Organismos.Plant;

public class NightState implements PlantState {
    @Override
    public void metabolism(Plant plant) {
        plant.useReserve(plant.getQuota());
    }

    @Override
    public void photosynthesis(Plant plant) {
        World world = World.getWorld();
        if (world.hasLight()) {
            plant.setState(Plant.DAY_STATE);
            plant.photosynthesis();
        }
    }
}
