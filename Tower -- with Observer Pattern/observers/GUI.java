package observers;

import assignment2.*;

public class GUI implements ITowerObserver {
	// singleton...
	private static GUI instance;

	private GUI() {
	}

	public static GUI getInstance() {
		if (instance == null)
			instance = new GUI();
		return instance;
	}

	// the input reference is the object in which the values have changed.
	public void update(ITower myReference) {
		// if the last upgrade was a DamageUpgrade, only display the new damage. Similar logic for the other two.
		if (myReference.getClass() == DamageUpgrade.class) {
			System.out.println("In tower with reference address: " + myReference + ", the following has changed:");
			System.out.println("Damage: " + myReference.getDamage() + "\n");
		}
		else if(myReference.getClass() == RangeUpgrade.class) {
			System.out.println("In tower with reference address: " + myReference + ", the following has changed:");
			System.out.println("Range: " + myReference.getRange() + "\n");
		}
		else if(myReference.getClass() == RateOfFireUpgrade.class) {
			System.out.println("In tower with reference address: " + myReference + ", the following has changed:");
			System.out.println("RateOfFire: " + myReference.getRateOfFire() + "\n");
		}
		// in this case, if the tower isn't being upgraded, it must be a composite tower.
		else {
			System.out.println("In tower with reference address: " + myReference + ", some of the following have changed: ");
			System.out.println("CompositeTower change, this is!!");
			System.out.println("Damage: " + myReference.getDamage());
			System.out.println("Range: " + myReference.getRange());
			System.out.println("RateOfFire: " + myReference.getRateOfFire());
			System.out.println("SlowingAbility: " + myReference.getSlowingAbility());
			System.out.println("AerialShootingAbility: " + myReference.isAerialShootingAbility());
			System.out.println("GroundShootingAbility: " + myReference.isGroundShootingAbility());
			System.out.println("IceShootingAbility: " + myReference.isIceShootingAbility() + "\n");
		}
	}

}
