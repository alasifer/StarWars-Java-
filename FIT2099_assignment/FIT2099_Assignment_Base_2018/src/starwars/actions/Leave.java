package starwars.actions;

import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

public class Leave extends SWAffordance {

    public Leave(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    /** this checks whether an actor is carrying an item
     *
     *@param a actor  class
     */
    public boolean canDo(SWActor a) {
        if (a.getItemCarried() == null)
            return false;
        return a.getItemCarried().equals(target);
    }

    @Override
    /**
     * Perform the Leave action.
     * leave the carried item at the actor current location
     *
     * @param a actor class
     */
    public void act(SWActor a) {
        if (a.getItemCarried() == null) { // this condition should never be called as the leave option should never get listed
            // unless an item is carried
            a.say("Helloooo, I am not holding anything");
        }
        else {
            if (target instanceof SWEntityInterface) {
                EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
                entityManager.setLocation((SWEntityInterface)target, entityManager.whereIs(a));
                a.setItemCarried(null);
                target.removeAffordance(this);
                target.addAffordance(new Take((SWEntityInterface)target, this.messageRenderer));
            }
        }
    }


    @Override
    /**
     * A String describing what this action will do
     *
     * @return String comprising "leave " and the short description of the item needed to be left
     */
    public String getDescription() {
        return "leave " + target.getShortDescription();
    }

}
