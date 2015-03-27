package tower;

public class IceShootingAbility extends TowerShootingStrategy {

	public IceShootingAbility(ITower tower) {
		super(tower);
	}

	@Override
	public double getDamage() {
		return 0;
		
	}
}
