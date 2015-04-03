package map;

public abstract class MapSquare {
	private int xPosition;
	private int yPosition;

	public int getxPosition() {
		return xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public String toString() {
		return "xPosition: " + xPosition + ", yPosition: " + yPosition;
	}
}
