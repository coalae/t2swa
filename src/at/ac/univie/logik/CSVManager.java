package at.ac.univie.logik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Observable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Die Klasse CSVManager bietet public Methoden, dass man CSV-Dateien lesen kann (read CSV) und schreiben bzw. speichern kann (write CSV).
 * Ausserdem gibt es eine private Methode, dass man aus dem eingelesenen CSV-File eine Tabelle erstellen kann (setTableWithTitle), in der 
 * Titel (Spaltennamen) und Daten (Zelleninhalte) gesetzt werden.
 * Weitere Methoden gibt es fuer das Handling der CSV-Files, die das Einlesen und Speichern unterstuetzen, wie beispielsweise 
 * die Methode getCountOfLine (die die Zeilenanzahl der Daten im CSV-File ermittelt), getTitel (die den Titel zurueckgibt) oder 
 * die Methode changeData (die an der gegebenen Position bestehend aus row und column Angabe den Dateninhalt aendert bzw mit einem 
 * neuen Data setzt.
 */
public class CSVManager extends Observable{
	private String csvFilePath;
	private String[] title;
	private Object[][] data;
	
	/**
	 * Dem Konstruktor CSVManager wird der Parameter csvFilePath (als String) uebergeben und es wird die Instanzvariable
	 * csvFilePath gesetzt.
	 * @param csvFilePath - Pfad der CSV-Datei
	 */
	public CSVManager(String csvFilePath){
		super();
		this.setCsvFilePath(csvFilePath);
	}
        
	/**
	 * Die Methode readCSV liest eine CSV-Datei mit Hilfe ihres Dateipfades (siehe Instanzvariable csvFilePath) ein und
	 * erstellt die Tabelle.
	 */
	public void readCSV(){
		
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(getCsvFilePath()));
            String text;            
            int firstLine = 0; // 0 ... man ist in der ersten zeile (erklaerung); 1 ... man ist in zweiter zeile (spaltennamen); ab 2 ... dateninhalte
            int size = 0;
            while ((text = bufferedReader.readLine()) != null) {
            	if (firstLine == 0){
            		firstLine = 1;
            	}else if (firstLine == 1){
            		setTitle(text.split(";")); // anzahl der spalten ermitteln
            		data = new Object[getCountOfLine()][getTitle().length]; // zellenmatrix erstellen

            		firstLine = 2;
            	}
            	else{
	                data[size]= text.split(";"); // setzt die dateninhalte pro zeile
	                size++;
            	}

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                	bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        setTableWithTitle();
        setTitle(createSpaltentitel(getTitle().length));
	}
	
	/**
	 * Die Methode setTableWithTitle ist eine private Methode und setzt die Dateninhalte der
	 * Tabelle und die Spaltenueberschriften, wobei die erste mit den Spaltenueberschriften 
	 * beginnt.
	 */
	private void setTableWithTitle(){
		Object[][]newData = new Object[data.length+1][data[0].length];
		for(int i = 0; i < data.length; i++)
			for (int f = 0; f < data[0].length; f++){
				if (i == 0)
					newData[i][f] = getTitle()[f];
				else
					newData[i][f] = data[i-1][f];
			}

		data = newData;
	}
	
	/**
	 * Die Methode writeCSV schreibt bzw. speichert eine CSV-Datei an dem angegebenen CSV-Dateipfad. 
	 * Alle Bestandteile der CSV-Datei werden hier gemaess der CSV-Syntax in das File geschrieben.
	 * @param separator 
	 * 		Separator (als char)
	 */	
	public void writeCSV(char separator){
		
		try {
			Writer writer = new FileWriter(getCsvFilePath());
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i  < getTitle().length; i++){
				builder.append(getTitle()[i] + separator);
			}
			builder.append("\n");
			for (int i = 0; i < data.length; i++){
				for (int f = 0; f < data[0].length; f++){
					builder.append(data[i][f] + "" +separator);
				}
				builder.append("\n");
			}
			
			writer.write(builder.toString());
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Die Methode getCountOfLine ist eine Methode, die von der public Methode readCSV verwendet wird. 
	 * Sie ermittelt die Anzahl der Zeilen der CSV-Datei und uebergibt diese an als int (lines).
	 * @return lines (Anzahl der Zeilen der CSV-Datei)
	 */
	public int getCountOfLine(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(getCsvFilePath()));
		} catch (FileNotFoundException eFileNotFound) {
			eFileNotFound.printStackTrace();
		}
		int lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException eIO) {
			eIO.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	
	/**
	 * Get-Methode fuer die Instanzvariable title. 
	 * @return title (als String)
	 */
	public String[] getTitel(){
		//title = new String[title.length];
		//getTitelList(5);
		return getTitle();	
	}
	
	/**
	 * Get-Methode fuer die Instanzvariable data.
	 * @return data (als Object[][])
	 */
	public Object[][] getData(){
		return data;
	}
	
	/**
	 * Die Methode changeData aendert den Inhalt eines Datenelements, das in Form von Zeilen- und Spaltenposition angegeben wird.
	 * 
	 * @param row
	 * 		Zeilenposition des zu aendernden Datenobjekts (als int)
	 * @param col
	 * 		Spaltenposition des zu aendernden Datenobjekts (als int)
	 * @param newData
	 * 		neuer Dateninhalt, das an die Stelle des alten gesetzt werden soll (als Object)
	 */
	public void changeData (int row, int col, Object newData){
		data[row][col] = newData;
		//System.out.println(data[row][col]);
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Die Methode getTitelList ist eine private Klassenmethode und gibt die Liste von Spaltentiteln (als Buchstaben
	 * bzw. Strings) zurueck.
	 * (Quelle: http://stackoverflow.com/questions/25796592/program-to-convert-numbers-to-letters/25796773#25796773) 
	 * @param lenght (als int, Spaltenanzahl)
	 * @return title (als String Array)
	 */
	public String[] createSpaltentitel(int lenght){
		String title[] = new String[lenght+2];
		title[0] = "";
        for(int x=0; x<=lenght; x++)
        {   
            int quotient, remainder;
            String result="";
            quotient=x;

            while (quotient >= 0)
            {
                remainder = quotient % 26;
                result = (char)(remainder + 65) + result;
                quotient = (int)Math.floor(quotient/26) - 1;
            }
            title[x+1] = result;
        }
        return title;
	}
	
	
	
	/*
	 * Die Methode createBarChart ist eine public Method, und dient dazu die gewünschten Spalten als BarChart zu zeichnen.
	 * @param title (Titel des Fensters)
	 * @param xAxisName (Benennung der xAchse)
	 * @param yAxisName (Benennung der yAchse)
	 * @param range	(Auswahl wieviele Zellen gezeichnet werden)
	 * @param xAxisIndex (Welche Spalte soll auf der xAchse aufgetragen werden)
	 * @param yAxisIndex (Welche Spalte soll auf der yAchse aufgetragen werden(Numeric values only))
	 */
	
	public  void createBarChart(String title, String xAxisName, String yAxisName,
			int range, String xAxis, String yAxis) {
		int xAxisIndex = 0;
		int yAxisIndex = 0;
		String titleArray[] = createSpaltentitel(title.length());
		
		for(int i=0; i<titleArray.length; i++){
			if(titleArray[i].toLowerCase().equals(xAxis.toLowerCase())){
				xAxisIndex = i;
			}
			if(titleArray[i].toLowerCase().equals(yAxis.toLowerCase())){
				yAxisIndex = i;
			}
		}
		//TODO: Überprüfung der Werte
		
		//private Object[][] data;
		if (range > data.length-1)
			range = data.length-3;
		
		System.out.println(range);
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (int i = 0; i < range; i++) {
			try{	
				dcd.setValue(Double.parseDouble(((String)data[i+1][yAxisIndex]).replace(",", ".")), yAxisName, (String)data[i+1][xAxisIndex]);
			}catch(ArrayIndexOutOfBoundsException e1){
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}
			catch(NumberFormatException e2){
				throw new IllegalArgumentException("Im gewählten Bereich ein oder mehrere nicht erlaubte Zeichen enthalten!");
			}catch(NullPointerException e3){
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}
			
		}

		JFreeChart jchart = ChartFactory.createBarChart3D(title, xAxisName, yAxisName, dcd, PlotOrientation.VERTICAL,
				true, true, false);
		


		ChartFrame chartFrm = new ChartFrame(title, jchart, true);
		chartFrm.setVisible(true);
		chartFrm.setSize(960, 540);

	}
	
	
	
	/*
	 * Die Methode createLineChart ist eine public Method, und dient dazu die gewünschten Zeilen als LineChart zu zeichnen.
	 * @param title (Titel des Fensters)
	 * @param xAxisName (Benennung der xAchse)
	 * @param yAxisName (Benennung der yAchse)
	 * @param range	(Auswahl wieviele Zellen gezeichnet werden)
	 * @param zeile (Welche Zeile soll als Barchart dargestellt werden (numeric values only))
	 */
	public void createLineChart(String titleName, String xAxisName, String yAxisName,
			int range, int zeile) {
		
		if (data[0].length<range)
			range = data[0].length-1;
		
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (int i = 0; i < range; i++) {
			//Jeden Wert überprüfen
			try{
				dcd.setValue(Double.parseDouble(((String)data[zeile-1][i+1]).replace(",", ".")), yAxisName, (String)data[0][i+1]);
			}catch(ArrayIndexOutOfBoundsException e1){
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}catch(NumberFormatException e2){
				throw new IllegalArgumentException("Im gewählten Bereich ein oder mehrere nicht erlaubte Zeichen enthalten!");
			}catch(NullPointerException e3){
				throw new IllegalArgumentException("Der gewählte Bereich ist leer!");
			}
			
			
		}

		JFreeChart jchart = ChartFactory.createLineChart(titleName, xAxisName, yAxisName, dcd, PlotOrientation.VERTICAL,
				true, true, false);
		


		ChartFrame chartFrm = new ChartFrame(titleName, jchart, true);
		chartFrm.setVisible(true);
		chartFrm.setSize(960, 540);

	}

	/**
	 * Get-Methode fuer den Pfad der Datei.
	 * @return csvFilePath, String
	 */
	public String getCsvFilePath() {
		return csvFilePath;
	}

	/**
	 * Set-Methode fuer den Pfad der Datei.
	 */
	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	/**
	 * Get-Methode fuer den Titel.
	 * @return title, String
	 */
	public String[] getTitle() {
		return title;
	}

	/**
	 * Set-Methode fuer den Titel.
	 */
	public void setTitle(String[] title) {
		this.title = title;
	}
	
	
 //TEST MAIN METHOD: 
//	public static void main(String[] args){
//		String spaltenUberschrift= "BC";
//		for(int x=0; x < 100000000; x++)
//        {   
//            int quotient, remainder;
//            String result="";
//            quotient=x;
//
//            while (quotient >= 0)
//            {
//                remainder = quotient % 26;
//                result = (char)(remainder + 65) + result;
//                quotient = (int)Math.floor(quotient/26) - 1;
//            }
//            System.out.println(x + ":" +result);
//            if (result.equals(spaltenUberschrift)){
//            	System.out.println(x);
//            	break;
//            	
//            }
//            
//        }
//        
//	}
	
}
