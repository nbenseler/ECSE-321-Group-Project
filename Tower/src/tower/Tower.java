package tower;

public abstract class Tower implements ITower {
	// perhaps I should just make these values protected and store them in the subclasses?
	private double damage;
	private double range;
	private double rateOfFire;
	private double slowingAbility;
	private boolean groundShootingAbility;
	private boolean airShootingAbility;
	private boolean iceShootingAbility;
	
	public Tower(double damage, double range, double rateOfFire, double slowingAbility, boolean groundShootingAbility,
			boolean airShootingAbility, boolean iceShootingAbility) {
		
		this.damage = damage;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.slowingAbility = slowingAbility; // update this
		this.groundShootingAbility = groundShootingAbility;
		this.airShootingAbility = airShootingAbility;
		this.iceShootingAbility = iceShootingAbility;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public double getRange() {
		return range;
	}
	
	public double getRateOfFire() {
		return rateOfFire;
	}

	public double getSlowingAbility() {
		return slowingAbility;
	}

	public boolean isGroundShootingAbility() {
		return groundShootingAbility;
	}

	public boolean isAerialShootingAbility() {
		return airShootingAbility;
	}

	public boolean isIceShootingAbility() {
		return iceShootingAbility;
	}

}
