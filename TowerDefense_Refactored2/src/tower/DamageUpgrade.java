package tower;

import java.util.ArrayList;

import critter.Critter;

public class DamageUpgrade extends UpgradeDecorator {
	private double damageMultiplyer = 1.2;
	public static final int cost = 30;

	public DamageUpgrade(ITower tower) {
		super(tower, cost);
	}
	
	@Override
	public double getDamage() {
		return decoratee.getDamage()*damageMultiplyer;
	}

}
