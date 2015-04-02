package tower;

import java.util.ArrayList;

import critter.critter;

public class RangeUpgrade extends UpgradeDecorator {
	private double rangeMultiplyer = 2.0;

	public RangeUpgrade(ITower tower) {
		super(tower);
	}
	
	@Override
	public double getRange() {
		return decoratee.getRange()*rangeMultiplyer;
	}
	
}
