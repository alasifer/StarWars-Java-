package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

/**
 * Implements a own droid action which allows the player to own the droid
 */
public class Own extends SWAffordance implements SWActionInterface{

    /**
     * Creates a own droid action to store the <code>theTarget</code>, <code>m</code>, etc
     * @param theTarget The entity which will be affected by the action
     * @param m <code>MessageRenderer</code> to display messages.
     */
    public Own(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    /**
     * Sets the duration of the action
     * @return the duration of the action
     */
    @Override
    public int getDuration() {
        return 0;
    }

    /**
     * Sets the priority of the action
     * @return the priority of the action
     */
    @Override
    public int getPriority() {
        return 1;
    }

    /**
     * Checks if the <code>SWActor</code> could perform the action
     * @return a boolean if the Actor which performs the action
     */
    @Override
    public boolean canDo(SWActor a) {
        Droid droid = (Droid) target;
        return !droid.isOwned();
    }

    /**
     * The actor will own the droid.
     * @param a The SWActor which performs the action
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof Droid)
        {
            ((Droid) target).setOwner(a);
        }
    }

    /**
     * short description of the action
     * @return A string which describes the action
     */
    @Override
    public String getDescription() {
        return "Own " + target.getShortDescription();
    }

}
