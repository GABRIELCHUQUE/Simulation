package Organismos;

import Algoritmos.Randomizer;
import Estrategias.Recolección.FeedingStrategy;
import Estrategias.Recolección.Grazing;
import Genes.Gen;
import Genes.GenesTemplates;
import Mundo.Tile;
import Mundo.World;
import Organismos.Enums.Decision;
import Organismos.Organos.Brain;

import java.util.List;
import java.util.Map;

public class Animal extends Lifeform {
    /// Estático
    private static final FeedingStrategy GRAZING = new Grazing();

    /// Atributos
    private Brain brain;

    private final FeedingStrategy feeding;

    private int jawStrength;

    private int awakeTime;
    private double hydration;
    private double hydroReserve;

    /// Constructor
    public Animal() {
        super(GenesTemplates.animalTemplate());
        this.brain = new Brain();
        this.feeding = GRAZING;
        this.jawStrength = 5;
        this.awakeTime = 0;
        this.hydration = genes.get(Gen.MAX_HYDRATION);
        this.hydroReserve = 0;
    }

    /// Turno
    @Override
    public void turn() {
        metabolism();

        for (int i = 0; i < genes.get(Gen.MOVEMENT_POINTS); i++) {
            Tile[] myArea = brain.nearbyTiles(this,getVisionRange());
            List<Lifeform> lifeforms = brain.nearbyLifeforms(myArea);

            Decision decision = takeDecision(lifeforms);
            execute(decision,lifeforms);
        }

        if (health < genes.get(Gen.MAX_HEALTH)) curation();
    }

    /// Métodos del turno
    private void execute(Decision decision, List<Lifeform> lifeforms) {
        switch (decision) {
            case FEED: feed(feeding.selectFood(this,lifeforms)); break;
            case DRINK: drink(); break;
            case FLEE: flee(); break;
            case EXPLORE: explore(); break;
        }
    }

    private void feed(Lifeform food) {
        if (brain.recordFood(food == null)) {
            explore();
            return;
        }

        World world = World.getWorld();
        double distance = distance(world.getTile(this),
                world.getTile(food));

        if (distance < 1.5) consumeFromLifeform(food);
        else {
            Tile target = world.getTile(food);
            movement(target);
        }
    }

    private void drink() {
        /*
        if(here.getTypeofTerrain()== Terrain.SHALLOW_WATER || here.getHumidityLv()>=70){hydration=Math.min(100,hydration+30);needs.drink(35);return;}
        Tile target=null;double best=Double.MAX_VALUE;
        for(Tile t:tiles)if(t.getTypeofTerrain()==Terrain.SHALLOW_WATER || t.getHumidityLv()>=70){double d=distance(here,t);if(d<best){best=d;target=t;}}
        if(target!=null){if(best<=1.5){hydration=Math.min(100,hydration+25);needs.drink(30);}else{movement.moveToward(w,here,target);useEnergy(moveCost);}}
        else {hydration=Math.min(100,hydration+Math.max(0,here.getHumidityLv()/10.0));}
         */
    }

    private void flee() {
        /*
        Tile threat=null;double best=-1;
        for(Tile t:tiles)if(t.getSurface() instanceof Animal && isPotentialThreat((Animal)t.getSurface())){double d=distance(here,t);if(d<best || threat==null){best=d;threat=t;}}
        if(threat!=null){Tile destination=null;double far=-1;for(Tile t:tiles)if(t.getSurface()==null){double d=distance(t,threat);if(d>far){far=d;destination=t;}}
            if(destination!=null)movement.moveToward(w,here,destination);
        } else movement.moveRandom(w,here);
        useEnergy(moveCost*1.8);needs.addStress(-15);
         */
    }

    private void explore() {
        movement();
    }

    private void consumeFromLifeform(Lifeform food) {
        double damage = food.applyDamage(jawStrength);
        if (food instanceof Plant) addEnergy(digestion(damage));
        else if (food.isDead()) {
            addEnergy(food.energy);
        }
    }

    /// Métodos de decisión
    private Decision takeDecision(List<Lifeform> lifeforms) {
        Plant food = (Plant) feeding.selectFood(this,lifeforms);
        return brain.takeDecision(hydration,false,food != null,
                false,canReproduce());
    }

    public double distance(Tile a, Tile b) {
        int dr = a.getRow() - b.getRow();
        int dc = a.getCol() - b.getCol();

        return Math.sqrt(dr * dr + dc * dc);
    }

    /// Métodos de movimiento
    private void movement() {
        World world = World.getWorld();
        while (true) {
            Tile tile = world.getTile(this);
            Tile[] tiles = world.getNeighborTiles(tile.getRow(),
                    tile.getCol(),1);
            Tile returned = (Tile) Randomizer.choose(tiles);
            if (returned != tile) {
                movement(returned);
                return;
            }
        }
    }

    private void movement(Tile tile) {
        World world = World.getWorld();
        Tile from = world.getTile(this);

        int dr=Integer.compare(tile.getRow(),from.getRow());
        int dc=Integer.compare(tile.getCol(),from.getCol());

        int[][] candidates={{dr,dc},{dr,0},{0,dc},{-dr,dc},{dr,-dc},{0,0}};
        for(int[] d:candidates){
            if(d[0]==0 && d[1]==0) continue;
            int r=from.getRow()+d[0], c=from.getCol()+d[1];
            Tile next=world.getTile(r,c);
            if(next!=null && next.getSurface()==null){
                Object occupant=from.getSurface();
                if(occupant!=null) {
                    from.setSurface(null);
                    next.setSurface(occupant);
                    world.updateLocation((Organismos.Lifeform)occupant,next);
                }
            }
        }
    }

    /// Métodos metabolicos
    @Override
    protected void metabolism() {
        useEnergy(getQuota()); awakeTime++;

        brain.updateNeeds(energy,genes.get(Gen.MAX_ENERGY),
                hydration,genes.get(Gen.MAX_HYDRATION),
                awakeTime,genes.get(Gen.MAX_AWAKE_TIME));
    }

    private double digestion(double amount) {
        double efficiency = (double) genes.get(Gen.DIGESTION_EFFICIENCY) / 1000;
        double absorbed = amount * efficiency;
        double cost = useEnergy((double) genes
                .get(Gen.DIGESTION_COST) / 10);

        return Math.max(0,absorbed - cost);
    }

    /// Métodos reproductivos
    @Override
    protected void reproduction() {

    }

    private boolean canReproduce() {
        return !isGrowing() &&
                hasEnergy(genes.get(Gen.MAX_ENERGY)*0.65);
    }

    /// Getters
    private int getVisionRange() {
        return genes
                .get(Gen.VISION_RANGE);
    }

    /// Otros
    @Override
    public String toString() {
        return "A";
    }

    @Override
    public String getStatus() {
        return super.getStatus() +
            String.format(" | Hambre %.0f Sed %.0f Agua %.0f Sueño %.0f",
                brain.getHunger(),brain.getThirst(),hydration,brain.getSleep());
    }
}
