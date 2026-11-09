package Estados.Plantas;

import Mundo.World;
import Organismos.Plant;

public class DayState implements PlantState {
    @Override
    public void metabolism(Plant plant) {
        plant.useEnergy(plant.getQuota());
    }

    @Override
    public void photosynthesis(Plant plant) {
        World world = World.getWorld();

        if (!world.hasLight())
            {plant.setState(Plant.NIGHT_STATE); return;}

        double energy = plant.photosynthesis
                (world.getLightLv(),plant.collectWater());
        plant.addEnergy(energy);
    }
}
