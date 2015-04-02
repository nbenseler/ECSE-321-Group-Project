package tower;

import java.util.ArrayList;

import critter.critter;

public abstract class UpgradeDecorator implements ITower {
	protected ITower decoratee;
	private ITower myReference;
	
	public UpgradeDecorator(ITower tower) {
		this.decoratee = tower;
	}
	// this is only called by the upgradeFactory object, thus an upgrade must have been applied...
	public void myReference(ITower myReference) {
		this.myReference = myReference;
		this.notifyObservers(this.myReference);
	}
	
	public double getDamage() {
		return decoratee.getDamage();
	}
	
	public double getRange() {
		return decoratee.getRange();
	}
	
	public double getRateOfFire() {
		return decoratee.getRateOfFire();
	}
	
	public double getSlowingAbility() {
		return decoratee.getSlowingAbility();
	}

	public boolean isGroundShootingAbility() {
		return decoratee.isGroundShootingAbility();
	}

	public boolean isAerialShootingAbility() {
		return decoratee.isAerialShootingAbility();
	}

	public boolean isIceShootingAbility() {
		return decoratee.isIceShootingAbility();
	}
	// we send the current upgrade reference to the previous decoratee. 
	public void notifyObservers(ITower myReference) {
		decoratee.notifyObservers(myReference);
	}
	
	public void changeList(ArrayList<critter> cList)
	{
		decoratee.changeList(cList);
	}
}
