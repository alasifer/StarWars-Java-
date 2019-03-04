package starwars.entities.actors;


import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import edu.monash.fit2099.gridworld.Grid;
import starwars.actions.Own;
import starwars.actions.Move;


public abstract class Droid extends SWActor{
    /**the <code>name</code> to which this <code>Droid</code> to be called**/
    private String name;
    /**the <code>SWActor</code> to which owns this <code>Droid</code>**/
    private SWActor owner;
    /**the <code>Direction</code> to which direction should the droid move**/
    private Direction rand;

    public Droid(Team team, String name, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);
        this.name = name;
        addAffordance(new Own(this, m));
        this.owner = null;
    }


    public final boolean isImmobile()
    {
        if (this.getHitpoints() > 0){
            return false;
        }
        return true;
    }

    /**
     * Sets the <code>SWActor</code> to which owns this <code>Droid</code>
     *
     * @param a the actor who is owning the droid
     */
    public final void setOwner(SWActor a)
    {
        owner = a;
        this.setTeam(owner.getTeam());
    }

    /**
     * gets the <code>owner</code>'s position in the grid
     *
     * @return A <code>Location</code> of  the <code>owner</code>'s position in the grid
     */
    public final Location ownerPosition ()
    {
        Location ownerCurrentPosition = null;
        if (this.owner != null)
        {
            ownerCurrentPosition = world.find(this.owner);
        }
        return ownerCurrentPosition;
    }


    /**
     * Follows the current owner's position
     * if the owner at neighbour  ing location the droid will move to that location, other wise
     * the droid will move randomly in one direction till it finds its owner or reaches the end of the drid
     *
     */
    public final void followOwner()
    {
        if (owner != null) {
            boolean chck = true;

            for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
                SWLocation futureLoc = SWWorld.getEntitymanager().whereIs(this);
                if (futureLoc.getNeighbour(d) == ownerPosition()) {
                    Move mymove = new Move(d, messageRenderer, world);
                    scheduler.schedule(mymove, this, 2);
                    chck = false;
                }
            }

            if (chck == true) {

                    SWLocation futureLoc = SWWorld.getEntitymanager().whereIs(this);

                    if (futureLoc.hasExit(getRand())){
                        Move rand = new Move(getRand(), messageRenderer, world);
                        scheduler.schedule(rand, this, 2);
                    }
                    else{
                        setrand();
                        Move rand = new Move(getRand(), messageRenderer, world);
                        scheduler.schedule(rand, this, 2);
                    }
            }

        }
    }





    /**
     * Decreases the <code>Droid</code>'s hp when they are in badlands
     */
    public final void badLandsEffect()
    {
        if (this.world.getEntityManager().whereIs(this).getSymbol() == 'b'){
            messageRenderer.render(this.getShortDescription() + " got hurt from a sand storm.");
            this.takeDamage(5);
            isImmobile();
        }
    }

    public void setrand(){
        rand  = Grid.CompassBearing.getRandomBearing();
        SWLocation futureLoc = SWWorld.getEntitymanager().whereIs(this);
        while (!futureLoc.hasExit(rand)){
            rand  = Grid.CompassBearing.getRandomBearing();
        }
    }

    public Direction getRand() {
        return rand;
    }

    /**
     * Checks if the <code>DisabledDroid</code> has been owned
     * @return A boolean if the <code>DisabledDroid</code> has been owned
     */
    public final boolean isOwned()
    {
        return this.owner != null;
    }

    /**
     * Describes the <code>Droid</code>'s <code>name</code>
     * @return A string of the <code>Droid</code>'s <code>name</code>
     */
    @Override
    public String getShortDescription() {
        return "Model " + name;
    }

    /**
     * Describes the <code>Droid</code>'s <code>name</code>
     * @return A string of the <code>Droid</code>'s <code>name</code>
     */
    @Override
    public String getLongDescription() {
        return getShortDescription();
    }

    /**
     * Describes the <code>Droid</code>'s position in the grid and it's <code>hitpoints</code>
     * @return A string of the <code>Droid</code>'s position in the grid and it's <code>hitpoints</code>
     */
    protected String describeLocation() {
        return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + this.world.getEntityManager().whereIs(this).getShortDescription();
    }

    public final SWActor getOwner()
    {
        return this.owner;
    }
}