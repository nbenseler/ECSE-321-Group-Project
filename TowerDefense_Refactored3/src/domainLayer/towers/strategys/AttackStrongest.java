package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.Critter;

public class AttackStrongest implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList) {
		double highestCritterStrength = 0;
		Critter strongestCritterSoFar = null;
		for (Critter critter : critterList) {
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
		return strongestCritterSoFar;
	}
}
