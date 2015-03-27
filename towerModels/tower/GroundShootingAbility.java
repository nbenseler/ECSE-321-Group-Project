package tower;

public class GroundShootingAbility extends TowerShootingStrategy {

	public GroundShootingAbility(ITower tower) {
		super(tower);
	}

	@Override
	public double getDamage() {
		return 0;
		
	}
}
