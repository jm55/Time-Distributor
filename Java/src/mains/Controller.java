package mains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import files.FileHandler;
import gui.GUI;
import compute.Compute;

public class Controller implements ActionListener{
	private GUI gui;
	private FileHandler fh;
	private Compute c;
	private String[][] data, output;
	public Controller(GUI g) {
		this.gui = g;
		this.gui.setListener(this);
		this.fh = new FileHandler();
		this.c = new Compute();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		
		if(e.getActionCommand().equalsIgnoreCase("SaveFile")) {
			fh.writeFile(output);
		}else if(e.getActionCommand().equalsIgnoreCase("Compute")) {
			compute();
		}else if(e.getActionCommand().equalsIgnoreCase("OpenFile")) {
			data = fh.readFile();
			if(data != null){
				if(data[1].length > 0) {
					gui.setSelectedFile(data[0][0]);
					output = new String[data[1].length][];
					int ctr = 1;
					for(int i = 0; i < data[1].length; i++) {
						output[i] = new String[5];
						output[i][0] = i + 1 + "";
						output[i][1] = data[1][ctr-1];
						output[i][2] = "00:00:00";
						output[i][3] = "00:00:00";
						output[i][4] = "0";
						ctr++;
					}
					gui.addMultipleRows(output);
				}
				compute();
			}else {
				gui.setSelectedFile("Path to selected file.");
			}
		}else if(e.getActionCommand().equalsIgnoreCase("Reset")) {
			System.gc();
			gui.clearIO();
		}else if(e.getActionCommand().equalsIgnoreCase("About")) {
			gui.popDialog("Time Distributor\n©2022 Escalona, J.M.\nCCS - DLSU Manila", "About", JOptionPane.QUESTION_MESSAGE);
		}else {
			gui.popDialog("Invalid command received.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void compute() {
		if(validTimes()) {
			int[] total = gui.getTotalTimeStr(), dur = gui.getDurTimeStr();
			output = c.findDuration(data[1], dur, total, gui.getDurTime());
			gui.setRecommendedCount(c.count(gui.getDurTime(), gui.getTotalTime()));
			gui.resetTable();
			gui.addMultipleRows(output);
		}else {
			gui.popDialog("Invalid time configuration. Duration time must be less than the total time.", "Time Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public boolean validTimes() {
		boolean state = true;
		int total = gui.getTotalTime(), dur = gui.getDurTime();
		
		if(total < dur)
			state = false;
		
		if(total == 0 && dur == 0)
			state = false;
		
		return state;
	}

}
