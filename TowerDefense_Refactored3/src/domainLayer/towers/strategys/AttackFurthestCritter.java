package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.Critter;

public class AttackFurthestCritter implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList) {
		int furthestCritterPosition = -1;
		Critter furthestCritterSoFar = null;
		for (Critter critter : critterList) {
			if (critter.getSquare().getPositionInRoad() > furthestCritterPosition) {
				furthestCritterSoFar = critter;
			}
		}
		return furthestCritterSoFar;
	}
}
