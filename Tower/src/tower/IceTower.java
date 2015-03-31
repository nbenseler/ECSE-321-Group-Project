package tower;

public class IceTower extends Tower {
	private static final double damage = 0;
	private static final double range = 30.0;
	private static final double rateOfFire = 30.0;
	private static final double slowingAbility = 10;
	private static final boolean groundShootingAbility = false;
	private static final boolean aerialShootingAbility = false;
	private static final boolean iceShootingAbility = true;
	
	public IceTower() {
		super(damage, range, rateOfFire, slowingAbility, groundShootingAbility, aerialShootingAbility, iceShootingAbility);
	}
	
}
