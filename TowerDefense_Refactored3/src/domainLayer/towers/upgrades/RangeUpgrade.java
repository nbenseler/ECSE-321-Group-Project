package domainLayer.towers.upgrades;

import java.util.ArrayList;

import domainLayer.critter.Critter;
import domainLayer.towers.ITower;

public class RangeUpgrade extends UpgradeDecorator {
	private double rangeMultiplyer = 2.0;
	public static final int cost = 20;

	public RangeUpgrade(ITower tower) {
		super(tower, cost);
	}
	
	@Override
	public double getRange() {
		return decoratee.getRange()*rangeMultiplyer;
	}
	
}
