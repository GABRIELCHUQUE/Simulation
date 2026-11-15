package Organismos.Organos;

import Organismos.Enums.Decision;

public final class FrontalCortex {
    /// Métodos
    public Decision takeDecision(Hypothalamus hypothalamus, double hydration,
            boolean danger, boolean canEat, boolean canDrink, boolean canReproduce)
    {
        if (danger) return Decision.FLEE;

        int hunger = hypothalamus.getHunger();
        int thirst = hypothalamus.getThirst();
        int sleepiness = hypothalamus.getSleep();

        double feed = canEat ? hunger * 0.9 : 0;
        double drink = canDrink ? Math.max(thirst,1000 - hydration) * 0.85 : 0;
        double rest = sleepiness * 0.75;
        double reproduce = canReproduce ? Math.max(0,350 - hunger) * 0.45 : 0;

        Decision decision = Decision.EXPLORE;
        double bestScore = 80.0;

        if (feed > bestScore) {bestScore = feed; decision = Decision.FEED;}
        if (drink > bestScore) {bestScore = drink; decision = Decision.DRINK;}
        if (rest > bestScore) {bestScore = rest; decision = Decision.REST;}
        if (reproduce > bestScore) {bestScore = reproduce; decision = Decision.REPRODUCE;}
        return decision;
    }
}
