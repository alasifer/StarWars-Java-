package starwars.actions;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.BenKenobi;


public class Training extends SWAffordance implements ActionInterface{
    /**
     * A Training affordance which allows only Ben to train Luke
     * ben can only train luke for times at the same location, after that luke has to make a move first then come
     * back to ben to be trained till he reached his maximum force 100.
     *
     */

    public Training(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor a) {
        return true;
    }

    @Override
    /**
     * preforming the training
     *
     * @param a the actor to be trained
     */
    public void act(SWActor a) {

        BenKenobi ben;
        if (target instanceof BenKenobi)
        {
            ben = (BenKenobi) target;
            if (sameLoc(a, this.getTarget()))// if actor and Ben are in the same location
            {
                if ((a.getForceAbility().getForce() != 100) && a.getCount() <= 1) {
				    ben.setTrainingMode(true);
                    a.setTrained(true);
                    incforce(a);
                    a.say("\t" + a.getShortDescription() + " says: my new force level is [" + a.getForceAbility().getForce() + "].");
                    a.setCount(a.getCount()+1);
                    a.setCurrloc(a);
                }
                else{
                    if (a.getForceAbility().getForce() == 100){
                        a.say("\t" + a.getShortDescription()+ " says: I have reached my maximum force ability");
                    }
                    else if (a.getCount() >= 1){
                        ben.say("\t"+"Luke, enough training for now, go around and come back");
                        ben.setTrainingMode(false);
                    }

                }

            }
        }

    }

    @Override
    public String getDescription() {
        return "Train under" + target.getShortDescription();
    }

    /**
     * this method is used to check whether ben and luke are at the same location
     *
     * @param a actor (Luke)
     * @param theTarget benkenobi
     * @return
     */
    public boolean sameLoc(SWActor a, SWEntityInterface theTarget)
    {
        SWLocation Luke = SWWorld.getEntitymanager().whereIs(a);
        SWLocation Ben = SWWorld.getEntitymanager().whereIs(theTarget);
        if (Luke == Ben){
            return true;

        }
        return false;
    }

    /**
     * increases the forces of the trained actor (luke)
     * @param a1 actor (luke)
     */
    public void incforce(SWActor a1){
        int oldfor = a1.getForceAbility().getForce();
        oldfor = oldfor + 10;
        a1.getForceAbility().setForce(oldfor);
    }


}
