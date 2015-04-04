package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.towers.ITower;

public class AttackStrongest implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList, ITower tower) {
		double highestCritterStrength = 0;
		Critter strongestCritterSoFar = null;
		for (Critter critter : critterList) {
			if ((tower.isAerialShootingAbility() && (AerialCritter.class.isInstance(critter))) || ((tower.isGroundShootingAbility() && GroundCritter.class.isInstance(critter)))||(tower.isIceShootingAbility()))
			{
			if (critter.getHealthPoints() > highestCritterStrength) {
				strongestCritterSoFar = critter;
				highestCritterStrength = critter.getHealthPoints();
			}
			else if (critter.getHealthPoints() == highestCritterStrength) {		
				if (critter.getSquare().getPositionInRoad() > strongestCritterSoFar.getSquare().getPositionInRoad()) {
					strongestCritterSoFar = critter;
					highestCritterStrength = critter.getHealthPoints();		
				}
			}
		}
		}
		return strongestCritterSoFar;
	}
}
