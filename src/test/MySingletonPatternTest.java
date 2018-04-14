package test;

import static org.junit.Assert.*;
import at.ac.univie.logik.strategyPattern.*;

import org.junit.Test;

public class MySingletonPatternTest {

	@Test
	public void testGetSplitPosition() {
		MySingletonPattern msp = MySingletonPattern.getInstance();
		String testInput = "AA44";
		int ergebnis = 2;
		assertEquals(ergebnis, msp.getSplitPosition(testInput));
	}

	@Test
	public void testGetStringPosition() {
		MySingletonPattern msp = MySingletonPattern.getInstance();
		String testInput = "AAAA44";
		int ergebnis = 4;
		assertEquals(ergebnis, msp.getSplitPosition(testInput));
	}


}
