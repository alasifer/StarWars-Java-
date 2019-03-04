package starwars;
/**
 * Class that manage the <code>Force</code> of an actor
 *
 *
 */

public class Force {
    /**
     * Stores the amount of <code>Force</code> an <code>SWActor</code>
     */
    private int amount;

    /**
     * Get the amount of  <code>Force</code> from a <code>SWActor</code> and stores it
     * @param 	amount the amount that the <code>SWActor</code> currently has
     */
    public Force(int amount)
    {
        this.amount = amount;
    }
    /**
     * Finds and return the amount of force that is stored for <code>SWActor</code>
     * @return return the amount of force that is stored for <code>SWActor</code>
     */
    public int getForce()
    {
        return amount;
    }

    /**
     * Update the amount of force <code>SWActor</code> has,
     * 		condition: must be less than equls to 100
     */
    public void setForce(int amount)
    {
        if (amount<=100){
            this.amount = amount;
        }
    }
}
