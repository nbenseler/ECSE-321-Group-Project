package towerModels;

public abstract class TowerSpecifications {	
}
abstract class IceTowerSpecifications extends TowerSpecifications{
	protected static final int damage = 1;
	protected static final int buyValue = 1;
	protected static final int refundValue = 1;
	protected static final int range = 1;
	protected static final int power = 1;
	protected static final int rateOfFire = 1;
}

abstract class AerialTowerSpecifications extends TowerSpecifications {
	protected static final int damage = 2;
	protected static final int buyValue = 2;
	protected static final int refundValue = 2;
	protected static final int range = 2;
	protected static final int power = 2;
	protected static final int rateOfFire = 2;
}

abstract class GroundTowerSpecifications extends TowerSpecifications {
	protected static final int damage = 3;
	protected static final int buyValue = 3;
	protected static final int refundValue = 3;
	protected static final int range = 3;
	protected static final int power = 3;
	protected static final int rateOfFire = 3;
}
