package tower;

public class RateOfFireUpgrade extends UpgradeDecorator {
	private double rateOfFireMultiplyer = 1.2;
	

	public RateOfFireUpgrade(ITower tower) {
		super(tower);
	}
	
	@Override
	public double getRateOfFire() {
		return tower.getRateOfFire()*rateOfFireMultiplyer;
	}

}
