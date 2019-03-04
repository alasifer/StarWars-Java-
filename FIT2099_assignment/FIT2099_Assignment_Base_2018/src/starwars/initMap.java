package starwars;

import java.util.List;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation.SWLocationMaker;
import starwars.entities.actors.Player;


public abstract class initMap {
    /**The grid for planet with the <code>width</code> && <code>height</code>*/
    private SWGrid grid;
    private int width;
    private int height;
    /**A location for the new <code>SWEntityInterface</code> to go to*/
    protected SWLocation spawnPoint;
    /**A boolean of which checks if the planet had been instantiated*/
    private String name;

    /**
     * Instantiate the planet
     * @param width A integer to store the <code>width</code>
     * @param height A integer to store the <code>height</code>
     *
     * @param factory An object with a <code>make()</code> method that can create <code>SWLocation</code>
     *
     */
    public initMap(int width, int height, SWLocationMaker factory)
    {
        grid = new SWGrid(width, height, factory);
        this.spawnPoint = null;
        name = null;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialize the <code>SWEntityInterface</code>s to the <code>Planet</code>
     * @param world The world this <code>SWActor</code> belongs to.
     *
     * @param iface <code>MessageRenderer</code> to display messages.
     *
     */
    public abstract void initializePlanet(SWWorld world, MessageRenderer iface);

    /**
     * Assigns the <code>SWEntityInterface</code>s to the <code>Planet</code> to the <code>spawnPoint</code>
     * @param entity an entity which will put at the starting point
     *
     */
    public final void spawn(SWEntityInterface entity) {

            List<SWEntityInterface> entities_SpawnPoint = SWWorld.getEntitymanager().contents(spawnPoint);
            if (entities_SpawnPoint != null)
            {
                for (SWEntityInterface entity_SpawnPoint : entities_SpawnPoint)
                {
                    if (entity != entity_SpawnPoint)
                    {
                        SWWorld.getEntitymanager().setLocation(entity, spawnPoint);
                    }
                }
            }
            else
            {
                SWWorld.getEntitymanager().setLocation(entity, spawnPoint);
            }

            if (entity instanceof Player)
            {
                ((Player)entity).resetMoveCommands(spawnPoint);
            }

    }

    /**
     * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
     *
     * @author ram
     * @return the height of the grid
     */
    public int height() {
        return this.height;
    }

    /**
     * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
     *
     * @author ram
     * @return the width of the grid
     */
    public int width() {
        return this.width;
    }

    /**
     * Gets the <code>grid</code> of the grid
     * @return the grid of the grid
     */
    public SWGrid grid()
    {
        return this.grid;
    }

    /**
     * Retrieves a description of the grid
     * @return a String of description of the grid
     */
    public abstract String getDescription();

    /**
     * Sets the name of the planet
     * @param name A string to store the name of the grid
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retrieves the name of the planet
     * @return A string which stores the name of the grid
     */
    public String getName()
    {
        return this.name;
    }

    public void setSpawnPoint(SWLocation spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

}
