/**
 * @author Escalona, J.M.
 */

package gui;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import compute.Compute;
import mains.Controller;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * GUI for the program.
 * Carried over from previous project for CSARCH2
 *
 */
public class GUI extends JFrame{
	//PRIVATE GLOBAL VALUES
	private boolean debug = false;
	private final int WIDTH = 1024, HEIGHT = 720;
	private final int BTNWIDTH = 256, BTNHEIGHT = 50;
	private final String WindowTitle = "TimeDistributor";
	private final String typeFace = "Arial";
	private ActionListener listener;
	private String[] columns = {"#", "Name", "Start", "End", "Duration (sec)"};
	private JLabel titleLabel, hrLabel, minLabel, secLabel, ATimeLabel, BTimeLabel, colon0, inputColon, selectedFileLabel, recommendedTimeLabel;
	private JTextField hrA, minA, secA, hrB, minB, secB, selectedFile, recommendedTime;
	private JCheckBox startend;
	private JButton openFile, saveFile, compute, about, reset, recommendedDuration;
	private JTable raw_table;
	private JScrollPane table;
	private DefaultTableModel tableModel;
	
	/**
	 * Default constructor that builds the window.
	 */
	public GUI() {
		
	}
	
	/**
	 * Constructor that builds the window with setVisible parameter.
	 * @param setVisible True if window will be set to be visible, false if otherwise.
	 */
	public GUI(boolean setVisible) {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setTitle(WindowTitle);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(setVisible);
		testMode();
	}
	
	/**
	 * Sets the listener for the program
	 * @param c Controller object managing components the window.
	 */
	public void setListener(Controller c) {
		listener = c;
	}
	
	/**
	 * Builds the default JPanel build of the program.
	 */
	public void setDefaultDisplay() {
		buildDisplayContents();
	}
	
	/**
	 * Shows a simple pop-up message using the given parameters.
	 * @param message Message of the pop-up
	 * @param title Title of the pop-up
	 * @param messageType Recommended: JOptionPane.INFORMATION_MESSAGE, JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE, etc.;
	 */
	public void popDialog(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(this, message, title, messageType);
	}
	
	/**
	 * Shows a simple input pop-up message.
	 * @param message Message to ask the user.
	 * @return User entry to input dialog.
	 */
	public String inputDialog(String message) {
		return JOptionPane.showInputDialog(this, message);
	}
	
	/**
	 * Displays pop-up message using the following parameters.
	 * @param message Message of the pop-up
	 * @param title Title of the pop-up
	 * @param optionType Recommended: JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.YES_NO_OPTION, JOptionPane.OK_CANCEL_OPTION
	 * @param messageType Recommended: JOptionPane.INFORMATION_MESSAGE, JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE, etc.;
	 * @return
	 */
	public int confirmDialog(String message, String title, int optionType, int messageType) {
		return JOptionPane.showConfirmDialog(null, message, title, optionType, messageType);
	}
	
	/**
	 * Clears input and output components of the window
	 */
	public void clearIO() {
		hrA.setText("00");
		minA.setText("00");
		secA.setText("00");
		hrB.setText("00");
		minB.setText("00");
		secB.setText("00");
		tableModel.setRowCount(0);
		selectedFile.setText("Path to selected names file.");
		recommendedTime.setText("");
		startend.setSelected(true);
		swapMode(startend.isSelected());
	}
	
	/**
	 * Hide GUI
	 * @return Returns hidden state of GUI, false if not hidden.
	 */
	public boolean hideGUI() {
		setVisible(false);
		return !isVisible();
	}
	
	/**
	 * Show GUI
	 * @return Returns visible state of GUI, false if not visible.
	 */
	public boolean showGUI() {
		setVisible(true);
		return isVisible();
	}
	
