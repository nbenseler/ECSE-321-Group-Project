package towerModels;

//import towerModels.Tower;

public abstract class Upgrade {
	// runningTotal is the current value of all accumulated upgrades of a particular type.
	int runningTotal;
	UpgradeSpecifications uS;
	Tower t;
	
	Upgrade(Tower t) {
		this.t = t;
	}
	
	public double seeUpgrade() {
		return uS.increaseFactor;
	}
	
	public double nextUpgrade() {
		return uS.upgrade().increaseFactor;
	}
	
	public double nextCost() {
		return uS.upgrade().value;
	}
	
	public double currentCost() {
		return uS.value;
	}
	
	public int value() {
		return runningTotal;
	}
	
	public void upgrade() {
		if (uS.upgrade() != null) {
			uS = uS.upgrade();
			runningTotal += uS.value;
			toNotify();
		}
	}
	
	public void addTower(Tower t) {
		this.t = t;
	}
	
	public void toNotify() {
		t.sync();
	}
}

/*
 *	Upon instantiation of a DamageUpgradeRunTime, uS is set to the appropriate Upgrade.DamageUpgrade, and running total
 *	is equal to the value of the first upgrade.
 */

class DamageUpgradeRunTime extends Upgrade {
	
	DamageUpgradeRunTime(Tower t) {
		super(t);
		uS = new Level1Damage();
		runningTotal = uS.value;
		toNotify();
	}
}

class RangeUpgradeRunTime extends Upgrade {
	
	RangeUpgradeRunTime(Tower t) {
		super(t);
		uS = new Level1Range();
		runningTotal = uS.value;
		toNotify();
	}
}

class RateOfFireUpgradeRunTime extends Upgrade {
	
	RateOfFireUpgradeRunTime(Tower t) {
		super(t);
		uS = new Level1RateOfFire();
		runningTotal = uS.value;
		toNotify();
	}
}

