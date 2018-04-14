package at.ac.univie.logik.observerPattern;

import java.util.Observable;
import java.util.Observer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import at.ac.univie.logik.CSVManager;

/**
 * Die Klasse Chart bietet die möglichkeit Charts zu erzeugen. Diese werden mit
 * implementierung des Observer-Patterns erzeugt und sind die Observer. Die
 * Instanzvariablen merken sich die Settings, die vom User eingegeben wurden,
 * und werden dann zum Zeichnen eines neuen Charts verwendet. Ein neuer Chart
 * wird erstellt sobald eine Änderung im CSV vorgenommen wird.
 * 
 * @author Martin
 *
 */
public class Chart implements Observer {

	private String title;
	private String xAxisName;
	private String yAxisName;
	private int range;
	private String xAxis;
	private String yAxis;
	private int row;
	private CSVManager csv;
	private ChartFrame chartFrm;
	private String chartType;

	/**
	 * Mit diesem Konstruktor wird ein LineChart erstellt.
	 * 
	 * @param title
	 *            (Titel des Fensters)
	 * @param xAxisName
	 *            (Benennung der xAchse)
	 * @param yAxisName
	 *            (Benennung der yAchse)
	 * @param range
	 *            (Auswahl wieviele Zellen gezeichnet werden)
	 * @param row
	 *            (Welche Zeile soll als Barchart dargestellt werden (numeric
	 *            values only))
	 * @param csv
	 *            Ein CSVManager-Objekt wird übergeben in dem alle Daten des
	 *            CSVs enthalten sind.
	 */

	public Chart(String title, String xAxisName, String yAxisName, int range, int row, CSVManager csv) {
		this.title = title;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.range = range;
		this.row = row;
		this.csv = csv;
		this.csv.addObserver(this);
		chartType = "lineChart";
		createLineChart();

	}

	/**
	 * Mit diesem Konstruktor wird ein BarChart erstellt.
	 * 
	 * @param title
	 *            (Titel des Fensters)
	 * @param xAxisName
	 *            (Benennung der xAchse)
	 * @param yAxisName
	 *            (Benennung der yAchse)
	 * @param range
	 *            (Auswahl wieviele Zeilen gezeichnet werden)
	 * @param xAxis
	 *            (Welche Spalte soll auf der xAchse aufgetragen werden)
	 * @param yAxis
	 *            (Welche Spalte soll auf der yAchse aufgetragen werden)
	 * @param csv
	 *            Ein CSVManager-Objekt wird übergeben in dem alle Daten des
	 *            CSVs enthalten sind.
	 */
	public Chart(String title, String xAxisName, String yAxisName, int range, String xAxis, String yAxis,
			CSVManager csv) {
		this.title = title;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.range = range;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.csv = csv;
		this.csv.addObserver(this);
		chartType = "barChart";
		createBarChart();
	}

	/**
	 * Methode zum Zeichnen des Bar-Charts
	 */
	public void createBarChart() {
		int xAxisIndex = 0;
		int yAxisIndex = 0;
		String titleArray[] = csv.createSpaltentitel(csv.getTitle().length);

		for (int i = 0; i < titleArray.length; i++) {
			if (titleArray[i].toLowerCase().equals(xAxis.toLowerCase())) {
				xAxisIndex = i;
			}
			if (titleArray[i].toLowerCase().equals(yAxis.toLowerCase())) {
				yAxisIndex = i;
			}
		}

		// private Object[][] data;
		if (range > csv.getData().length - 1)
			range = csv.getData().length - 3;

		System.out.println(range);
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (int i = 0; i < range; i++) {
			try {
				dcd.setValue(Double.parseDouble(((String) csv.getData()[i][yAxisIndex]).replace(",", ".")), yAxisName,
						(String) csv.getData()[i][xAxisIndex]);
			} catch (ArrayIndexOutOfBoundsException e1) {
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			} catch (NumberFormatException e2) {
				throw new IllegalArgumentException(
						"Im gewählten Bereich ein oder mehrere nicht erlaubte Zeichen enthalten!");
			} catch (NullPointerException e3) {
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}

		}

		JFreeChart jchart = ChartFactory.createBarChart3D(title, xAxisName, yAxisName, dcd, PlotOrientation.VERTICAL,
				true, true, false);

		chartFrm = new ChartFrame(title, jchart, true);
		chartFrm.setVisible(true);
		chartFrm.setSize(960, 540);
	}

	/**
	 * Methode zum Zeichnen des Line-Charts
	 */
	public void createLineChart() {

		if (csv.getData()[0].length < range)
			range = csv.getData()[0].length - 1;

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (int i = 0; i < range; i++) {
			// Jeden Wert überprüfen
			try {
				dcd.setValue(Double.parseDouble(((String) csv.getData()[row - 1][i + 1]).replace(",", ".")), yAxisName,
						(String) csv.getData()[0][i + 1]);
			} catch (ArrayIndexOutOfBoundsException e1) {
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			} catch (NumberFormatException e2) {
				throw new IllegalArgumentException(
						"Im gewählten Bereich ein oder mehrere nicht erlaubte Zeichen enthalten!");
			} catch (NullPointerException e3) {
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}

		}

		JFreeChart jchart = ChartFactory.createLineChart(title, xAxisName, yAxisName, dcd, PlotOrientation.VERTICAL,
				true, true, false);

		chartFrm = new ChartFrame(title, jchart, true);
		chartFrm.setVisible(true);
		chartFrm.setSize(960, 540);

	}

	/**
	 * Update-Methode des Observer-Patterns. Das CSVManager-Objekt wird
	 * entsprechend auf neuesten stand gebracht. Außerdem wird hier ausgewertet
	 * um welchen Chart-Type es sich handelt, und die entsprechende
	 * update-Method aufgerufen.
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("an update happened :OO:O:O:O:OOO");
		this.csv = (CSVManager) o;

		if (this.chartType.equals("lineChart")) {
			updateLinechart();
		}

		if (this.chartType.equals("barChart")) {
			updateBarchart();
		}

	}

	/**
	 * Hier wird das alte Fenster ausgeblendet, und ein neues gezeichnet durch Aufruf der Methode createBarChart().
	 */
	private void updateBarchart() {
		this.chartFrm.setVisible(false);
		createBarChart();

	}

	/**
	 * Hier wird das alte Fenster ausgeblendet, und ein neues gezeichnet durch Aufruf der Methode createLineChart().
	 */
	private void updateLinechart() {
		this.chartFrm.setVisible(false);
		createLineChart();
	}
}
