package Map;

import java.awt.Color;

public class AerialCritter extends critter {

	public AerialCritter(RoadMapSquare s) {
		super(s);
		c=Color.ORANGE;
		speed =0.04;
		radius = 0.6;
		distance = 0.20;
		damage=2;
	}

	private Color c;
	public Color getColor() {
		return c;
	}
}