	/**
	 * Get the total time string of the time indicated by the user.
	 * Equivalent to 'start' when in 'Start-End Mode'.
	 * @return 
	 */
	public int[] getATimeArr() {
		int[] times = {0,0,0};
		try {
			if(hrA.getText().length() != 0)
				times[0] = Integer.parseInt(hrA.getText());
			if(minA.getText().length() != 0)
				times[1] = Integer.parseInt(minA.getText());
			if(secA.getText().length() != 0)
				times[2] = Integer.parseInt(secA.getText());
		}catch(NumberFormatException e) {
			this.popDialog("Please enter numbers only on the time field.", "Error", JOptionPane.ERROR_MESSAGE);
			for(int i = 0; i < times.length; i++)
				times[i] = 0;
		}
		return times;
	}
	
	/**
	 * Get the duration time string of the time indicated by the user.
	 * Equivalent to 'end' when in 'Start-End Mode'.
	 * @return 
	 */
	public int getATime() {
		int hr = 0, min = 0, sec = 0;
		try {
			if(hrA.getText().length() != 0)
				hr = Integer.parseInt(hrA.getText());
			if(minA.getText().length() != 0)
				min = Integer.parseInt(minA.getText());
			if(secA.getText().length() != 0)
				sec = Integer.parseInt(secA.getText());
		}catch(NumberFormatException e) {
			this.popDialog("Please enter numbers only on the time field.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return new Compute().timeToSeconds(hr, min, sec);
	}
	
	public int[] getBTimeArr() {
		int[] times = {0,0,0};
		try {
			if(hrB.getText().length() != 0)
				times[0] = Integer.parseInt(hrB.getText());
			if(minB.getText().length() != 0)
				times[1] = Integer.parseInt(minB.getText());
			if(secB.getText().length() != 0)
				times[2] = Integer.parseInt(secB.getText());
		}catch(NumberFormatException e) {
			this.popDialog("Please enter numbers only on the time field.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return times;
	}
	
	public int getBTime() {
		int hr = 0, min = 0, sec = 0;
		try {
			if(hrB.getText().length() != 0)
				hr = Integer.parseInt(hrB.getText());
			if(minB.getText().length() != 0)
				min = Integer.parseInt(minB.getText());
			if(secB.getText().length() != 0)
				sec = Integer.parseInt(secB.getText());
		}catch(NumberFormatException e) {
			this.popDialog("Please enter numbers only on the time field.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return new Compute().timeToSeconds(hr, min, sec);
	}
	
	public void setBTime(int[] timeUnits) {
		hrB.setText(timeUnits[0] + "");
		minB.setText(timeUnits[1] + "");
		secB.setText(timeUnits[2] + "");
	}
	
	public void setSelectedFile(String f) {
		selectedFile.setText(f);
	}
	
	public void addMultipleRows(String[][] row) {
		for(String[] r: row)
			addRow(r);
	}
	
	public void addRow(Object[] row) {
		tableModel.addRow(row);
	}
	
	public void setRecommendedCount(String count) {
		recommendedTime.setText(count);
	}
	
	public void resetTable() {
		tableModel.setRowCount(0);
	}
	
	public boolean isStartEndMode() {
		return startend.isSelected();
	}
	
	public void swapMode(boolean startend) {
		if(startend) {
			ATimeLabel.setText("Start Time: ");
			BTimeLabel.setText("End Time: ");
			this.hrB.setEditable(true);
			this.minB.setEditable(true);
			this.secB.setEditable(true);
		}else {
			ATimeLabel.setText("Total Time: ");
			BTimeLabel.setText("Duration Time: ");
			this.hrB.setEditable(false);
			this.minB.setEditable(false);
			this.secB.setEditable(false);
		}
		repaint();
	}
	
	/***
	 * =============================================
		 * PRIVATE METHODS
		 * =============================================
	 */
	
	private void buildDisplayContents(){
		String methodName = "buildDisplayContents()";
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		//LABELS
		//Title Label
		titleLabel = createLabel(WindowTitle, newFont(Font.BOLD, 24), WIDTH/2-200,24,400,32, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(titleLabel);
		//Total Time Label
		hrLabel = createLabel("hh", newFont(Font.BOLD, 16), WIDTH/2-100,72,32,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(hrLabel);
		minLabel = createLabel("mm", newFont(Font.BOLD, 16), WIDTH/2,72,32,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(minLabel);
		secLabel = createLabel("ss", newFont(Font.BOLD, 16), WIDTH/2+100,72,32,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(secLabel);
		colon0 = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2-50,72,32,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(colon0);
		colon0 = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2+50,72,32,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(colon0);
		ATimeLabel = createLabel("Start Time: ", newFont(Font.BOLD, 16), 180,104,200,16, SwingConstants.RIGHT, SwingConstants.TOP);
		panel.add(ATimeLabel);
		BTimeLabel = createLabel("End Time: ", newFont(Font.BOLD, 16), 180,136,200,16, SwingConstants.RIGHT, SwingConstants.TOP);
		panel.add(BTimeLabel);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2-50,100,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2+50,100,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2-50,100,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2-50,132,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2+50,132,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		inputColon = createLabel(":", newFont(Font.BOLD, 16), WIDTH/2-50,132,32,16,  SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(inputColon);
		//Selected File
		selectedFileLabel = createLabel("Selected File: ", newFont(Font.BOLD, 16), WIDTH/2-550,240,400,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(selectedFileLabel);
		recommendedTimeLabel = createLabel("Recommended Time: ", newFont(Font.BOLD, 16), WIDTH/2,240,400,16, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(recommendedTimeLabel);
		
		//INPUT/OUTPUT FIELDS/AREAS
		//Total Time
		hrA = createTextField(newFont(Font.PLAIN, 16),WIDTH/2-128,100,88,24);
		panel.add(hrA);
		minA = createTextField(newFont(Font.PLAIN, 16),WIDTH/2-28,100,88,24);
		panel.add(minA);
		secA = createTextField(newFont(Font.PLAIN, 16),WIDTH/2+72,100,88,24);
		panel.add(secA);
		//Duration Time
		hrB = createTextField(newFont(Font.PLAIN, 16),WIDTH/2-128,132,88,24);
		panel.add(hrB);
		minB = createTextField(newFont(Font.PLAIN, 16),WIDTH/2-28,132,88,24);
		panel.add(minB);
		secB = createTextField(newFont(Font.PLAIN, 16),WIDTH/2+72,132,88,24);
		panel.add(secB);
		//Selected File
		selectedFile = createTextField(newFont(Font.PLAIN, 16),WIDTH/2-290,238,300,24);
		selectedFile.setEditable(false);
		panel.add(selectedFile);
		recommendedTime = createTextField(newFont(Font.PLAIN, 16),WIDTH/2+290,238,110,24);
		recommendedTime.setEditable(false);
		panel.add(recommendedTime);
		
		//BUTTON
		openFile = createButton("Open Names File", newFont(Font.BOLD,16),WIDTH/2-(800/2),172, this.BTNWIDTH, this.BTNHEIGHT, listener, "OpenFile");
		panel.add(openFile);
		compute = createButton("Compute", newFont(Font.BOLD,16),WIDTH/2-(this.BTNWIDTH/2),172, this.BTNWIDTH, this.BTNHEIGHT, listener, "Compute");
		panel.add(compute);
		saveFile = createButton("Save Output", newFont(Font.BOLD,16),WIDTH/2+(this.BTNWIDTH/2)+16,172, this.BTNWIDTH, this.BTNHEIGHT, listener, "SaveFile");
		panel.add(saveFile);
		about = createButton("About",newFont(Font.BOLD,16),WIDTH/2+(this.BTNWIDTH/2)+16,600,this.BTNWIDTH,this.BTNHEIGHT,listener,"About");
		panel.add(about);
		reset = createButton("Reset",newFont(Font.BOLD,16),WIDTH/2-(this.BTNWIDTH/2),600,this.BTNWIDTH,this.BTNHEIGHT,listener,"Reset");
		panel.add(reset);
		recommendedDuration = createButton("Find Recommended Duration",newFont(Font.BOLD,14),WIDTH/2-(800/2),600,this.BTNWIDTH,this.BTNHEIGHT,listener,"RecDuration");
		panel.add(recommendedDuration);
		
		//CHECKBOX
		startend = createCheckBox("Start-End Mode", newFont(Font.BOLD,16), WIDTH/2+250, 130, 180, 24, false,listener,"StartEndMode");
		startend.setSelected(true);
		panel.add(startend);
		
		tableModel = new DefaultTableModel(columns,0);
		raw_table = createTable(newFont(Font.PLAIN, 12), WIDTH/2-(800/2),280,800,300, tableModel);
		raw_table.setModel(tableModel);
		raw_table.getTableHeader().setReorderingAllowed(false);
		table = createScrollPane(raw_table);
		panel.add(table);
		
		add(panel);
		revalidate();
	}
	
	/**
	 * Builds a Font object with the default typeFace.
	 * @param style Style of the font (bold, italicized, normal).
	 * @param size Font size of the Font.
	 * @return Font object with the specified parameter.
	 */
	private Font newFont(int style, int size) {
		return new Font(this.typeFace, style, size);
	}
	
	/**
	 * Builds a Font object using the specified typeface, style and size.
	 * @param typeFace Fontface of the Font.
	 * @param style Style of the font (bold, italicized, normal).
	 * @param size Font size of the Font.
	 * @return Font object with the specified parameter.
	 */
	private Font newFont(String typeFace, int style, int size) {
		return new Font(typeFace, style, size);
	}
	
	/**
	 * Builds a JLabel object given the text data, font, position & size, and text alignment.
	 * @param text Initial value of the object
	 * @param f Font of object
	 * @param x X position of the JLabel (via setBounds)
	 * @param y Y position of the JLabel (via setBounds)
	 * @param width Width of the JLabel (via setBounds)
	 * @param height Height of the JLabel (via setBounds)
	 * @param hAlignment Horizontal alignment of the text in JLabel (e.g. "SwingConstants.LEFT", "SwingConstants.CENTER", "SwingConstants.RIGHT", "SwingConstants.LEADING", "SwingConstants.TRAILING")
	 * @param vAlignment Vertical alignment of the text in JLabel (e.g. "SwingConstants.TOP", "SwingConstants.CENTER", "SwingConstants.BOTTOM")
	 * @return JLabel object configured using the given basic parameters.
	 */
	private JLabel createLabel(String text, Font f, int x, int y, int width, int height, int hAlignment, int vAlignment) {
		JLabel l = new JLabel(text);
		l.setFont(f);
		l.setBounds(x,y,width,height);
		l.setHorizontalAlignment(hAlignment);
		l.setVerticalAlignment(vAlignment);
		
		if(this.debug) {
			l.setOpaque(true);
			l.setBackground(Color.CYAN);
		}
		
		return l;
	}
	
	/**
	 * Builds a JTextField object given the text data, font, and position & size.
	 * @param f Font of object
	 * @param x X position of the JLabel (via setBounds)
	 * @param y Y position of the JLabel (via setBounds)
	 * @param width Width of the JLabel (via setBounds)
	 * @param height Height of the JLabel (via setBounds)
	 * @return JTextField object configured using the given basic parameters.
	 */
	private JTextField createTextField(Font f, int x, int y, int width, int height) {
		JTextField tf = new JTextField();
		tf.setFont(f);
		tf.setBounds(x,y,width,height);	
		return tf;
	}
	
	/**
	 * Builds a JButton object given the text data, font, position & size, and listener. 
	 * @param text Display text of the button
	 * @param f Font 
	 * @param x X position of the JLabel (via setBounds)
	 * @param y Y position of the JLabel (via setBounds)
	 * @param width Width of the JLabel (via setBounds)
	 * @param height Height of the JLabel (via setBounds)
	 * @param listener Listener of the button
	 * @param actionCommand ActionCommand label of the button.
	 * @return JButon object configured using the given basic parameters.
	 */
	private JButton createButton(String text, Font f, int x, int y, int width, int height, ActionListener listener, String actionCommand) {
		JButton b = new JButton(text);
		b.setFont(f);
		b.setBounds(x,y,width,height);
		b.addActionListener(listener);
		b.setActionCommand(actionCommand);
		return b;
	}
	
	/**
	 * Builds a JCheckBox object given the text data, font, position & size, and default initial select state.
	 * @param text Display text of the button
	 * @param f Font 
	 * @param x X position of the JLabel (via setBounds)
	 * @param y Y position of the JLabel (via setBounds)
	 * @param width Width of the JLabel (via setBounds)
	 * @param height Height of the JLabel (via setBounds)
	 * @param selected Default initial select state of the checkbox
	 * @return JCheckBox object configured using the given basic parameters.
	 */
	private JCheckBox createCheckBox(String text, Font f, int x, int y, int width, int height, boolean selected, ActionListener listener, String actionCommand) {
		JCheckBox cb = new JCheckBox(text,selected);
		cb.setFont(f);
		cb.setBounds(x,y,width,height);	
		cb.addActionListener(listener);
		cb.setActionCommand(actionCommand);
		return cb;
	}
	
	/**
	 * Builds a JTextArea object given the text data, font, position & size, and editable state.
	 * @param f Font 
	 * @param x X position of the JLabel (via setBounds)
	 * @param y Y position of the JLabel (via setBounds)
	 * @param width Width of the JLabel (via setBounds)
	 * @param height Height of the JLabel (via setBounds)
	 * @param editable Set whether editable or not
	 * @return Configured JTextArea
	 */
	private JTextArea createTextArea(Font f, int x, int y, int width, int height, boolean editable) {
		JTextArea ta = new JTextArea();
		ta.setFont(f);
		ta.setBounds(x,y,width,height);
		ta.setEditable(editable);
		ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ta.setLineWrap(false);
		
		if(debug) {
			ta.setText(lorem_ipsum);
		}
		
		return ta;
	}
	
	/**
	 * Builds a JScrollPane object given a JText area object.
	 * @param ta JTextArea to add a JScrollPane for.
	 * @return JScrollPane for the given JTextArea.
	 */
	private JScrollPane createScrollPane(JTextArea ta) {
		JScrollPane scroll = new JScrollPane(ta);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(ta.getX(), ta.getY(), ta.getWidth(), ta.getHeight());
		return scroll;
	}

	/**
	 * Builds a JScrollPane object given a JTable area object.
	 * @param ta JTable to add a JScrollPane for.
	 * @return JScrollPane for the given JTable.
	 */
	private JScrollPane createScrollPane(JTable ta) {
		JScrollPane scroll = new JScrollPane(ta);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(ta.getX(), ta.getY(), ta.getWidth(), ta.getHeight());
		return scroll;
	}
	
	/**
	 * Builds a JComboBox object given the specifications.
	 * @param selection List of items for selection
	 * @param f Font
	 * @param x X position
	 * @param y Y positon
	 * @param width Width of object
	 * @param height Height of object
	 * @param listener ActionListener for object.
	 * @return JComboxBox object
	 */
	private JComboBox createComboBox(String[] selection, Font f, int x, int y, int width, int height, ActionListener listener, String actionCommand) {
		JComboBox combo = new JComboBox(selection);
		combo.setFont(f);
		combo.setBounds(x, y, width, height);
		combo.setSelectedItem(0);
		combo.setEditable(false);
		combo.addActionListener(listener);
		combo.setActionCommand(actionCommand);
		return combo;
	}

	private JTable createTable(Font f, int x, int y, int width, int height, DefaultTableModel model) {
		JTable t = new JTable(model);
		t.setBounds(x,y,width,height);
		t.setFont(f);
		return t;
	}
		
	/**
	 * Warns if the GUI is in test mode.
	 */
	private void testMode(){
		if(debug)
			JOptionPane.showMessageDialog(null, "GUI under construction", WindowTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private String lorem_ipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
}
