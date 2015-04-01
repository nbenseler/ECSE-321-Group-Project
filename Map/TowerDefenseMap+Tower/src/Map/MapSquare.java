package Map;



public class MapSquare {							// superclass of RoadMapSquare and TowerMapSquar
	private int xPosition;
	private int yPosition;
	protected boolean road;							// boolean defines if the square is a road or not
	

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getxPosition() {
		return xPosition;
	}
	public boolean isRoad() 
	{
		return road;
	}
	public void addObserver(ITowerObserver tower)
	{
		
	}
	
}

