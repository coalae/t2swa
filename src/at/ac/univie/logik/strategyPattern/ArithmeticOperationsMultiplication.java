package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Die Klasse ArithmeticOperationsMultiplication implementiert das Interface IOperations und 
 * bietet die Implementierung der Methode execute fuer die Multiplikation.
 */
public class ArithmeticOperationsMultiplication implements IOperations{

	/**
	 * Die Methode execute nimmt die Parameter eingabe und jtable entgegen und berechnet die 
	 * arithmetische Operation der Multiplikation.
	 * @param eingabe
	 * 		Eingabe-String der arithmetischen Operation der Multiplikation (z.B. =3*1) 
	 * @param table
	 * 		JTable-Objekt, in dem die Eingabe gemacht wurde 
	 */	
	@Override
	public void execute(String eingabe, JTable table) {
		MySingletonPattern mySingletonPattern = MySingletonPattern.getInstance();
		mySingletonPattern.checkArithmeticComponents(eingabe, "*", table);
	}

}
