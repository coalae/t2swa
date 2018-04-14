package at.ac.univie.logik.strategyPattern;

import javax.swing.JTable;

/**
 * Klasse MySingletonPattern beinhaltet Funktionen, die haeufig gebraucht werden, 
 * damit nicht jedes Mal ein neues Objekt von dieser Klasse erstellt werden muss.
 */
public class MySingletonPattern {
	/**
	 * Instanzvariablen
	 */
	public static MySingletonPattern mySingletonPattern;
	private int FromCellColumnPosition, FromCellRowPosition, TO_CELLColumnPosition, TO_CELLRowPosition;
	
	/** 
	 * getInstance fuer MySingletonPattern
	 * @return mySingletonPattern
	 * 		Instanz vom Typ MySingletonPattern
	 */
	public static MySingletonPattern getInstance(){
		if (mySingletonPattern == null)
			mySingletonPattern = new MySingletonPattern();
		return mySingletonPattern;
	}
	
	/**
	 * Die Methode findCeils berechnet die Zeilen- und Spaltenposition der FromCell und ToCell jeweils.
	 * @param eingabe
	 * 		Eingabe des Benutzers als String (z.B. =SUM(B2:B4))
	 * @param callElement
	 * 		Element, mit dem die Operation aufgerufen wird (z.B. =SUM( )
	 * 		
	 */
	public void findCeils(String eingabe, String callElement){
		int index = eingabe.indexOf(':');
		String FROM_CELL =  eingabe.substring(callElement.length(), index);//B2
		String TO_CELL = eingabe.substring(index+1, eingabe.lastIndexOf(')'));//B4
		int splitPosFROM_CELL = getSplitPosition(FROM_CELL);
		String FromCellColumnPositionStr = FROM_CELL.substring(0, splitPosFROM_CELL);//B
		FromCellColumnPosition = getStringPosition(FromCellColumnPositionStr)+1;
		FromCellRowPosition = Integer.parseInt(FROM_CELL.substring(splitPosFROM_CELL, FROM_CELL.length()))-1;//2
		//System.out.println(model.getValueAt(FromCellRowPosition,FromCellColumnPosition));
		
		int splitPosTO_CELL = getSplitPosition(TO_CELL);
		String TO_CELLColumnPositionStr = TO_CELL.substring(0, splitPosTO_CELL);
		TO_CELLColumnPosition = getStringPosition(TO_CELLColumnPositionStr) + 1;
		TO_CELLRowPosition = Integer.parseInt(TO_CELL.substring(splitPosTO_CELL, TO_CELL.length())) - 1;
		//System.out.println(model.getValueAt(TO_CELLRowPosition,TO_CELLColumnPosition));
	}
	
	/** 
	 * Die Methode getSplitPosition ermittelt die Trennung zwischen Spalte und Zeile (z.B. B2)
	 * @param input  
	 * 		Input, fuer den die Split-Position ermittelt werden soll (als String)
	 * @return splitposition 
	 * 		als int
	 */
	public int getSplitPosition( String input ) {
		for (int i = 0; i < input.length();i++){
		  if (!Character.isLetter( input.charAt(i)) )
			return i;
		}
		return -1;
	}
	
	/**
	 * Methode getStringPosition macht die Umwandlung von Buchstaben (der Spaltenueberschriften) in Zahlen (des Spaltenindex)
	 * (Quelle: http://stackoverflow.com/questions/25796592/program-to-convert-numbers-to-letters/25796773#25796773)
	 * @param spaltenUberschrift
	 * @return string position (als int)
	 */
	public int getStringPosition(String spaltenUberschrift) {
		spaltenUberschrift = spaltenUberschrift.toUpperCase();
		for(int i=0; i < 100000000; i++)
        {   
            int quotient, remainder;
            String result="";
            quotient=i;

            while (quotient >= 0)
            {
                remainder = quotient % 26;
                result = (char)(remainder + 65) + result;
                quotient = (int)Math.floor(quotient/26) - 1;
            }
            if (result.equals(spaltenUberschrift))
            	return i;
        }
		return -1;
	}
	
