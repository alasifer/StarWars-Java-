package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.Door;

/**
 * a class uses <code>initMap</code> to initialize the grid for sandcrawler
 *
 * @author Ahmed & Mohamed
 */
public class SandcrawlerWorld extends initMap{


    public SandcrawlerWorld(int width, int height, SWLocation.SWLocationMaker factory){
        super(width, height, factory);
        spawnPoint = grid().getLocationByCoordinates(0, 0);
        setName("Sandcrawler");

    }

    /**
     * initialize the grid of the sandcrawler
     *
     * @param world The world this <code>SWActor</code> belongs to.
     *
     * @param iface <code>MessageRenderer</code> to display messages.
     */
    public void initializePlanet(SWWorld world, MessageRenderer iface){
        SWLocation locat;
        for (int row=0; row < height(); row++) {
            for (int col=0; col < width(); col++) {
                locat = grid().getLocationByCoordinates(col, row);
                locat.setLongDescription("Sandcrawler (" + col + ", " + row + ")");
                locat.setShortDescription("Sandcrawler (" + col + ", " + row + ")");
                locat.setSymbol('.');
            }
        }

        // initialize the door position at the sandcrawler

        Door exit = new Door(iface,world);
        locat = grid().getLocationByCoordinates(1,1);
        SWWorld.getEntitymanager().setLocation(exit,locat);

    }


    @Override
    public String getDescription() {
        return "Sandcrawler. welcome on board.";
    }


}
