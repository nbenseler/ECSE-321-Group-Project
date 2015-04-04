package tower;

public class IceTower extends Tower {
	private static final double damage = 0;
	private static final double range = 2;
	private static final double rateOfFire = 1;
	private static final double slowingAbility = 2;
	public static final int cost = 100;
	private static final boolean groundShootingAbility = false;
	private static final boolean aerialShootingAbility = false;
	private static final boolean iceShootingAbility = true;
	
	public IceTower() {
		super(damage, range, rateOfFire, slowingAbility, cost, groundShootingAbility, aerialShootingAbility, iceShootingAbility);
	}
	
}
