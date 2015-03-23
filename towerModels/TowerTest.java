package towerModels;

import static org.junit.Assert.*;

import org.junit.Test;

public class TowerTest {
	
	@Test
	public void testTowerSpecification() {
		assertEquals(IceTowerSpecifications.damage, 1);
	}
	
	@Test
	public void testTowerCreation() {
		Tower aT = TowerFactory.createTower(200);
		assertEquals(aT.getDamage(), 1);
	}
	
	@Test
	public void testSeeUpGrades() {
		Tower aT = TowerFactory.createTower(200);
		aT.upgrade(0);
		aT.upgrade(1);
		aT.upgrade(1);
		aT.upgrade(2);
		aT.upgrade(2);
		aT.upgrade(2);
		
		double[] upgradesTest = aT.seeUpgrades();
		
		assertEquals((int) upgradesTest[0], 3);
		assertEquals((int) upgradesTest[1], 12);
		assertEquals((int) upgradesTest[2], 39);
	}
	
	@Test
	public void testSimplifiedUpgrade() {
		Tower aT = TowerFactory.createTower(200);
		aT.upgrade(1);
		aT.upgrade(1);
		aT.upgrade(2);
		aT.upgrade(1);
		aT.upgrade(2);
		aT.upgrade(1);
		
		double[] upgradesTest = aT.seeUpgrades();
		
		assertEquals((int) upgradesTest[0], -1);
		assertEquals((int) upgradesTest[1], 18);
		assertEquals((int) upgradesTest[2], 26);
		
		Tower iT = TowerFactory.createTower(100);
		iT.upgrade(0);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		
		upgradesTest = iT.seeUpgrades();
		
		assertEquals((int) upgradesTest[0], 3);
		assertEquals((int) upgradesTest[1], -1);
		assertEquals((int) upgradesTest[2], 39);
		
		Tower gT = TowerFactory.createTower(300);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(2);
		gT.upgrade(2);
		
		upgradesTest = gT.seeUpgrades();
		
		assertEquals((int) upgradesTest[0], -1);
		assertEquals((int) upgradesTest[1], 18);
		assertEquals((int) upgradesTest[2], 26);
	}
	
	@Test
	public void testNextUpgrade() {
		Tower aT = TowerFactory.createTower(200);
		aT.upgrade(0);
		aT.upgrade(2);
		aT.upgrade(2);

		double[][] upgradesNextTest = aT.nextUpgrades();
		
		assertEquals((int) upgradesNextTest[0][0], 6);
		assertEquals((int) upgradesNextTest[0][1], 300);
		
		assertEquals((int) upgradesNextTest[1][0], 6);
		assertEquals((int) upgradesNextTest[1][1], 1000);
		
		
		assertEquals((int) upgradesNextTest[2][0], 39);
		assertEquals((int) upgradesNextTest[2][1], 30000);
	}
	
	
	@Test
	public void testvalue() {
		Tower aT = TowerFactory.createTower(200);
		aT.upgrade(1);
		aT.upgrade(1);
		aT.upgrade(2);
		aT.upgrade(1);
		aT.upgrade(2);
		aT.upgrade(1);
		
		int value = aT.valueOfTower();
		
		assertEquals(value, 36000);

		
		Tower iT = TowerFactory.createTower(100);
		iT.upgrade(0);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		iT.upgrade(2);
		
		value = iT.valueOfTower();
		
		assertEquals(value, 60100);
		
		Tower gT = TowerFactory.createTower(300);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(1);
		gT.upgrade(2);
		gT.upgrade(2);
		
		value = gT.valueOfTower();
		
		assertEquals(value, 36000);
		
		
		Tower gT2 = TowerFactory.createTower(300);
		gT2.upgrade(1);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(1);
		gT2.upgrade(1);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(2);
		
		value = gT2.valueOfTower();
		
		assertEquals(value, 66000);
	}
	
	@Test 
	public void testSync() {
		Tower gT2 = TowerFactory.createTower(300);
		gT2.upgrade(1);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(1);
		gT2.upgrade(0);
		gT2.upgrade(0);
		gT2.upgrade(1);
		gT2.upgrade(2);
		gT2.upgrade(2);
		
		gT2.sync();
		
		assertEquals(gT2.getDamage(), 6);
		assertEquals(gT2.getRange(), 18);
		assertEquals(gT2.getRateOfFire(), 39);
		assertEquals(gT2.getBuyValue(), 66400);
	}
	
}