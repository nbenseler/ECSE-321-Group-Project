package Map;

import java.awt.Color;
public class BossCritter extends critter {
	private Color c;	

	public BossCritter(RoadMapSquare s) {
		super(s);
		c=Color.CYAN;
		speed = 0.02;
		radius=0.6;
		damage = 10;
	}
	public Color getColor() {
		return c;
	}

}
