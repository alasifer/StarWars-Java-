package starwars.entities;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Move;
import starwars.actions.Travel;
import starwars.entities.actors.Droid;
import starwars.entities.actors.behaviors.Patrol;
import starwars.SWWorld;
import starwars.Team;
import java.util.List;
import java.util.HashMap;

/**
 * this class represent the Sandcrawler in the map and its movement
 *
 * @author Mohamed & Ahmed
 */

public class Sandcrawler extends SWActor{

    private boolean chck =false;

    private Patrol path;

    private SWLocation locat;

    /**
     *
     *
     * @param m message renderer for this <code>Sandcrawler</code> to display messages
     * @param world the <code>World</code> to which <code>Sandcrawler</code> belongs to
     * @param moves patrol moves for <code>Sandcrawler</code>
     */
    public Sandcrawler( MessageRenderer m, SWWorld world, Direction [] moves) {
        super(Team.GOOD, 10000, m, world);

        path = new Patrol(moves);
        this.shortDescription = "A Sandcrawler";
        this.longDescription = "A Sandcrawler, you might find your droid inside!";
        this.addAffordance(new Travel(this,m,world));
    }


    /**
     * method to preform the movement of the <code>Sandcrawler</code>
     * and take a droid inside if it is in the same location
     */
    public void act(){

        locat = SWWorld.getEntitymanager().whereIs(this);

        HashMap<String, initMap> planets = world.getPlanets();

        if (!getchck()) {
            Direction newdirection = path.getNext();
            say(getShortDescription() + " moves " + newdirection);
            Move myMove = new Move(newdirection, messageRenderer, world);
            scheduler.schedule(myMove,this,1);
            setchck(true);

        }
        else if (getchck()){
            setchck(false);
        }

        List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(locat);


        for (SWEntityInterface en: contents){
            if (en instanceof Droid){
                SWWorld.getEntitymanager().remove(en);
                this.world.SwitchPlanets(planets.get("Sandcrawler"),en);
            }
        }

    }

    public boolean setchck(boolean c){
        return chck = c;
    }

    public boolean getchck(){
        return chck;
    }

    public SWLocation getLocat() {
        return this.getCurrloc();
    }
}
