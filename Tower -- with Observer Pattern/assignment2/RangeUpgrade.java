package assignment2;

public class RangeUpgrade extends UpgradeDecorator {
	private double rangeMultiplyer = 1.2;

	public RangeUpgrade(ITower tower) {
		super(tower);
	}
	
	@Override
	public double getRange() {
		return decoratee.getRange()*rangeMultiplyer;
	}

}
