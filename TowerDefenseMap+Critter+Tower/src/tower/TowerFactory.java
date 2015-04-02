package tower;

public class TowerFactory {

	private static TowerFactory uniqueInstance;

	private TowerFactory() {

	}

	public static TowerFactory getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TowerFactory();
		}
		return uniqueInstance;
	}

	public ITower newTower(String type) {
		ITower t;
		
		// calling myReference(ITower) sets the myReference variable in every tower to its own reference.
		switch (type.toLowerCase()) {
		case "aerialtower":
			t = new AerialTower();
			return (ITower) t;
		case "groundtower":
			t = new GroundTower();
			return (ITower) t;
		case "icetower":
			t = new IceTower();
			return (ITower) t;
		case "compositetower":
			t = new CompositeTower();
			return (ITower) t;
		default:
			return null;
		}
	}

}
