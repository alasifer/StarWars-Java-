package starwars;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.actors.Player;
import starwars.swinterfaces.SWGridController;

import java.util.HashMap;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {

	/**
	 * <code>SWGrid</code> of this <code>SWWorld</code>
	 */
	private SWGrid myGrid;
	private boolean chck = false; //to check whether a grid has been initialized or not

	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();

	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/

	private HashMap<String, initMap> planets = new HashMap<String, initMap>();

	private initMap currentPlanet;
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {

		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		planets.put("Tatooine", new Tatoonie(10, 10, factory));
		planets.put("Sandcrawler", new SandcrawlerWorld(2, 2, factory));
		currentPlanet = planets.get("Tatooine");
		myGrid = currentPlanet.grid();
		space = myGrid;

	}


	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		return myGrid;
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author Mohamed
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}


	public void initializeWorld(MessageRenderer iface)
	{
		currentPlanet.initializePlanet(this, iface);
	}


	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e);
	}

	public initMap getCurrentPlanet()
	{
		return this.currentPlanet;
	}

	public HashMap<String, initMap> getPlanets()
	{
		return this.planets;
	}

	/**
	 * switch between Tatoonine and Sandcrawler
	 *
	 * @param targetPlanet grid to where the entity wants to go
	 * @param entity
	 */
	public void SwitchPlanets(initMap targetPlanet, SWEntityInterface entity)
	{

		if (entity instanceof Player && targetPlanet.getName() != "Tatooine" && !chck) {
			this.currentPlanet = targetPlanet;
			myGrid = currentPlanet.grid();
			SWGridController ui = new SWGridController(this);
			targetPlanet.initializePlanet(this, ui);
			chck = true;
		}
		else if (entity instanceof Player) {
			// call back the saved grid
			this.currentPlanet = targetPlanet;
			myGrid = currentPlanet.grid();
			SWGridController ui = new SWGridController(this);
		}

		targetPlanet.spawn(entity);
	}



	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
}
