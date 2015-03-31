package assignment2;

public class UpgradeFactory {

	private static UpgradeFactory uniqueInstance;
	// singleton...
	private UpgradeFactory() {
		
	}

	public static UpgradeFactory getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new UpgradeFactory();
		}
		return uniqueInstance;
	}

	public ITower newUpgrade(String type, ITower tower) {
		ITower t;
		// calling myReference(ITower) sets the myReference variable in every upgrade to its own reference.
		switch (type.toLowerCase()) {
		case "damage":
			t = new DamageUpgrade(tower);
			t.myReference(t);
			return t;
		case "range":
			t = new RangeUpgrade(tower);
			t.myReference(t);
			return t;
		case "rateoffire":
			t = new RateOfFireUpgrade(tower);
			t.myReference(t);
			return t;
		default:
			return null;
		}
	}
}
