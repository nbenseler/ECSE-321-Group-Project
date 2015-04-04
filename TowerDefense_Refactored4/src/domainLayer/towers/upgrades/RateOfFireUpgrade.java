package domainLayer.towers.upgrades;

import java.util.ArrayList;

import domainLayer.critter.Critter;
import domainLayer.towers.ITower;

public class RateOfFireUpgrade extends UpgradeDecorator {
	private double rateOfFireMultiplyer = 1.2;
	public static final int cost = 10;
	

	public RateOfFireUpgrade(ITower tower) {
		super(tower, cost);
	}
	
	@Override
	public double getRateOfFire() {
		return decoratee.getRateOfFire()*rateOfFireMultiplyer;
	}


}
