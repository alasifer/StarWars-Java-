package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Attack;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.actions.Fill}
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */

	public Reservoir(MessageRenderer m) {
		super(m);
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);

		this.addAffordance(new Attack(this, m));

		this.setShortDescription("a water reservoir.");

		this.setLongDescription("a water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
	}

	/**
	 * this method allows the <code>Reservoir</code> to be attacked and get damaged
	 *
	 * @param damage amount of damage
	 */
	public void takeDamage(int damage) {
		super.takeDamage(damage);

		if (this.hitpoints<=0) {
			this.shortDescription = "the wreckage of a water reservoir";
			this.longDescription  = "the wreckage of a water reservoir, surrounded by slightly damp soil";
			this.setSymbol("X");
			this.setHitpoints(0);
			this.capabilities.remove(Capability.FILLABLE);

		}
		else if (this.hitpoints <= 20){
			this.shortDescription = "a damaged water reservoir";
			this.longDescription  = "a damaged water reservoir, leaking slowly";
			this.setSymbol(("V"));
		}
	}
	@Override
	public String getShortDescription() {

		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
}
