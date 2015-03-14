package towerModels;

public abstract class Upgrade {
	// runningTotal is the current value of all accumulated upgrades of a particular type.
	int runningTotal;
	UpgradeSpecifications uS;
	
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
		}
	}
}

/*
 *	Upon instantiation of a DamageUpgradeRunTime, uS is set to the appropriate Upgrade.DamageUpgrade, and running total
 *	is equal to the value of the first upgrade.
 */

class DamageUpgradeRunTime extends Upgrade {
	
	DamageUpgradeRunTime() {
		uS = new Level1Damage();
		runningTotal = uS.value;
	}
}

class RangeUpgradeRunTime extends Upgrade {
	
	RangeUpgradeRunTime() {
		uS = new Level1Range();
		runningTotal = uS.value;
	}
}

class RateOfFireUpgradeRunTime extends Upgrade {
	
	RateOfFireUpgradeRunTime() {
		uS = new Level1RateOfFire();
		runningTotal = uS.value;
	}
}

