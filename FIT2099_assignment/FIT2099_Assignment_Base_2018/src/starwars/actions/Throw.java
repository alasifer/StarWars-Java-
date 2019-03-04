package starwars.actions;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.SWWorld;
import edu.monash.fit2099.simulator.space.Location;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import java.util.ArrayList;
import java.util.List;


/**
 * <code>Throw</code> is an affordact that allow the user throw entities
 *
 * @author Ahmed & Mohamed
 *
 */
public class Throw extends SWAffordance implements SWActionInterface{

    protected SWWorld world;
    /**
     * to save the list of entities at a given location in the grid
     */
    private List<SWEntityInterface> entities;

    /**
     * to save the the locations that has been affected by the throw action
     */
    private List<SWLocation> check;
    private SWLocation loc;


    /**
     *
     * @param theTarget the target being thrown at
     * @param m <code>MessageRenderer</code> to display messages.
     */

    public Throw(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
        priority = 1;

    }

    /**
     * Returns the time is takes to perform this <code>Throw</code> action.
     *
     * @return The duration of the Attack action. Currently hard coded to return 1.
     */
    public int getDuration() {
        return 1;
    }

    /**
     * Determine whether a particular <code>SWActor a</code> can throw t.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true any <code>SWActor</code> can always throw
     */

    public boolean canDo(SWActor a) {
        return true;
    }


    /**
     * A String describing what this <code>Throw</code> action will do, suitable for display on a user interface
     *
     * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
     */

    public String getDescription() {
        return "throw a grenade";
    }

    /**
     * preform the <code>Throw</code> command on entity
     * * <p>
     * This method does not perform any damage  if,
     * <ul>
     * 	<li>The target of the <code>Throw</code> is the same player who used the affordance
     * </ul>
     * <p>
     *
     * this method decreses the hitpoints of any SWEntity in the same location where it is preformes or any location
     * that is two steps away with different affects based on the location.
     *
     * @param a the <code>SWActor</code> who performs this action.
     */

    public void act(SWActor a){
        SWLocation actorloc = SWWorld.getEntitymanager().whereIs(a);

        check = new ArrayList<>(); //list of locations that has been affected
        check.add(actorloc);


        entities = SWWorld.getEntitymanager().contents(actorloc);
        if (entities != null) {
            for (SWEntityInterface en : entities) { // preform damage to all entities at the same location of the acor
                if (en != a) {
                    en.takeDamage(20);
                }
            }
        }

        for (Grid.CompassBearing d : Grid.CompassBearing.values()) {

            Location neighnour = actorloc.getNeighbour(d);
            loc = (SWLocation) neighnour;
            firstlayer.add(loc);

            if (!(check.contains(loc))){
                entities = SWWorld.getEntitymanager().contents(loc);
                check.add(loc);
                if (entities != null ){
                    for (SWEntityInterface en: entities){   // preform damage to all entities that are at neighbouring locations
                        en.takeDamage(10);
                    }
                }
            }
        }

        if (firstlayer != null) {
            for (SWLocation l : firstlayer) {

                for (Grid.CompassBearing r : Grid.CompassBearing.values()) {
                    Location twoStep = l.getNeighbour(r);

                    SWLocation twoSteploc = (SWLocation) twoStep;

                    if (!(check.contains(twoSteploc))) {
                        entities = SWWorld.getEntitymanager().contents(twoSteploc);
                        check.add(twoSteploc);
                        if (entities != null) {
                            for (SWEntityInterface en : entities) { // preform damage to all entities that are two steps away from the actor location
                                en.takeDamage(5);
                            }

                        }

                    }

                }
            }
        }
        // remove the entity from the map and from the actor holding items
        a.getItemCarried().removeAffordance(this);
        a.setItemCarried(null);
    }
}

