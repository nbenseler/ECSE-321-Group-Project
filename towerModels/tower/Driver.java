package tower;

public class Driver {

	public static void main(String[] args) {
	ITower tower = new AirTower();
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());
	
	tower = new RangeUpgrade(tower);
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());
	
	tower = new RangeUpgrade(tower);
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());
	
	tower = new RateOfFireUpgrade(tower);
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());
	
	tower = new DamageUpgrade(tower);
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());
	
	tower = new DamageUpgrade(tower);
	
	System.out.println("Damage: " + tower.getDamage() + ", Range: " + tower.getRange() + ", RateOfFire: " + tower.getRateOfFire());

	}
}
