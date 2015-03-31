package tower;

public abstract class UpgradeDecorator implements ITower {
	protected ITower tower;
	
	public UpgradeDecorator(ITower tower) {
		this.tower = tower;
	}
	
	public double getDamage() {
		return tower.getDamage();
	}
	
	public double getRange() {
		return tower.getRange();
	}
	
	public double getRateOfFire() {
		return tower.getRateOfFire();
	}
	
	public double getSlowingAbility() {
		return tower.getSlowingAbility();
	}

	public boolean isGroundShootingAbility() {
		return tower.isGroundShootingAbility();
	}

	public boolean isAerialShootingAbility() {
		return tower.isAerialShootingAbility();
	}

	public boolean isIceShootingAbility() {
		return tower.isIceShootingAbility();
	}
}
