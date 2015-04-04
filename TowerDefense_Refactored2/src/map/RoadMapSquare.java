package map;

import java.util.ArrayList;

import critter.Critter;

public class RoadMapSquare extends MapSquare {
	private ArrayList<IMapSquareObserver> mapSquareObservers;
	private ArrayList<Critter> critterList;
	private RoadMapSquare nextRoadMapSquare;

	public RoadMapSquare() {
		super();
		mapSquareObservers = new ArrayList<>();
		critterList = new ArrayList<>();
	}

	public void addIMapSquareObserver(IMapSquareObserver mapSquareObserver) {
		if (!this.mapSquareObservers.contains(mapSquareObserver)) {
			this.mapSquareObservers.add(mapSquareObserver);
			this.notifyObservers();
		}
	}

	public void removeIMapSquareObserver(IMapSquareObserver mapSquareObserver) {
		this.mapSquareObservers.remove(mapSquareObserver);
	}

	public void notifyObservers() {
		for (IMapSquareObserver o : mapSquareObservers) {
			o.update(critterList);
		}
	}
	

	public void addCritter(Critter critter) {
		critterList.add(critter);
		for (IMapSquareObserver o : mapSquareObservers) {
			o.addCritter(critter);
		}
	}

	public void removeCritter(Critter critter) {
		critterList.remove(critter);
		for (IMapSquareObserver o : mapSquareObservers) {
			o.removeCritter(critter);
		}
	}

	public void setNextRoadMapSquare(RoadMapSquare nextRoadMapSquare) {
		this.nextRoadMapSquare = nextRoadMapSquare;
	}

	public RoadMapSquare getNextRoadMapSquare() {
		return this.nextRoadMapSquare;
	}

}
