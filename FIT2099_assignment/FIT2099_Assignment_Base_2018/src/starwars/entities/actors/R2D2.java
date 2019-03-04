package starwars.entities.actors;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.SWWorld;
import starwars.Team;

/**
 *
 *
 */
public class R2D2 extends Droid {
    /**
     * Creates a R2D2 which stores the <code>name</code>, <code>hitpoints</code>, etc
     *
     * @param hitpoints the amount of <code>hitpoints</code> of this actor. If the hitpoints are zero or less this <code>Actor</code> is dead
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the world this <code>SWActor</code> belongs to.
     */
    public R2D2(int hitpoints, MessageRenderer m, SWWorld world)
    {
        super(Team.GOOD, "R2-D2", hitpoints, m, world);
        this.setShortDescription("R2-D2");
        this.setLongDescription("R2D2, legend once said that it is a failed creation of Doraemon");
    }

    /**
     * The <code>R2D2</code> gets damaged from badlands.
     * The <code>R2D2</code> follows the <code>owner</code> if exists.
     * The <code>R2D2</code> stays at its position if it does not have an owner
     */
    public void act() {
        if (isImmobile()) {
            return;
        }

        say(describeLocation());
        badLandsEffect();

        if (getOwner() != null) {
            followOwner();
        }

    }

}
