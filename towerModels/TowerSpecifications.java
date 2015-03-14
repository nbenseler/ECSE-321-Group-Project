package towerModels;

public abstract class TowerSpecifications {
	
}

class IceTowerSpecifications extends TowerSpecifications{
	protected static int damage = 1;
	protected static int buyValue = 1;
	protected static int refundValue = 1;
	protected static int range = 1;
	protected static int power = 1;
	protected static int rateOfFire = 1;
}

class AerialTowerSpecifications extends TowerSpecifications {
	protected static int damage = 2;
	protected static int buyValue = 2;
	protected static int refundValue = 2;
	protected static int range = 2;
	protected static int power = 2;
	protected static int rateOfFire = 2;
}

class GroundTowerSpecifications extends TowerSpecifications {
	protected static int damage = 3;
	protected static int buyValue = 3;
	protected static int refundValue = 3;
	protected static int range = 3;
	protected static int power = 3;
	protected static int rateOfFire = 3;
}
