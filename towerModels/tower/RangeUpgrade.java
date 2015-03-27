package tower;

public class RangeUpgrade extends UpgradeDecorator {
	private double rangeMultiplyer = 1.2;

	public RangeUpgrade(ITower tower) {
		super(tower);
	}
	
	@Override
	public double getRange() {
		return tower.getRange()*rangeMultiplyer;
	}

}
