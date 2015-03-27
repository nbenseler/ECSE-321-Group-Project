package tower;

public abstract class TowerShootingStrategy extends UpgradeDecorator {
	
	private boolean abilityAdded;

	public TowerShootingStrategy(ITower tower) {
		super(tower);
		abilityAdded = true;
	}
	
	public double getDamage() {
		return 1;
	}
	
	public double getRange() {
		return 1;
	}
	
	public double getRateOfFire() {
		return 1;
	}

}
