package at.ac.univie.logik.strategyPattern;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import at.ac.univie.view.Main;

/**
 * Die Klasse ArithmeticOperationsDivision implementiert das Interface IOperations und 
 * bietet die Implementierung der Methode execute fuer die Division.
 */
public class ArithmeticOperationsDivision implements IOperations{

	/**
	 * Die Methode execute nimmt die Parameter eingabe und jtable entgegen und berechnet die 
	 * arithmetische Operation der Division.
	 * @param eingabe
	 * 		Eingabe-String der arithmetischen Operation der Division (z.B. =3/1) 
	 * @param table
	 * 		JTable-Objekt, in dem die Eingabe gemacht wurde 
	 */
	@Override
	public void execute(String eingabe, JTable table) {
		MySingletonPattern mySingletonPattern = MySingletonPattern.getInstance();
		mySingletonPattern.checkArithmeticComponents(eingabe, "/", table);
		
	}

}
