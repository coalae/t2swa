package at.ac.univie.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import at.ac.univie.logik.CSVManager;
import at.ac.univie.logik.observerPattern.Chart;
import at.ac.univie.logik.strategyPattern.ArithmeticOperationsAddition;
import at.ac.univie.logik.strategyPattern.ArithmeticOperationsDivision;
import at.ac.univie.logik.strategyPattern.ArithmeticOperationsMultiplication;
import at.ac.univie.logik.strategyPattern.ArithmeticOperationsSubtraction;
import at.ac.univie.logik.strategyPattern.COUNT;
import at.ac.univie.logik.strategyPattern.ContextOperation;
import at.ac.univie.logik.strategyPattern.MEAN;
import at.ac.univie.logik.strategyPattern.ReferenceCell;
import at.ac.univie.logik.strategyPattern.SUM;

public class Main {

	/** 
	 * Instanzvariablen 
	 */
	private JFrame frame;
	private JTable table;
	private JButton btnsaveAsODF;
	private JPanel panel;
	private JButton btnSaveCSVFile;
	private CSVManager csvManager;
	private DefaultTableModel model = null;
	private JPanel panel_1;
	private JButton btnOpenFile;
	private final JFileChooser fileChooserOpen;
	private JButton generateChart;
	
	public static final String[]diagrams = {"LineChart","BarChart"};

	/**
	 * Application starten.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Application anlegen und fileChooser initialisieren.
	 */
	public Main() {
		
		fileChooserOpen = new JFileChooser();
		//fileChooserSave = new JFileChooser();
		initialize();
	}

