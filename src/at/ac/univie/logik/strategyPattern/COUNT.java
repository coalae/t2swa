package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Die Klasse COUNT implementiert das Interface IOperations und 
 * bietet die Implementierung der Methode execute fuer die Zaehlfunktion.
 */
public class COUNT implements IOperations{

	/**
	 * Instanzvariable vom Typ MySingletonPattern, das das Auffinden und die
	 * Positionsbestimmung der Zellen macht
	 */
	private MySingletonPattern mySingletonPattern;
	
	/**
	 * Konstruktor
	 */
	public COUNT(){
		mySingletonPattern = MySingletonPattern.getInstance(); 
	}
	
	/**
	 * Implementierung der Methode execute des Interface IOperations.
	 * FromCell-Zeilenposition und -Spaltenposition und TO_CELL-Zeilenposition 
	 * und -Spaltenposition werden zugewiesen und die Anzahl (COUNT-Funktion)
	 * wird berechnet.
	 * @param eingabe 
	 * 		Eingabe zur Berechnung (z.B. =COUNT(A2:A5)) als String
	 * @param table
	 * 		JTable, in dem die Berechnung gemacht wird
	 */
	@Override
	public void execute(String eingabe, JTable table) {
		mySingletonPattern.findCeils(eingabe,"=COUNT(");
		int FromCellRowPosition = mySingletonPattern.getFromCellRowPosition();
		int TO_CELLRowPosition = mySingletonPattern.getTO_CELLRowPosition();
		int FromCellColumnPosition = mySingletonPattern.getFromCellColumnPosition();
		int TO_CELLColumnPosition = mySingletonPattern.getTO_CELLColumnPosition();
		
		int count = 0;
		if (FromCellRowPosition != TO_CELLRowPosition)
		{
			
			for(int i = FromCellRowPosition; i <= TO_CELLRowPosition; i++){
				count++;
			}
			
		}else if(FromCellColumnPosition != TO_CELLColumnPosition){
			for(int i = FromCellColumnPosition ; i <= TO_CELLColumnPosition; i++){
				count++;
			}
			
		}
		table.getModel().setValueAt(count, table.getSelectedRow(), table.getSelectedColumn());
		System.out.println("Count: " + count);
		
	}

}
