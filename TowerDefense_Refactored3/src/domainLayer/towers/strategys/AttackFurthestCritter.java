package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.towers.ITower;

public class AttackFurthestCritter implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList, ITower tower) {
		int furthestCritterPosition = -1;
		Critter furthestCritterSoFar = null;
		for (Critter critter : critterList) {
			if ((tower.isAerialShootingAbility() && (AerialCritter.class.isInstance(critter))) || ((tower.isGroundShootingAbility() && GroundCritter.class.isInstance(critter)))||(tower.isIceShootingAbility()))
			{
				//if (critter.getSquare().getPositionInRoad() > furthestCritterPosition) {
				furthestCritterSoFar = critter;
				furthestCritterPosition = critter.getSquare().getPositionInRoad();
			}
		}
		return furthestCritterSoFar;
	}
}
/*
 * package domainLayer.mapSquares;

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
attackStrategy.attackCritter(critterList,tower).setSpeed(critterList.get(0).getSpeed() / tower.getSlowingAbility());
}
Critter critterToAttack = attackStrategy.attackCritter(critterList, tower);
if (critterToAttack!=null)
{
critterToAttack.takeDamage((int) tower.getDamage());
}
lastShootingTime = System.currentTimeMillis();
}
}
}
}


package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.towers.AerialTower;
import domainLayer.towers.ITower;

public class AttackFurthestCritter implements IAttackStrategy {

@Override
public Critter attackCritter(ArrayList<Critter> critterList, ITower tower) {
int furthestCritterPosition = -1;
Critter furthestCritterSoFar = null;
for (Critter critter : critterList) {
if ((tower.isAerialShootingAbility() && (critter.getClass() == AerialCritter.class) || (tower.isGroundShootingAbility() && critter.getClass() == GroundCritter.class)))
{
if (critter.getSquare().getPositionInRoad() > furthestCritterPosition) {
furthestCritterSoFar = critter;
furthestCritterPosition = critter.getSquare().getPositionInRoad();
}
}
}
return furthestCritterSoFar;
}
}
*/