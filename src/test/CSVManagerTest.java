package test;

import static org.junit.Assert.*;

import at.ac.univie.logik.CSVManager;
import org.junit.Test;

public class CSVManagerTest {

	@Test
	public void testCSVManager() {
		String path = "implementation/src/test/resources/test_it.csv";
		CSVManager cs = new CSVManager(path);
		assertEquals(path, cs.getCsvFilePath());
	}

	@Test
	public void testReadCSV() {
		CSVManager cs = new CSVManager("implementation/src/test/resources/test_it.csv");
		cs.readCSV();
		Object testData [][] = {{"Test_A", "Test_B", "Test_C"}, {"2","8","8"}};
		Object data [][] = cs.getData();
		Object a = testData[1][0];
		Object b = data [1][0];
		assert(b.equals(a)); 
	}

	@Test
	public void testWriteCSV() {
		CSVManager cs = new CSVManager("implementation/src/test/resources/test_it.csv");
		cs.readCSV();
		String ergebnis = "Test";
		cs.changeData(0, 0, "Test");
		Object data[] [] = cs.getData();
		Object a = data [0][0];
		assert(ergebnis.equals(a));
	}

	@Test
	public void testGetCountOfLine() {
		CSVManager cs = new CSVManager("implementation/src/test/resources/test_it.csv");
		cs.readCSV();
		int ergebnis = 9;
		assertEquals(ergebnis, cs.getCountOfLine());
	}

	@Test
	public void testChangeData() {
		CSVManager cs = new CSVManager("implementation/src/test/resources/test_it.csv");
		cs.readCSV();
		cs.changeData(1, 1, "newData");
		Object data[] [] = cs.getData();
		Object ergebnis = data[1][1];
		assert(ergebnis.equals("newData"));
	}

}
