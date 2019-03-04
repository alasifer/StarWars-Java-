package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;

/**
 * <code>grenade</code> an entity that can be thrown and cause damage
 *
 * @author Ahmed
 */
public class grenade extends SWEntity {

    /**
     *
     * @param m message renderer for this <code>grenade</code> to display messages
     */
    public grenade(MessageRenderer m) {
        super(m);

        this.shortDescription = "A Grenade";
        this.longDescription = "A Grenad.  booooooooooooooooom!";

        this.addAffordance(new Take(this, m));//add the take affordance so that the grenade can be taken by SWActors
        this.capabilities.add(Capability.WEAPON);// it's a weapon.


    }

    /**
     * set the symbol of the grenade
     *
     */
    public String getSymbol() {
        return "\uD83D\uDCA3";
    }

}
