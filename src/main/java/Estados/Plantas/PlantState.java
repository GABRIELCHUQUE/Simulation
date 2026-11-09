package Estados.Plantas;

import Organismos.Plant;

public interface PlantState {
    void metabolism(Plant plant);

    void photosynthesis(Plant plant);
}
