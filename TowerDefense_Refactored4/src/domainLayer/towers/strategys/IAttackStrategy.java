package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.Critter;
import domainLayer.towers.ITower;

public interface IAttackStrategy {
	public Critter attackCritter(ArrayList<Critter> critterList, ITower tower);
}
