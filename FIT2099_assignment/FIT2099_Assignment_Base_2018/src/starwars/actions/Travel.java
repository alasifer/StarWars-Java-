package starwars.actions;

import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.Door;
import starwars.entities.Sandcrawler;
import starwars.SWWorld;
import java.util.HashMap;
import java.util.List;

/**
 * class that is used to travel between Sandcrawler and Tatooine
 *
 * @author Mohamed & Ahmed
 */

public class Travel extends SWAffordance implements SWActionInterface {


    private SWLocation sandloc;
    private List<SWEntityInterface> en;
    private SWLocation loact;
    private SWWorld world;
    private HashMap<String, initMap> planets;
    private String desc;

    /**
     *
     *
     * @param theTarget the target being traveled to
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the <code>SWWorld</code> world to which this <code>theTarget</code> belongs to
     */
    public Travel(SWEntityInterface theTarget, MessageRenderer m, SWWorld world) {
        super(theTarget, m);
        this.world = world;
        planets = world.getPlanets();
    }



    /**
     * Determine whether a particular <code>SWActor a</code> can travel.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true any <code>SWActor</code> can always travel
     */
    public boolean canDo(SWActor a) {

        return true;
    }

    /**
     * to check whether the actor and the target are at the same location
     *
     * @param a the <code>SWActor</code> being queried
     * @param theTarget the target being traveled to
     * @return true if both at the same location, false otherwise
     */

    public boolean sameLoc(SWActor a, SWEntityInterface theTarget)
    {
        SWLocation Luke = SWWorld.getEntitymanager().whereIs(a);
        SWLocation Sand = SWWorld.getEntitymanager().whereIs(theTarget);
        if (Luke == Sand){
            return true;

        }
        return false;
    }

    @Override
    public void act(SWActor a) {

        if (sameLoc(a,this.getTarget()) && (a.getForceAbility().getForce()>10)){
            if (target instanceof Sandcrawler) {
                SWWorld.getEntitymanager().remove(a);
                this.world.SwitchPlanets(planets.get("Sandcrawler"),a);

            }
            else if (target instanceof Door) {
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        SWLocation chck = planets.get("Tatooine").grid().getLocationByCoordinates(row,col);
                        en = SWWorld.getEntitymanager().contents(chck);

                        if (en != null){
                            for (SWEntityInterface x: en){
                                if (x instanceof Sandcrawler){
                                    sandloc = chck;
                                    break;
                                }
                            }
                        }
                    }
                }
                SWWorld.getEntitymanager().remove(a);
                this.world.SwitchPlanets(planets.get("Tatooine"), a);
                SWWorld.getEntitymanager().setLocation(a,sandloc);
            }

        }

    }
    /**
     * A String describing where this <code>Travel</code> travel to
     *
     * @return String comprising "attack " and the short description of the target grid
     */

    public String getDescription() {

        if (target instanceof Door){
            return "Move to " + planets.get("Tatooine").getDescription() ;
        }
        return "Move to " + planets.get("Sandcrawler").getDescription();
    }

}
