package towerModels;

public abstract class UpgradeSpecifications {
	abstract UpgradeSpecifications upgrade();
	double increaseFactor;
	int value;
}

/**
 * DamageUpgrade contains three subclasses: Level1Damage, Level2Damage, Level3Damage. Each Level, 
 * when instantiated, changes the 'damageIncreaseFactor' to match its 'damageFactor' value. 
 * Each Levels upgrade() method creates a new instance of the subsequent damage level and alters 
 * the superclass variable 'damageIncreaseFactor'.
 */

abstract class DamageUpgrade extends UpgradeSpecifications {
	abstract DamageUpgrade upgrade();
}

class Level1Damage extends DamageUpgrade {
	static double damageFactor = 3;
	static int valueToBeAdded = 100;

	Level1Damage() {
		this.increaseFactor = damageFactor;
		value = valueToBeAdded;
	}

	public DamageUpgrade upgrade() {
		return new Level2Damage();
	}

}

class Level2Damage extends DamageUpgrade {
	static double damageFactor = 6;
	static int valueToBeAdded = 300;

	Level2Damage() {
		this.increaseFactor = damageFactor;
		value = valueToBeAdded;
	}

	public DamageUpgrade upgrade() {
		return new Level3Damage();
	}
}

class Level3Damage extends DamageUpgrade {
	static double damageFactor = 9;
	static int valueToBeAdded = 500;

	Level3Damage() {
		this.increaseFactor = damageFactor;
		value = valueToBeAdded;
	}

	public DamageUpgrade upgrade() {
		return null;
	}
}

abstract class RangeUpgrade extends UpgradeSpecifications  {
	abstract RangeUpgrade upgrade();
}

class Level1Range extends RangeUpgrade {
	static double rangeFactor = 6;
	static int valueToBeAdded = 1000;

	Level1Range() {
		this.increaseFactor = rangeFactor;
		value = valueToBeAdded;
	}

	public RangeUpgrade upgrade() {
		return new Level2Range();
	}
}

class Level2Range extends RangeUpgrade {
	double rangeFactor = 12;
	int valueToBeAdded = 2000;

	Level2Range() {
		this.increaseFactor = rangeFactor;
		value = valueToBeAdded;
	}

	public RangeUpgrade upgrade() {
		return new Level3Range();
	}
}

class Level3Range extends RangeUpgrade {
	double rangeFactor = 18;
	int valueToBeAdded = 3000;

	Level3Range() {
		this.increaseFactor = rangeFactor;
		value = valueToBeAdded;
	}

	public RangeUpgrade upgrade() {
		return null;
	}

}

abstract class RateOfFireUpgrade extends UpgradeSpecifications  {
	abstract RateOfFireUpgrade upgrade();
}

class Level1RateOfFire extends RateOfFireUpgrade {
	static double rateOfFireFactor = 13;
	static int valueToBeAdded = 10000;

	Level1RateOfFire() {
		this.increaseFactor = rateOfFireFactor;
		value = valueToBeAdded;
	}

	public RateOfFireUpgrade upgrade() {
		return new Level2RateOfFire();
	}
}

class Level2RateOfFire extends RateOfFireUpgrade {
	double rateOfFireFactor = 26;
	int valueToBeAdded = 20000;

	Level2RateOfFire() {
		this.increaseFactor = rateOfFireFactor;
		value = valueToBeAdded;
	}

	public RateOfFireUpgrade upgrade() {
		return new Level3RateOfFire();
	}
}

class Level3RateOfFire extends RateOfFireUpgrade {
	double rateOfFireFactor = 39;
	int valueToBeAdded = 30000;

	Level3RateOfFire() {
		this.increaseFactor = rateOfFireFactor;
		value = valueToBeAdded;
	}

	public RateOfFireUpgrade upgrade() {
		return null;
	}
}

