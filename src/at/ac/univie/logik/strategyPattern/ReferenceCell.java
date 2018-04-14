package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Die Klasse ReferenceCell implementiert das Interface IOperations und stellt die Implementierung 
 * fuer die Methode execute zur Verfuegung. Der Wert einer Reference Cell wird im Feld der Eingabe
 * gesetzt.
 * @author Cordula Eggerth
 */
public class ReferenceCell implements IOperations{

	/**
	 * Es wird ueber die Methode execute ermittelt, was der Wert der referenzierten Zelle ist. 
	 * Dann wird der Wert der Zelle, in der die Eingabe gemacht wurde, auf den Wert, der sich
	 * in der referenzierten Zelle  befindet, gesetzt. 
	 * @param eingabe
	 * 		String der Eingabe in die Zelle, wo der Referenzwert gesetzt werden soll (z.B. =B3)
	 * @param table 
	 * 		JTable-Objekt der Tabelle, in der die Eingabe gemacht wird
	 */
	@Override
	public void execute(String eingabe, JTable table) {
		MySingletonPattern mySingletonPattern = MySingletonPattern.getInstance();
		eingabe = eingabe.replace("=","");
		int splitPos = mySingletonPattern.getSplitPosition(eingabe);
		int choseRow = mySingletonPattern.getStringPosition(eingabe.substring(0, splitPos))+1;
		int choseCol = Integer.parseInt(eingabe.substring(splitPos, eingabe.length()))-1;
		String value = (String) table.getModel().getValueAt(choseCol, choseRow);
		table.getModel().setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
		
	}

}
