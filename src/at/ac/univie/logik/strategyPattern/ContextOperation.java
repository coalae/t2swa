package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/** 
 * Die Klasse ContextOperation instanziiert ein Objekt vom Typ IOperations, 
 * auf dem die Methode execute fuer die jeweilige Operation aufgerufen wird. 
 * Es werden fuer execute die Parameter eingabe und table uebergeben.
 */
public class ContextOperation {
	/**
	 * Instanzvariable: operations (Datentyp IOperations)
	 */
	private IOperations operations;
	
	/**
	 * Konstruktor: Instanzvariable operations wird hier gesetzt
	 * @param operations
	 * 		durchzufuehrende operation (als IOperations)
	 */
	public ContextOperation(IOperations operations){
		this.operations = operations;
	}
	
	/** 
	 * Die Methode execute fuehrt eine Operation fuer die eingabe, die 
	 * der Benutze in der Tabelle gemacht hat, aus.
	 * @param eingabe
	 * 		Eingabe des Benutzers als String
	 * @param table
	 * 		Tabelle table, in der die Operation ausgefuehrt wird (als JTable-Objekt)
	 */
	public void execute(String eingabe, JTable table ){
		operations.execute(eingabe, table);
	}
	
}
