package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.actions.Travel;

public class Door extends SWEntity {

    /**
     * <code>Door</code> represent the exit door from Sandcrawler
     *
     * @auther Mohamed
     *
     * @param m message renderer for this <code>Door</code> to display messages
     * @param world the <code>World</code> to which <code>Door</code> belongs to
     */

    public Door(MessageRenderer m, SWWorld world){
        super(m);

        this.shortDescription = "Exit";
        this.longDescription = "Move to Tatoonie!";
        this.addAffordance(new Travel(this,m,world));
    }

    @Override
    public String getSymbol() {
        return "D";
    }

}
