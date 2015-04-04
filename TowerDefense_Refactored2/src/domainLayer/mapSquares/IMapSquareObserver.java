package domainLayer.mapSquares;

import java.util.ArrayList;

import domainLayer.critter.Critter;

public interface IMapSquareObserver {
	public void update(ArrayList<Critter> cList);
	public void addCritter(Critter critter);
	public void removeCritter(Critter critter);
}
