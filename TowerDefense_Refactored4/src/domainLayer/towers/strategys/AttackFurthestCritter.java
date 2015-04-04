package domainLayer.towers.strategys;

import java.awt.Color;
import java.util.ArrayList;

import domainLayer.critter.AerialCritter;
import domainLayer.critter.AerialCritter1;
import domainLayer.critter.AerialCritter2;
import domainLayer.critter.Critter;
import domainLayer.critter.GroundCritter;
import domainLayer.critter.GroundCritter1;
import domainLayer.critter.GroundCritter2;
import domainLayer.towers.ITower;

public class AttackFurthestCritter implements IAttackStrategy {

	@Override
	public Critter attackCritter(ArrayList<Critter> critterList, ITower tower) {
		int furthestCritterPosition = -1;
		Critter furthestCritterSoFar = null;
		for (Critter critter : critterList) {
			if ((tower.isAerialShootingAbility() && (critter.getColor() == Color.RED)) || (tower.isGroundShootingAbility() && (critter.getColor() == Color.ORANGE)) || tower.isIceShootingAbility())
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
