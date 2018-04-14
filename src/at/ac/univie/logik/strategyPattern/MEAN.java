package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Die Klasse MEAN implementiert das Interface IOperations und 
 * bietet die Implementierung der Methode execute fuer die Durchschnittsfunktion.
 */
public class MEAN implements IOperations{

	/**
	 * Instanzvariable vom Typ MySingletonPattern, das das Auffinden und die
	 * Positionsbestimmung der Zellen macht
	 */
	private MySingletonPattern mySingletonPattern;

	/**
	 * Konstruktor
	 */
	public MEAN(){
		mySingletonPattern = MySingletonPattern.getInstance(); 
	}

	/**
	 * Implementierung der Methode execute des Interface IOperations.
	 * FromCell-Zeilenposition und -Spaltenposition und TO_CELL-Zeilenposition 
	 * und -Spaltenposition werden zugewiesen und der Durchschnitt (MEAN-Funktion)
	 * wird berechnet.
	 * @param eingabe 
	 * 		Eingabe zur Berechnung (z.B. =MEAN(A2:A5)) als String
	 * @param table
	 * 		JTable, in dem die Berechnung gemacht wird
	 */
	@Override
	public void execute(String eingabe, JTable table) {
		mySingletonPattern.findCeils(eingabe,"=MEAN(");
		int FromCellRowPosition = mySingletonPattern.getFromCellRowPosition();
		int TO_CELLRowPosition = mySingletonPattern.getTO_CELLRowPosition();
		int FromCellColumnPosition = mySingletonPattern.getFromCellColumnPosition();
		int TO_CELLColumnPosition = mySingletonPattern.getTO_CELLColumnPosition();
		
		int count = 0;
		double summe = 0;
		if (FromCellRowPosition != TO_CELLRowPosition)
		{
			
			for(int i = FromCellRowPosition; i <= TO_CELLRowPosition; i++){
				summe += Double.parseDouble(((String) table.getModel().getValueAt(i,TO_CELLColumnPosition)).replace(',', '.'));
				count++;
			}
			
		}else if(FromCellColumnPosition != TO_CELLColumnPosition){
			for(int i = FromCellColumnPosition ; i <= TO_CELLColumnPosition; i++){
				summe += Double.parseDouble(((String) table.getModel().getValueAt(FromCellColumnPosition,i)).replace(',', '.'));
				count++;
			}
			
		}
		table.getModel().setValueAt(((summe/count) +"").replace('.', ','), table.getSelectedRow(), table.getSelectedColumn());
		System.out.println("Mean: " + summe/count);
	}

}