	public void checkArithmeticComponents(String eingabe, String opretaion, JTable table){
		int operPosition = eingabe.indexOf(opretaion);
		double first = 0;
		double second = 0;	
		String firstStr = eingabe.substring(1, operPosition);
		String secondStr = eingabe.substring(operPosition+1, eingabe.length());
		
		int splitPosFirst = getSplitPosition(firstStr);
		int splitPosSecond = getSplitPosition(secondStr);
		
		if (splitPosFirst != 0){
			
			int col = Integer.parseInt(firstStr.substring(splitPosFirst, firstStr.length())) -1;
			String charakter = firstStr.substring(0, splitPosFirst);
			int row = getStringPosition(charakter) + 1;
			first = Double.parseDouble(((String) table.getModel().getValueAt(col, row)).replace(',', '.'));	
			
		}else{
			first = Double.parseDouble(eingabe.substring(1, operPosition));
		}
			
		if (splitPosSecond != 0){
			int col = Integer.parseInt(secondStr.substring(splitPosSecond, secondStr.length())) -1;
			String charakter = secondStr.substring(0, splitPosSecond);
			int row = getStringPosition(charakter) + 1;
			second = Double.parseDouble(((String) table.getModel().getValueAt(col, row)).replace(',', '.'));
		}
		else
			second = Double.parseDouble(eingabe.substring(operPosition+1, eingabe.length()));
		
		if (opretaion.equals("+"))
			table.getModel().setValueAt(((first+second)+"").replace('.', ','), table.getSelectedRow(), table.getSelectedColumn());
		else if (opretaion.equals("-"))
			table.getModel().setValueAt(((first-second)+"").replace('.', ','), table.getSelectedRow(), table.getSelectedColumn());
		else if (opretaion.equals("*"))
			table.getModel().setValueAt(((first*second)+"").replace('.', ','), table.getSelectedRow(), table.getSelectedColumn());
		else if (opretaion.equals("/"))
			table.getModel().setValueAt(((first/second)+"").replace('.', ','), table.getSelectedRow(), table.getSelectedColumn());
	}
	
	/**
	 * get-Methode fuer Spaltenposition der FromCell
	 * @return FromCellColumnPosition
	 * 		Spaltenposition de FromCell als int
	 */
	public int getFromCellColumnPosition() {
		return FromCellColumnPosition;
	}


	/** 
	 * set-Methode fuer Spaltenposition der FromCell
	 * @param fromCellColumnPosition
	 */
	public void setFromCellColumnPosition(int fromCellColumnPosition) {
		FromCellColumnPosition = fromCellColumnPosition;
	}

	/**
	 * get-Methode fuer Zeilenposition der FromCell
	 * @return FromCellColumnPosition
	 * 		Zeilenposition der FromCell als int
	 */
	public int getFromCellRowPosition() {
		return FromCellRowPosition;
	}

	/** 
	 * set-Methode fuer Zeilenposition der FromCell
	 * @param fromCellRowPosition
	 */
	public void setFromCellRowPosition(int fromCellRowPosition) {
		FromCellRowPosition = fromCellRowPosition;
	}

	/**
	 * get-Methode fuer Spaltenposition der ToCell
	 * @return TO_CELLColumnPosition;
	 * 		Spaltenposition der ToCell als int
	 */
	public int getTO_CELLColumnPosition() {
		return TO_CELLColumnPosition;
	}

	/** 
	 * set-Methode fuer Spaltenposition der ToCell
	 * @param fromCellRowPosition
	 */	
	public void setTO_CELLColumnPosition(int tO_CELLColumnPosition) {
		TO_CELLColumnPosition = tO_CELLColumnPosition;
	}

	/**
	 * get-Methode fuer Zeilenposition der ToCell
	 * @return TO_CELLRowPosition;
	 * 		Zeilenposition der ToCell als int
	 */
	public int getTO_CELLRowPosition() {
		return TO_CELLRowPosition;
	}

	/** 
	 * set-Methode fuer Zeilenposition der ToCell
	 * @param tO_CELLRowPosition
	 */		
	public void setTO_CELLRowPosition(int tO_CELLRowPosition) {
		TO_CELLRowPosition = tO_CELLRowPosition;
	}

	/**
	 * Konstruktor von MySingletonPattern
	 */
	private MySingletonPattern(){
		mySingletonPattern = null;
	}
	
	
}
