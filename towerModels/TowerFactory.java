package towerModels;

public abstract class TowerFactory {
	
	public static Tower createTower(int type) {
		Tower t;
		switch (type) {
		case 100:
			t = new IceTower();
			t.initialize(t);
		case 200:
			t = new AerialTower();
			t.initialize(t);
		case 300:
			t = new GroundTower();
			t.initialize(t);
		default:
			t = new IceTower();
			t.initialize(t);
		}
		return t;
	}
}
