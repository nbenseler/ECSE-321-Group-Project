package tower;

public class AirTower extends Tower {
	private static final double damage = 10.0;
	private static final double range = 10.0;
	private static final double rateOfFire = 10.0;
	private static final double slowingAbility = 0;
	private static final boolean groundShootingAbility = false;
	private static final boolean aerialShootingAbility = true;
	private static final boolean iceShootingAbility = false;
	
	public AirTower() {
		super(damage, range, rateOfFire, slowingAbility, groundShootingAbility, aerialShootingAbility, iceShootingAbility);
	}
	
}
