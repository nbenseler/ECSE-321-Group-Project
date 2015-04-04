package domainLayer.towers.strategys;

import java.util.ArrayList;

import domainLayer.critter.Critter;

public interface IAttackStrategy {
	public Critter attackCritter(ArrayList<Critter> critterList);
}
