package assignment2;

import java.util.ArrayList;

public abstract class Tower implements ITower {
	private double damage;
	private double range;
	private double rateOfFire;
	private double slowingAbility;
	private boolean groundShootingAbility;
	private boolean airShootingAbility;
	private boolean iceShootingAbility;
	private ArrayList<ITowerObserver> observerList;
	private ITower myReference;

	public Tower(double damage, double range, double rateOfFire, double slowingAbility, boolean groundShootingAbility,
			boolean airShootingAbility, boolean iceShootingAbility) {

		this.damage = damage;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.slowingAbility = slowingAbility;
		this.groundShootingAbility = groundShootingAbility;
		this.airShootingAbility = airShootingAbility;
		this.iceShootingAbility = iceShootingAbility;
		this.observerList = new ArrayList<>();
	}
	
	// this is only called by the towerFactory object, thus an tower must have been created...
	public void myReference(ITower myReference) {
		this.myReference = myReference;
	}

	public double getDamage() {
		return damage;
	}

	public double getRange() {
		return range;
	}

	public double getRateOfFire() {
		return rateOfFire;
	}

	public double getSlowingAbility() {
		return slowingAbility;
	}

	public boolean isGroundShootingAbility() {
		return groundShootingAbility;
	}

	public boolean isAerialShootingAbility() {
		return airShootingAbility;
	}

	public boolean isIceShootingAbility() {
		return iceShootingAbility;
	}
	
	public void addTowerObserver(ITowerObserver observer) {
		observerList.add(observer);
	}
	
	public void removeTowerObserver(ITowerObserver observer) {
		observerList.remove(observer);
	}
	
	// when an upgrade is applied, the latest upgrade reference is passed through to this point, where all the
	// the update() method of all observers of the tower are called. The reference of the applied upgrade is provided
	// as input...
	public void notifyObservers(ITower myReference) {
		for (ITowerObserver observer: observerList) {
			observer.update(myReference);
		}
	}

}

