package assignment2;

public interface ITower {
	public double getDamage();
	public double getRange();
	public double getRateOfFire();
	public double getSlowingAbility();
	public boolean isGroundShootingAbility();
	public boolean isAerialShootingAbility();
	public boolean isIceShootingAbility();
	public void notifyObservers(ITower myReference);
	public void myReference(ITower t);
}
