package starwars;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;
import starwars.SWLocation.SWLocationMaker;
import java.util.List;


/**
 * this class initialize the grid of the star wars galaxy called tatooine
 *
 * @author Mohamed & Ahmed
 *
 */
public class Tatoonie extends initMap {


    public Tatoonie(int width, int height, SWLocationMaker factory){
        super(width, height, factory);

        spawnPoint = grid().getLocationByCoordinates(2,2);
        setName("Tatooine");


    }


    public void initializePlanet(SWWorld world, MessageRenderer iface) {
        SWLocation loc;
        // Set default location string
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                loc = grid().getLocationByCoordinates(col, row);
                loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
                loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
                loc.setSymbol('.');
            }
        }


        // BadLands
        for (int row = 5; row < 8; row++) {
            for (int col = 4; col < 7; col++) {
                loc = grid().getLocationByCoordinates(col, row);
                loc.setLongDescription("Badlands (" + col + ", " + row + ")");
                loc.setShortDescription("Badlands (" + col + ", " + row + ")");
                loc.setSymbol('b');
            }
        }

        //Ben's Hut
        loc = grid().getLocationByCoordinates(5, 6);
        loc.setLongDescription("Ben's Hut");
        loc.setShortDescription("Ben's Hut");
        loc.setSymbol('H');

        Direction[] patrolmoves = {
                CompassBearing.EAST, CompassBearing.EAST, CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST, CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST, CompassBearing.NORTHWEST,
                CompassBearing.NORTHWEST};

        BenKenobi ben = BenKenobi.getBenKenobi(iface, world, patrolmoves);
        ben.setSymbol("B");
        loc = grid().getLocationByCoordinates(4, 5);
        SWWorld.getEntitymanager().setLocation(ben, loc);


        loc = grid().getLocationByCoordinates(2, 2);

        // Luke
        Player luke = new Player(Team.GOOD, 100, iface, world);
        luke.setShortDescription("Luke");
        SWWorld.getEntitymanager().setLocation(luke, loc);
        luke.resetMoveCommands(loc);
        luke.setCount(0);

        //R2D2
        loc = grid().getLocationByCoordinates(0, 6);
        R2D2 R2D2 = new R2D2(200, iface, world);
        R2D2.setShortDescription("R2D2");
        SWWorld.getEntitymanager().setLocation(R2D2, loc);
        R2D2.setOwner(luke);
        R2D2.setSymbol("R");

        // Beggar's Canyon
        for (int col = 3; col < 8; col++) {
            loc = grid().getLocationByCoordinates(col, 8);
            loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
            loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
            loc.setSymbol('C');
            loc.setEmptySymbol('='); // to represent sides of the canyon
        }


        // Moisture Farms
        for (int row = 0; row < 10; row++) {
            for (int col = 8; col < 10; col++) {
                loc = grid().getLocationByCoordinates(col, row);
                loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
                loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
                loc.setSymbol('F');

                // moisture farms have reservoirs
                SWEntity reservoir = new Reservoir(iface);
                reservoir.setHitpoints(40);
                SWWorld.getEntitymanager().setLocation(reservoir, loc);
            }
        }
        // grenade
        int[] x = {3, 4, 5, 1, 9};
        int[] y = {0, 2, 7, 4, 8};

        for (int i = 0; i < 5; i++) {
            loc = grid().getLocationByCoordinates(x[i], y[i]);
            SWEntity grenade = new grenade(iface);
            SWWorld.getEntitymanager().setLocation(grenade, loc);

        }
        // Ben Kenobi's hut
        /*
         * Scatter some other entities and actors around
         */
        // a canteen
        loc = grid().getLocationByCoordinates(3, 1);
        SWEntity canteen = new Canteen(iface, 10, 0);
        canteen.setSymbol("©");
        canteen.setHitpoints(500);
        SWWorld.getEntitymanager().setLocation(canteen, loc);
        canteen.addAffordance(new Take(canteen, iface));

        // an oil can treasure
        loc = grid().getLocationByCoordinates(1, 5);
        SWEntity oilcan = new SWEntity(iface);
        oilcan.setShortDescription("an oil can");
        oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
        oilcan.setSymbol("o");
        oilcan.setHitpoints(100);
        // add a Take affordance to the oil can, so that an actor can take it
        SWWorld.getEntitymanager().setLocation(oilcan, loc);
        oilcan.addAffordance(new Take(oilcan, iface));

        // a lightsaber
        LightSaber lightSaber = new LightSaber(iface);
        loc = grid().getLocationByCoordinates(5, 5);
        SWWorld.getEntitymanager().setLocation(lightSaber, loc);

        // A blaster
        Blaster blaster = new Blaster(iface);
        loc = grid().getLocationByCoordinates(3, 4);
        SWWorld.getEntitymanager().setLocation(blaster, loc);

        // A Tusken Raider
        TuskenRaider tim = new TuskenRaider(10, "Tim", iface, world);
        tim.setSymbol("∞");
        loc = grid().getLocationByCoordinates(4, 3);
        SWWorld.getEntitymanager().setLocation(tim, loc);

        //sandcrawler
        Sandcrawler sand = new Sandcrawler(iface, world, patrolmoves);
        sand.setSymbol("\uD83D\uDEF8");
        loc = grid().getLocationByCoordinates(2, 2);
        SWWorld.getEntitymanager().setLocation(sand, loc);



    }


    public String getDescription() {
        return "Tatooine. Welcome to the galaxy of Tatooine";
    }
}