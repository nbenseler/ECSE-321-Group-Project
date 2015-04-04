package domainLayer.mapSquares;

import java.util.ArrayList;
import java.util.ListIterator;

import domainLayer.critter.Critter;
import domainLayer.towers.ITower;
import domainLayer.towers.TowerFactory;
import domainLayer.towers.strategys.AttackFurthestCritter;
import domainLayer.towers.strategys.IAttackStrategy;
import domainLayer.towers.upgrades.UpgradeFactory;

public class TowerMapSquare extends MapSquare implements IMapSquareObserver {
	private ITower tower;
	private IAttackStrategy attackStrategy;
	private ArrayList<Critter> critterList;
	private long lastShootingTime;

	public TowerMapSquare(String type) {
		this.tower = TowerFactory.getInstance().newTower(type);
		this.attackStrategy = new AttackFurthestCritter();
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
	
	public void setAttackStrategy(IAttackStrategy attackStrategy) {
		this.attackStrategy = attackStrategy;
	}

	public void attackCritters() {
		if (System.currentTimeMillis() - lastShootingTime > (1 / tower.getRateOfFire())*1000) {
			if (!critterList.isEmpty()) {
				if (tower.isIceShootingAbility()) {
					attackStrategy.attackCritter(critterList).setSpeed(critterList.get(0).getSpeed() / tower.getSlowingAbility());
				}
				attackStrategy.attackCritter(critterList).takeDamage((int) tower.getDamage());
				lastShootingTime = System.currentTimeMillis();
			}
		}
	}
}
