package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Das Interface IOperations dient dazu, die jeweiligen Operationen auf den Zellen der Tabelle, abzukapseln und 
 * je nach Bedarf, eine der Operationen auszufuehren.
 */
public interface IOperations {
	/**
	 * Die Methode execute nimmt die Parameter eingabe in der betroffenen Zelle und den JTable table entgegen und fuehrt dann
	 * die jeweils gewuenschte Operation aus.
	 * @param eingabe
	 * 		Benutzer-Eingabe in die Zelle (als String)
	 * @param table
	 * 		JTable, in dem die Eingabe gemacht wird
	 */
	public void execute(String eingabe, JTable table);
}
