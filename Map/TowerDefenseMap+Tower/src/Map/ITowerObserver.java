package Map;


public interface ITowerObserver {							// interface used to facilitate the observer principle
	public void updateTowerCritterAdded(RoadMapSquare m);	//unimplemented methods which are implemented by Tower Class
	public void updateTowerCritterRemoval(RoadMapSquare m);
	public int getTowerRange();

}
