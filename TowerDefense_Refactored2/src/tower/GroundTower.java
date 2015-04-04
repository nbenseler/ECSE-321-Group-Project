package tower;

public class GroundTower extends Tower {
	private static final double damage = 20.0;
	private static final double range = 5;
	private static final double rateOfFire = 1;
	private static final double slowingAbility = 0;
	public static final int cost = 200;
	private static final boolean groundShootingAbility = true;
	private static final boolean aerialShootingAbility = false;
	private static final boolean iceShootingAbility = false;
	
	public GroundTower() {
		super(damage, range, rateOfFire, slowingAbility, cost, groundShootingAbility, aerialShootingAbility, iceShootingAbility);
	}
	
}
 