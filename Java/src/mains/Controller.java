/**
 * @author Escalona, J.M.
 */


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
		boolean initial = true;
		if(e.getActionCommand().equalsIgnoreCase("SaveFile")) {
			fh.writeFile(output);
		}
		if(e.getActionCommand().equalsIgnoreCase("Compute")) {
			compute();
		}
		if(e.getActionCommand().equalsIgnoreCase("OpenFile")) {
			String[][] tempData = fh.readFile();
			if(tempData == null) {
				gui.setSelectedFile("Path to selected file.");
			}else {
				data = tempData.clone();
				gui.resetTable();
				if(data[1].length > 0) {
					gui.setSelectedFile(data[0][0]);
					output = new String[data[1].length][5];
					for(int i = 0; i < data[1].length; i++) {
						output[i][0] = i + 1 + "";
						output[i][1] = data[1][i+1];
						output[i][2] = "00:00:00";
						output[i][3] = "00:00:00";
						output[i][4] = "0";
					}
					gui.addMultipleRows(output);
				}
				if(!initial)
					compute();
				initial = false;
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("Reset")) {
			System.gc();
			gui.clearIO();
		}
		if(e.getActionCommand().equalsIgnoreCase("About")) {
			gui.popDialog("Time Distributor\n©2022 Escalona, J.M.\nCollege of Computer Science - DLSU Manila\n🇵🇭", "About", JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getActionCommand().equalsIgnoreCase("RecDuration")){
			try {
				String total_time = gui.inputDialog("Enter total time (hh:mm:ss): ");
				String persons = gui.inputDialog("Enter number of persons to divide the time from: ");
				int total_sec = c.timeToSeconds(c.splitTimeString(total_time));
				String split_time = c.secondsToString(c.count(Integer.parseInt(persons), total_sec));
				gui.popDialog("Recommended time for each (hh:mm:ss): " + split_time, "Recommended Duration Time", JOptionPane.PLAIN_MESSAGE);
			}catch(NumberFormatException nf) {
				gui.popDialog("Error parsing integer, please ensure that inputs are numbers.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("StartEndMode")){
			gui.swapMode(gui.isStartEndMode());
			if(gui.isStartEndMode())
				gui.popDialog("Start-End Time Mode enabled.", "Start-End Mode", JOptionPane.INFORMATION_MESSAGE);
			else
				gui.popDialog("Start-End Time Mode disabled.", "Start-End Mode", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void compute() {
		if(!validTimes()) {
			gui.popDialog("Invalid time configuration. Please check that time configurations are valid.\nTotal time must be greater than duration.\nEnd time must be greater than start time.", "Time Error", JOptionPane.WARNING_MESSAGE);
		}
		
		if(gui.isStartEndMode()) {
			int[] start = gui.getATimeStr(), end = gui.getBTimeStr();
			int duration = c.timeToSeconds(end[0], end[1], end[2]) - c.timeToSeconds(start[0], start[1], start[2]);
			int durationEach = c.count(data[1].length, duration);
			output = c.findDurationStartEnd(data[1], start, end, duration, durationEach);
			output = c.redistribute(output);
			gui.setRecommendedCount("N/A");
			gui.resetTable();
			gui.addMultipleRows(output);
			gui.popDialog("Time duration between end and start time (hh:mm:ss): " + c.secondsToString(duration), "Time Duration between Start-End", JOptionPane.INFORMATION_MESSAGE);
		}else {
			int[] total = gui.getATimeStr(), dur = gui.getBTimeStr();
			output = c.findDuration(data[1], dur, total, gui.getBTime());
			output = c.redistribute(output);
			gui.setRecommendedCount(c.count(gui.getBTime(), gui.getATime()) + "");
			gui.resetTable();
			gui.addMultipleRows(output);
		}
	}
	
	private boolean validTimes() {
		if(gui.isStartEndMode()) {
			int start = gui.getATime(), end = gui.getBTime();
			if(end < start || (start == 0 && end == 0))
				return false;
		}else {
			int total = gui.getATime(), dur = gui.getBTime();
			if(total < dur || (total == 0 && dur == 0))
				return false;
		}
		return true;
	}

}
