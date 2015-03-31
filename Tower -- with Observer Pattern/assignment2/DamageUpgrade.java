package assignment2;

public class DamageUpgrade extends UpgradeDecorator {
	private double damageMultiplyer = 1.2;

	public DamageUpgrade(ITower tower) {
		super(tower);
	}
	
	@Override
	public double getDamage() {
		return decoratee.getDamage()*damageMultiplyer;
	}
	
	
}
