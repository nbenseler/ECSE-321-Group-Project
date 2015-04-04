package map;

import java.util.ArrayList;
import java.util.ListIterator;

import tower.ITower;
import tower.TowerFactory;
import tower.UpgradeFactory;
import critter.Critter;

public class TowerMapSquare extends MapSquare implements IMapSquareObserver {
	private ITower tower;
	private ArrayList<Critter> critterList;
	private long lastShootingTime;

	public TowerMapSquare(String type) {
		this.tower = TowerFactory.getInstance().newTower(type);
		critterList = new ArrayList<>();
		lastShootingTime = System.currentTimeMillis();
	}

	public void upgradeTower(String type) {

		this.tower = UpgradeFactory.getInstance().newUpgrade(type, tower);
	}

	public ITower getTower() {
		return tower;
	}

	public void update(ArrayList<Critter> cList) {
		// 
	}

	public void addCritter(Critter critter) {
		critterList.add(critter);
	}

	public void removeCritter(Critter critter) {
		critterList.remove(critter);
	}

	public void attackCritters() {
		if (System.currentTimeMillis() - lastShootingTime > tower.getRateOfFire()*1000) {
			if (!critterList.isEmpty()) {
				if (tower.isIceShootingAbility()) {
					critterList.get(0).setSpeed(critterList.get(0).getSpeed() / tower.getSlowingAbility());
				}
				critterList.get(0).takeDamage((int) this.tower.getDamage());
				lastShootingTime = System.currentTimeMillis();
			}
		}
	}
}