	/**
	 * Inhalte des JFrame initialisieren.
	 */
	private void initialize() {
		
		/**
		 * JFrame anlegen
		 */
		frame = new JFrame();
		//frame.setResizable(false);
		frame.setSize(640, 480);
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		/**
		 * JPanel anlegen
		 */
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Spreadsheet Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 15;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		/** 
		 * JButton "Open File" anlegen
		 */
		btnOpenFile = new JButton("Open File");
		GridBagConstraints gbc_btnOpenFile = new GridBagConstraints();
		gbc_btnOpenFile.gridx = 0;
		gbc_btnOpenFile.gridy = 0;
		panel.add(btnOpenFile, gbc_btnOpenFile);
		
		/**
		 * JScrollPane anlegen
		 */
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.gridwidth = 14;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		/** 
		 * JPanel anlegen
		 */
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 7;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 14;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{30, 120, 0};
		gbl_panel_1.rowHeights = new int[]{29, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		/** 
		 * JButton "Save As CSV" anlegen
		 */
		btnSaveCSVFile = new JButton("Save As CSV");
		btnSaveCSVFile.setEnabled(false);
		GridBagConstraints gbc_btnSaveCSVFile = new GridBagConstraints();
		gbc_btnSaveCSVFile.gridwidth = 2;
		gbc_btnSaveCSVFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveCSVFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveCSVFile.anchor = GridBagConstraints.NORTH;
		gbc_btnSaveCSVFile.gridx = 0;
		gbc_btnSaveCSVFile.gridy = 0;
		panel_1.add(btnSaveCSVFile, gbc_btnSaveCSVFile);
		
		/**
		 * JButton "Save As ODF" anlegen
		 */
		btnsaveAsODF = new JButton("Save As ODF");
		btnsaveAsODF.setEnabled(false);
		GridBagConstraints gbc_btnsaveAsODF = new GridBagConstraints();
		gbc_btnsaveAsODF.gridwidth = 2;
		gbc_btnsaveAsODF.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnsaveAsODF.gridx = 0;
		gbc_btnsaveAsODF.gridy = 1;
		panel_1.add(btnsaveAsODF, gbc_btnsaveAsODF);
		
		
		/**
		 * JButton "GenerateChart" anlegen
		 */
		generateChart = new JButton("Generate Chart");
		generateChart.setEnabled(false);
		GridBagConstraints gbc_generateChart = new GridBagConstraints();
		gbc_generateChart.gridwidth = 2;
		gbc_generateChart.fill = GridBagConstraints.HORIZONTAL;
		gbc_generateChart.gridx = 0;
		gbc_generateChart.gridy = 2;
		panel_1.add(generateChart, gbc_generateChart);
		/**
		
		
		/**
		 *	ActionListener fuer Button "generateChart" anlegen
		 */
		generateChart.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				Frame frame = new Frame();
				String answer = (String)JOptionPane.showInputDialog(frame,"What diagram do you want to create?","DiagramChooser",JOptionPane.QUESTION_MESSAGE, 
				        null,diagrams,diagrams[0]);
				//String title, String xAxisName, String yAxisName,int range, int zeile
				if(answer.equals("LineChart")){
					JTextField title = new JTextField(5);
				    JTextField xAxisName = new JTextField(5);
				    JTextField yAxisName = new JTextField(5);
				    JTextField range = new JTextField(5);
				    JTextField row = new JTextField(5);

				    JPanel myPanel = new JPanel();
				    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				    myPanel.add(new JLabel("Title:"));
				    myPanel.add(title);
				    myPanel.add(new JLabel("Name of the objects on the x-axis:"));
				    myPanel.add(xAxisName);
				    myPanel.add(new JLabel("Name of their values on the y-axis:"));
				    myPanel.add(yAxisName);
				    myPanel.add(new JLabel("Number of elements:"));
				    myPanel.add(range);
				    myPanel.add(new JLabel("Row (1-n):"));
				    myPanel.add(row);

				    int result = JOptionPane.showConfirmDialog(null, myPanel,
				        "Please specify your Chart-Configuration!", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
				    	try{
				    		Integer.parseInt(range.getText());
				    		Integer.parseInt(row.getText());
				    	
				    	}catch(NumberFormatException e1){
				    		JOptionPane.showMessageDialog(frame,
	            				    "The Field range, must contain Integers",
	            				    "ChartError",
	            				    JOptionPane.ERROR_MESSAGE);
				    		return;
				    	}
				    	if(title.getText().length()==0 || xAxisName.getText().length()==0 || yAxisName.getText().length()==0){
				    		JOptionPane.showMessageDialog(frame,
	            				    "No field can be left Empty!",
	            				    "ChartError",
	            				    JOptionPane.ERROR_MESSAGE);
				    	}
				    	try{
				    		//csvManager.createLineChart(title.getText(), xAxisName.getText(), yAxisName.getText(), Integer.parseInt(range.getText()),Integer.parseInt(row.getText()));
				    		new Chart(title.getText(), xAxisName.getText(), yAxisName.getText(), Integer.parseInt(range.getText()),Integer.parseInt(row.getText()),csvManager);
				    	}catch(IllegalArgumentException e1){
				    		JOptionPane.showMessageDialog(frame,
            				    e1.getMessage(),
            				    "ChartError",
            				    JOptionPane.ERROR_MESSAGE);
				    	}
				    }

				}
				
				if(answer.equals("BarChart")){
					JTextField title = new JTextField(5);
				    JTextField xAxisName = new JTextField(5);
				    JTextField yAxisName = new JTextField(5);
				    JTextField range = new JTextField(5);
				    JTextField xAxis = new JTextField(5);
				    JTextField yAxis = new JTextField(5);

				    JPanel myPanel = new JPanel();
				    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				    myPanel.add(new JLabel("Title:"));
				    myPanel.add(title);
				    myPanel.add(new JLabel("Name of the objects on the x-axis:"));
				    myPanel.add(xAxisName);
				    myPanel.add(new JLabel("Name of their values on the y-axis:"));
				    myPanel.add(yAxisName);
				    myPanel.add(new JLabel("Number of elements:"));
				    myPanel.add(range);
				    myPanel.add(new JLabel("Columname of the x-Axis (A-Z):"));
				    myPanel.add(xAxis);
				    myPanel.add(new JLabel("Columname of the y-Axis (A-Z):"));
				    myPanel.add(yAxis);

				    int result = JOptionPane.showConfirmDialog(null, myPanel,
				        "Please specify your Chart-Configuration!", JOptionPane.OK_CANCEL_OPTION);
				    if (result == JOptionPane.OK_OPTION) {
				    	try{
				    		Integer.parseInt(range.getText());

				    	
				    	
				    	
				    	}catch(NumberFormatException e1){
				    		JOptionPane.showMessageDialog(frame,
	            				    "The Fields range, xAxisIndex and yAxisIndex must contain Integers",
	            				    "ChartError",
	            				    JOptionPane.ERROR_MESSAGE);
				    		return;
				    	}
				    	if(title.getText().length()==0 || xAxisName.getText().length()==0 || yAxisName.getText().length()==0){
				    		JOptionPane.showMessageDialog(frame,
	            				    "No field can be left Empty!",
	            				    "ChartError",
	            				    JOptionPane.ERROR_MESSAGE);
				    	}
				    	
				    try{
//				    	csvManager.createBarChart(title.getText(), xAxisName.getText(), yAxisName.getText(), Integer.parseInt(range.getText()),xAxis.getText(), 
//				    			yAxis.getText());
				    	new Chart(title.getText(), xAxisName.getText(), yAxisName.getText(), Integer.parseInt(range.getText()),xAxis.getText(), 
				    			yAxis.getText(),csvManager);
				    }catch(IllegalArgumentException e1){
				    	JOptionPane.showMessageDialog(frame,
            				    e1.getMessage(),
            				    "ChartError",
            				    JOptionPane.ERROR_MESSAGE);
				    }
				    
				    }

				}
			}
		});
		
		
		/**
		 *  ActionListener fuer Button "Save As ODF" anlegen
		 */
		btnsaveAsODF.addActionListener(new ActionListener() {
			/**
			 * Die Methode actionPerformed enthaelt die Aktionen, die durchgefuehrt werden, 
			 * wenn der Benutzer auf den Button "Save As ODF" klickt.
			 * Die Methode versucht, eine Datei mit .ods Endung zu speichern. Wenn dies 
			 * moeglich ist, wird das derzeitige model (d.h. die Dateiinhalte der Tabelle) 
			 * in einem OpdenDocumentFormat Spreadsheet gespeichert. 
			 * Ist die Speicherung nicht moeglich, wird eine Fehlermeldung angezeigt.
			 */
            public void actionPerformed(ActionEvent e) {
            	int returnVal = fileChooserOpen.showSaveDialog(null);
            	
            	if (returnVal == JFileChooser.APPROVE_OPTION){
            		String path=fileChooserOpen.getSelectedFile().getAbsolutePath();
            		if (path.toLowerCase().endsWith(".ods") && model != null){
    	            	final File file = new File(path);
    	            	  try {
    						SpreadSheet.createEmpty(model).saveAs(file);
    					} catch (FileNotFoundException e1) {
    						e1.printStackTrace();
    					} catch (IOException e1) {
    						e1.printStackTrace();
    					}
                	}else{
                		JOptionPane.showMessageDialog(frame,
            				    "The file name must be '*.ods'",
            				    "File name error",
            				    JOptionPane.ERROR_MESSAGE);
                	}
            	}
            	
            }	
        });
		
		/** 
		 * ActionListener fuer Button "Save As CSVFile" anlegen
		 */
		btnSaveCSVFile.addActionListener(new ActionListener() {
			/**
			 * Die Methode actionPerformed enthaelt die Aktionen, die durchgefuehrt werden, 
			 * wenn der Benutzer auf den Button "Save As CSV" klickt.
			 * Die Methode versucht, das derzeit in der Tabelle geoeffnete File in der CSV-
			 * Datei zu speichern (bzw. zu "schreiben"). Bei erfolgreicher Speicherung, wird
			 * eine Meldung angezeigt fuer den Benutzer.
			 */			
            public void actionPerformed(ActionEvent e) {
            	csvManager.writeCSV(';');
            	JOptionPane.showMessageDialog(frame, "The CSV-File changed and saved!!");
            }	
        });
		
		/**
		 * ActionListener fuer Button "Open File" anlegen
		 */
		btnOpenFile.addActionListener(new ActionListener() {
			/**
			 * Die Methode actionPerformed enthaelt die Aktionen, die durchgefuehrt werden, 
			 * wenn der Benutzer auf den Button "Open File" klickt.
			 * Die Methode oeffnet eine Datei mit .csv Endung. Wenn dies 
			 * moeglich ist, werden die Dateninhalte des CSV-Files in der Tabelle dargestellt.
			 * Wenn ein ausgewaehltes File keine .csv Endung hat, wird dem Benutzer eine
			 * Fehlermeldung angezeigt. 
			 */
            public void actionPerformed(ActionEvent e) {
            	
            	int returnVal = fileChooserOpen.showDialog(null, "Open CSV File");
            	fileChooserOpen.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));
            	if (returnVal == JFileChooser.APPROVE_OPTION){
            		if (fileChooserOpen.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".csv"))
            		{
            			csvManager = new CSVManager(fileChooserOpen.getSelectedFile().getAbsolutePath());
            			csvManager.readCSV();
            			btnSaveCSVFile.setEnabled(true);
            			btnsaveAsODF.setEnabled(true);
            			generateChart.setEnabled(true);
            			setTheDataInTheTable(); // setzt restliche daten
            			for (int i = 0; i < table.getRowCount(); i++) { // setzt spaltenindex-zahlen (in 1. spalte)
            	            table.setValueAt((i + 1) + "", i, 0);
            	        }
            			table.setShowGrid(false);
            			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            			table.setPreferredScrollableViewportSize(new Dimension(50, 0));
            			table.getColumnModel().getColumn(0).setPreferredWidth(50);
            			table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

            	            @Override
            	            public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            	                boolean selected = table.getSelectionModel().isSelectedIndex(row);
            	                Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, -2);
            	                ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
            	                if (selected) {
            	                    component.setFont(component.getFont().deriveFont(Font.BOLD));
            	                    component.setForeground(Color.red);
            	                } else {
            	                    component.setFont(component.getFont().deriveFont(Font.PLAIN));
            	                }
            	                return component;
            	            }
            	        });
            			
            			
            			scrollPane.setViewportView(table);
            			
            		}
            		else{
            			JOptionPane.showMessageDialog(frame,
            				    "You can choose only .csv File",
            				    "File choose error",
            				    JOptionPane.ERROR_MESSAGE);
            		}
            	}
            	
            }	
        });
		
		
	}
	
	/**
	 * Die Methode setTheDataInTheTable setzt die Dateninhalte und Spaltentitel in der Tabelle.
	 */
	private void setTheDataInTheTable(){
		
		model = new DefaultTableModel(csvManager.getData(), csvManager.getTitel()){
 
			private static final long serialVersionUID = 1L;
//			boolean[] canEdit = new boolean[]{
//	                    false, true, true, false, false,false,false, true
//			};
//			
			/** 
			 * Die Methode isCellEditable gibt an, ob die betrachtete Zelle editierbar ist oder nicht.
			 * @param rowIndex
			 * 		Zeilenindex der betrachteten Zelle
			 * @param columnIdex 
			 * 		Spaltenindex der betrachteten Zelle 
			 */
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if(columnIndex!=0)
					return true;
				return false;
			}
			
			/**
			 * Die Methode fireTableCellUpdated stoesst einerseits die Ausfuehrung der jeweiligen 
			 * Operationen beginnend mit "=" in einer Zelle an. Die passende Operation wird mittels 
			 * Strategy Pattern je nach Bedarf ausgewaehlt und durchgefuehrt. Andererseits kann
			 * der Inhalt einer Zelle auch ohne Operation mit "=" ueberschrieben werden, wofuer
			 * die Methode changeData aus dem CSVManager aufgerufen wird.
			 */
			@Override
			public void fireTableCellUpdated(int row, int column) {
				String eingabe = this.getValueAt(row, column).toString();
				if (eingabe.startsWith("="))
				{
					ContextOperation imp = null; // imp steht fuer implementation 
					if (eingabe.toLowerCase().startsWith("=sum("))
					{
						imp = new ContextOperation(new SUM());
					}else if (eingabe.toLowerCase().startsWith("=mean(")){
						imp = new ContextOperation(new MEAN());
					}else if (eingabe.toLowerCase().startsWith("=count(")){
						imp = new ContextOperation(new COUNT());
					}else if(eingabe.contains("+")){
						imp = new ContextOperation(new ArithmeticOperationsAddition());
					}else if(eingabe.contains("-")){
						imp = new ContextOperation(new ArithmeticOperationsSubtraction());
					}else if(eingabe.contains("*")){
						imp = new ContextOperation(new ArithmeticOperationsMultiplication());
					}else if(eingabe.contains("/")){
						imp = new ContextOperation(new ArithmeticOperationsDivision());
					}else{
						imp = new ContextOperation(new ReferenceCell());
					}
					imp.execute(eingabe, table);
				}
				else
					csvManager.changeData(row, column, this.getValueAt(row, column));
				super.fireTableCellUpdated(row, column);
			}
		};
		
		
		table = new JTable(model);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setBackground(UIManager.getColor("Button.background"));
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
	}
	
	

}
