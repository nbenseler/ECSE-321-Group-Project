package Map;

import java.util.ArrayList;


public class RoadMapSquare extends MapSquare {				// Subclass of mapSquare
	
	private int numberOfCritters=0;				// stores number of critters on the square currently
	private int xPosition;						// stores squares position
	private int yPosition;
	private RoadMapSquare nextSquare;
	
	public RoadMapSquare getNextSquare() {
		return nextSquare;
	}

	public void setNextSquare(RoadMapSquare nextSquare) {
		this.nextSquare = nextSquare;
	}

	private ArrayList<ITowerObserver> observers = new ArrayList<ITowerObserver>();		// arrayList of observers (towers observing this square)
	protected boolean firstRoad=false;
	public boolean isFirstRoad() {
		return firstRoad;
	}

	public void setFirstRoad(boolean firstRoad) {
		this.firstRoad = firstRoad;
	}

	public RoadMapSquare(int x, int y)			//RoadMapSquare constructor
	{
		xPosition =x;
		yPosition =y;
		road = true;
		//System.out.println("Created Road Square succesfully at: ("+xPosition+","+yPosition+")");
	}
	public ArrayList getObservers() {			
		return observers;
	}
	public void setObservers(ArrayList observers) {
		this.observers = observers;
	}
	public void notifyObservers()					// Notifies the observers that a critter has been added to this square
	{
		for (ITowerObserver o: observers)
		{
			o.updateTowerCritterAdded(this);
		}
		this.numberOfCritters++;
	}
	public void notifyObserversCritterRemoved()		// Notifies observers that a critter has been removed from this square
	{
		for (ITowerObserver o: observers)
		{
			o.updateTowerCritterRemoval(this);
		}
		this.numberOfCritters--;
	}
	public int getNumberOfCritters() {				
		return numberOfCritters;
	}
	public void setNumberOfCritters(int numberOfCritters) {
		this.numberOfCritters = numberOfCritters;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public void addObserver(ITowerObserver tower)			// add observers (towers) to the observer list of this square
	{
		observers.add(tower);
	}

}
