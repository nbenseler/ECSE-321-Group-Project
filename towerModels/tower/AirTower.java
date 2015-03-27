package tower;

public class AirTower extends Tower {
	private static final double damage = 10.0;
	private static final double range = 10.0;
	private static final double rateOfFire = 10.0;
	private static final double slowingAbility = 0;
	
	public AirTower() {
		super(damage, range, rateOfFire, slowingAbility);
	}
	
}
