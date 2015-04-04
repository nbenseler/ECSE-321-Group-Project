package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.AerialCritter;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.towers.ITower;

public class AttackFastest implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList, ITower tower) {
		double fastestSpeed = 0;
		Critter fastestCritterSoFar = null;
		for (Critter critter : critterList) {
			if ((tower.isAerialShootingAbility() && (AerialCritter.class.isInstance(critter))) || ((tower.isGroundShootingAbility() && GroundCritter.class.isInstance(critter)))||(tower.isIceShootingAbility()))
			{
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
		}
		return fastestCritterSoFar;
	}
}
