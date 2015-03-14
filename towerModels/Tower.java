package towerModels;

import java.util.ArrayList;

public abstract class Tower implements ITower {
	private int damage;
	private int buyValue;
	private int refundValue;
	private int range;
	private int power;
	private int rateOfFire;
	private ArrayList<Upgrade> upgrades;
	private Tower currentTower;
	
	Tower() {
		upgrades = new ArrayList<>();
		upgrades.add(null);
		upgrades.add(null);
		upgrades.add(null);
	}
	
	/**
	 * Sync upgrades with Tower class variables.
	 */
	

	public void initialize(Tower t) {
		this.currentTower = t;
	}
	
	public void sync() {
		double[] d = seeUpgrades();
		if (d[0] >= 0)
			this.setDamage((int) d[0]);
		if (d[1] >= 0)
			this.setRange((int) d[1]);
		if (d[2] >= 0)
			this.setRateOfFire((int) d[2]);
		this.buyValue = valueOfTower();
		this.setRefundValue((int) (valueOfTower()*0.6));
	}
	
	/**
	 * Calculates the total value of the tower.
	 */
	

	public int valueOfTower() {
		int value = 0;
		// Add value of damage upgrades
		if (upgrades.get(0) != null)
			value += upgrades.get(0).value();
		// Add value of range upgrades
		if (upgrades.get(1) != null)
			value += upgrades.get(1).value();
		// Add value of rateOfFire upgrades
		if (upgrades.get(2) != null)
			value += upgrades.get(2).value();
		return value;
	}
	
	/**
	 * Places the value of a particular type of upgrade into each index of currentUpgrades[]. If that
	 * particular type of upgrade has not yet been instantiated, the value placed into
	 * the corresponding index is -1. 
	 */
	

	public double[] seeUpgrades() {
		double[] currentUpgrades = new double[3];
		// Return damage multiplier of damage upgrade.
		if (upgrades.get(0) != null)
			currentUpgrades[0] = upgrades.get(0).seeUpgrade();
		else
			currentUpgrades[0] = -1;
		// Return range multiplier of range upgrade.
		if (upgrades.get(1) != null)
			currentUpgrades[1] = upgrades.get(1).seeUpgrade();
		else
			currentUpgrades[1] = -1;
		// Return rateOfFire multiplier of rateOfFire upgrade.
		if (upgrades.get(2) != null)
			currentUpgrades[2] = upgrades.get(2).seeUpgrade();
		else
			currentUpgrades[2] = -1;
		return currentUpgrades;
	}
	
	/**
	 * Places the value of the next upgrade into the [][0] index of nextUpgrades.
	 * Places the cost of the next upgrade into the [][1] index of nextUpgrades. 
	 */
	


	public double[][] nextUpgrades() {
		double[][] nextUpgrades = new double[3][2];
		if (upgrades.get(0) != null) {
			nextUpgrades[0][0] = upgrades.get(0).nextUpgrade();
			nextUpgrades[0][1] = upgrades.get(0).nextCost();
		}
		else {
			nextUpgrades[0][0] = Level1Damage.damageFactor;
			nextUpgrades[0][1] = Level1Damage.valueToBeAdded;	
		}
		if (upgrades.get(1) != null) {
			nextUpgrades[1][0] = upgrades.get(1).nextUpgrade();
			nextUpgrades[1][1] = upgrades.get(1).nextCost();
		}
		else {
			nextUpgrades[1][0] = Level1Range.rangeFactor;
			nextUpgrades[1][1] = Level1Range.valueToBeAdded;	
		}	
		if (upgrades.get(2) != null) {
			nextUpgrades[2][0] = upgrades.get(2).nextUpgrade();
			nextUpgrades[2][1] = upgrades.get(2).nextCost();
		}
		else {
			nextUpgrades[2][0] = Level1RateOfFire.rateOfFireFactor;
			nextUpgrades[2][1] = Level1RateOfFire.valueToBeAdded;
		}
		return nextUpgrades;
	}
	
	/**
	 * Calls the upgrade() method of the Upgrade specified (see \@param below).
	 * Instantiates an Upgrade if an Upgrade of that particular kind does not yet exist.
	 * @param type: 0 -- damage, 1 -- range, 2 -- rateOfFire. 
	 */
	


	public void upgrade(int type) {
		assert type >= 0 && type <= 2;
		
		if (upgrades.get(type) != null)
			upgrades.get(type).upgrade();
		else {
			switch (type) {
				// Instantiate damage upgrade.
			case 0: 
				upgrades.set(type, new DamageUpgradeRunTime(currentTower));
				break;
				// Instantiate range upgrade.
			case 1:
				upgrades.set(type, new RangeUpgradeRunTime(currentTower));
				break;
				// Instantiate rateOfFire upgrade.
			case 2:
				upgrades.set(type, new RateOfFireUpgradeRunTime(currentTower));
				break;
			}

		}
	}
	
	// Getter & Setter Methods

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(int buyValue) {
		this.buyValue = buyValue;
	}

	public int getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(int refundValue) {
		this.refundValue = refundValue;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(int rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public ArrayList<Upgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(ArrayList<Upgrade> upgrades) {
		this.upgrades = upgrades;
	}
}

class IceTower extends Tower {
	IceTower() {
		super();
		this.setDamage(IceTowerSpecifications.damage);
		this.setBuyValue(IceTowerSpecifications.buyValue);
		this.setRefundValue(IceTowerSpecifications.refundValue);
		this.setRange(IceTowerSpecifications.range);
		this.setPower(IceTowerSpecifications.power);
		this.setRateOfFire(IceTowerSpecifications.rateOfFire);
	}
}

class AerialTower extends Tower {
	AerialTower() {
		super();
		this.setDamage(AerialTowerSpecifications.damage);
		this.setBuyValue(AerialTowerSpecifications.buyValue);
		this.setRefundValue(AerialTowerSpecifications.refundValue);
		this.setRange(AerialTowerSpecifications.range);
		this.setPower(AerialTowerSpecifications.power);
		this.setRateOfFire(AerialTowerSpecifications.rateOfFire);
	}
}

class GroundTower extends Tower {
	GroundTower() {
		super();
		this.setDamage(GroundTowerSpecifications.damage);
		this.setBuyValue(GroundTowerSpecifications.buyValue);
		this.setRefundValue(GroundTowerSpecifications.refundValue);
		this.setRange(GroundTowerSpecifications.range);
		this.setPower(GroundTowerSpecifications.power);
		this.setRateOfFire(GroundTowerSpecifications.rateOfFire);
	}
}





