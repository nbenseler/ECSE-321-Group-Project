package driver;

import observers.GUI;
import assignment2.*;

public class Driver {

	public static void main(String[] args) {

		TowerFactory tFactory = TowerFactory.getInstance();
		UpgradeFactory uFactory = UpgradeFactory.getInstance();
		GUI gui = GUI.getInstance();

		ITower tower1 = tFactory.newTower("aerialTower");
		ITower tower2 = tFactory.newTower("groundTower");
		ITower tower3 = tFactory.newTower("iceTower");
		ITower cT = tFactory.newTower("compositeTower");
		
		((Tower) tower1).addTowerObserver(gui);
		((Tower) tower2).addTowerObserver(gui);
		((Tower) tower3).addTowerObserver(gui);
		((CompositeTower) cT).addTowerObserver(gui);

		// tower1 = new DamageUpgrade(tower1);
		
		System.out.println("tower1's current damage: " + tower1.getDamage());
		System.out.println("Lets update tower1's damage...");
		tower1 = uFactory.newUpgrade("damage", tower1);
		
		System.out.println("tower1's current range: " + tower1.getRange());
		System.out.println("Lets update tower1's range...");
		tower1 = uFactory.newUpgrade("range", tower1);
		
		System.out.println("tower1's current range: " + tower1.getRange());
		System.out.println("Lets update tower1's range...");
		tower1 = uFactory.newUpgrade("range", tower1);
		
		System.out.println("tower1's current range: " + tower1.getRange());
		System.out.println("Lets update tower1's range...");
		tower1 = uFactory.newUpgrade("range", tower1);
		
		System.out.println("tower1's current rateOfFire: " + tower1.getRateOfFire());
		System.out.println("Lets update tower1's rateOfFire...");
		tower1 = uFactory.newUpgrade("rateOfFire", tower1);
		
		System.out.println("tower1's current rateOfFire: " + tower1.getRateOfFire());
		System.out.println("Lets update tower1's rateOfFire...");
		tower1 = uFactory.newUpgrade("rateOfFire", tower1);
		
		System.out.println("tower3's current damage: " + tower3.getDamage());
		System.out.println("Lets update tower3's damage...");
		tower3 = uFactory.newUpgrade("damage", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("tower3's current rateOfFire: " + tower3.getRateOfFire());
		System.out.println("Lets update tower3's rateOfFire...");
		tower3 = uFactory.newUpgrade("rateOfFire", tower3);
		
		System.out.println("FIRST Composite Tower additon...");
		((CompositeTower) cT).addTower(tower2);

		System.out.println("New Composite Tower additon...");
		((CompositeTower) cT).addTower(tower1);
		
		System.out.println("New Composite Tower additon...");
		((CompositeTower) cT).addTower(tower3);
		
	}

}
