package tower;

public class Driver {

	public static void main(String[] args) {
	
	ITower tower1 = new GroundTower();
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());
	
	tower1 = new RangeUpgrade(tower1);
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());
	
	tower1 = new RangeUpgrade(tower1);
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());
	
	tower1 = new RateOfFireUpgrade(tower1);
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());
	
	tower1 = new RateOfFireUpgrade(tower1);
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());
	
	tower1 = new DamageUpgrade(tower1);
	
	System.out.println("Damage: " + tower1.getDamage() + ", Range: " + tower1.getRange() + ", RateOfFire: " + tower1.getRateOfFire());

	System.out.println();
	
	
	ITower tower2 = new AirTower();
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	
	tower2 = new RangeUpgrade(tower2);
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	
	tower2 = new RateOfFireUpgrade(tower2);
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	
	tower2 = new RateOfFireUpgrade(tower2);
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	
	tower2 = new DamageUpgrade(tower2);
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	
	tower2 = new DamageUpgrade(tower2);
	
	System.out.println("Damage: " + tower2.getDamage() + ", Range: " + tower2.getRange() + ", RateOfFire: " + tower2.getRateOfFire());
	System.out.println();
	
	
	ITower tower3 = new IceTower();
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	
	tower3 = new RangeUpgrade(tower3);
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	
	tower3 = new RangeUpgrade(tower3);
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	
	tower3 = new RateOfFireUpgrade(tower3);
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	
	tower3 = new RateOfFireUpgrade(tower3);
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	
	
	tower3 = new DamageUpgrade(tower3);
	
	System.out.println("Damage: " + tower3.getDamage() + ", Range: " + tower3.getRange() + ", RateOfFire: " + tower3.getRateOfFire());
	System.out.println();
	
	CompositeTower tower4 = new CompositeTower();
	tower4.addTower(tower1);
	tower4.addTower(tower2);
	tower4.addTower(tower3);
	
	System.out.println("AND THE ULTIMATE COMPOSITE TOWER!! \n \nDamage: " + tower4.getDamage() + ", Range: " + tower4.getRange() + ", RateOfFire: "
			+ tower4.getRateOfFire() + ", SlowingAbility: " + tower4.getSlowingAbility() + ", isGroundShooting: " + tower4.isGroundShootingAbility()
			+ ", isAerialShooting: " + tower4.isAerialShootingAbility() + ", isIceShooting: " + tower4.isIceShootingAbility());
	System.out.println();
	
	CompositeTower tower5 = new CompositeTower();
	tower5.addTower(tower1);
	tower5.addTower(tower3);
	
	System.out.println("THE ULTIMATE COMPOSITE TOWER #2!! \n \nDamage: " + tower5.getDamage() + ", Range: " + tower5.getRange() + ", RateOfFire: "
			+ tower5.getRateOfFire() + ", SlowingAbility: " + tower5.getSlowingAbility() + ", isGroundShooting: " + tower5.isGroundShootingAbility()
			+ ", isAerialShooting: " + tower5.isAerialShootingAbility() + ", isIceShooting: " + tower5.isIceShootingAbility());
	}
}
