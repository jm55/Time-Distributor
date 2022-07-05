package gui;

import javax.swing.*;
import javax.swing.BorderFactory;

import mains.Controller;

import java.awt.*;
import java.awt.event.*;

/**
 * GUI for the program.
 * Carried over from previous project for CSARCH2
 *
 */
public class GUI extends JFrame{
	//PRIVATE GLOBAL VALUES
	private boolean debug = false;
	private final int WIDTH = 1024, HEIGHT = 620;
	private final int BTNWIDTH = 256, BTNHEIGHT = 50;
	private final String WindowTitle = "TimeDistributor";
	private final String typeFace = "Arial", consoleFace = "Consolas";
	private ActionListener listener;
	private JLabel titleLabel, serverIPLabel, serverPortLabel, consoleLabel, localSelectedFileLabel, remoteSelectedFileLabel, blockSizeLabel;
	private JTextField serverIPField, serverPortField, localSelectedFileField, remoteSelectedFileField ;
	private JTextArea outputArea;
	private JScrollPane outputScroll;
	//private JCheckBox P2PCheckBox;
	private JButton pingBtn, openFileBtn, sendFileBtn, recvFileBtn, resetBtn, aboutBtn, exitBtn, dataPortBtn;
	private JComboBox blockSizes;
	
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
		localSelectedFileField.setText("No File Selected");
		remoteSelectedFileField.setText("");
		serverIPField.setText("");
		serverPortField.setText("");
		blockSizes.setSelectedIndex(0);
	}
	
	/**
	 * Gets contents of outputArea.
	 * @return Contents of outputArea
	 */
	public String getOutputText() {
		return outputArea.getText();
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
//		titleLabel = createLabel(WindowTitle, newFont(Font.BOLD, 24), WIDTH/2-200,24,400,32, SwingConstants.CENTER, SwingConstants.TOP);
//		panel.add(titleLabel);
		
		//INPUT/OUTPUT FIELDS/AREAS
//		serverIPField = createTextField(newFont(Font.PLAIN, 16),32,(64*1)+32,256,32);
//		panel.add(serverIPField);
		
//		dataPortBtn = createButton("Set Data Port", newFont(Font.BOLD,16),32,(72*4), this.BTNWIDTH, this.BTNHEIGHT, listener, "SetDataPort");
//		panel.add(dataPortBtn);
		
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
	private JCheckBox createCheckBox(String text, Font f, int x, int y, int width, int height, boolean selected) {
		JCheckBox cb = new JCheckBox(text,selected);
		cb.setFont(f);
		cb.setBounds(x,y,width,height);	
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
	
	
	/**
	 * Warns if the GUI is in test mode.
	 */
	private void testMode(){
		if(debug)
			JOptionPane.showMessageDialog(null, "GUI under construction", WindowTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private String lorem_ipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
}
