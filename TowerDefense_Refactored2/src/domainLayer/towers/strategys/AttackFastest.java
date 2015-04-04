package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.Critter;

public class AttackFastest implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList) {
		double fastestSpeed = 0;
		Critter fastestCritterSoFar = null;
		for (Critter critter : critterList) {
			if (critter.getSpeed() > fastestSpeed) {
				fastestCritterSoFar = critter;
				fastestSpeed = fastestCritterSoFar.getSpeed();
			}
			else if (critter.getSpeed() == fastestSpeed) {
				if (critter.getSquare().getPositionInRoad() > fastestCritterSoFar.getSquare().getPositionInRoad()) {
					fastestCritterSoFar = critter;
				}
			}
		}
		return fastestCritterSoFar;
	}
}
