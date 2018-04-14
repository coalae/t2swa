package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Die Klasse ArithmeticOperationsSubtraction implementiert das Interface IOperations und 
 * bietet die Implementierung der Methode execute fuer die Subtraktion.
 */
public class ArithmeticOperationsSubtraction implements IOperations{

	/**
	 * Die Methode execute nimmt die Parameter eingabe und jtable entgegen und berechnet die 
	 * arithmetische Operation der Subtraktion.
	 * @param eingabe
	 * 		Eingabe-String der arithmetischen Operation der Subtraktion (z.B. =3-1) 
	 * @param table
	 * 		JTable-Objekt, in dem die Eingabe gemacht wurde 
	 */
	@Override
	public void execute(String eingabe, JTable table) {
		MySingletonPattern mySingletonPattern = MySingletonPattern.getInstance();
		mySingletonPattern.checkArithmeticComponents(eingabe, "-", table);
	}

}
