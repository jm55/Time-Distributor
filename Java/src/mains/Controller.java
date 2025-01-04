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
		if(e.getActionCommand().equalsIgnoreCase("SaveFile")) {
			fh.writeFile(output);
		}
		if(e.getActionCommand().equalsIgnoreCase("Compute")) {
			compute();
		}
		if(e.getActionCommand().equalsIgnoreCase("OpenFile")) {
			openFile();
		}
		if(e.getActionCommand().equalsIgnoreCase("Reset")) {
			reset();
		}
		if(e.getActionCommand().equalsIgnoreCase("About")) {
			gui.popDialog("Time Distributor\n©2022 Escalona, J.M.\nCollege of Computer Science - DLSU Manila\n🇵🇭", "About", JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getActionCommand().equalsIgnoreCase("RecDuration")){
			recommendedDuration();
		}
		if(e.getActionCommand().equalsIgnoreCase("StartEndMode")){
			gui.swapMode(gui.isStartEndMode());
		}
	}
	
	private void recommendedDuration() {
		try {
			String total_time = gui.inputDialog("Enter total time (hh:mm:ss): ");
			String persons = gui.inputDialog("Enter number of persons to divide the time from: ");
			if (total_time == null || persons == null)
				return;
			int total_sec = c.timeToSeconds(c.stringToTime(total_time));
			String split_time = c.secondsToString(c.findSplitCount(Integer.parseInt(persons), total_sec));
			gui.popDialog("Recommended time for each (hh:mm:ss): " + split_time, "Recommended Duration Time", JOptionPane.PLAIN_MESSAGE);
		}catch(NumberFormatException nf) {
			gui.popDialog("Error parsing integer, please ensure that inputs are numbers.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void openFile() {
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
					output[i][1] = data[1][i];
					output[i][2] = "00:00:00";
					output[i][3] = "00:00:00";
					output[i][4] = "0";
				}
				gui.addMultipleRows(output);
			}
		}
	}

	private void reset() {
		System.gc();
		gui.clearIO();
	}
	
	private void compute() {
		if(!validTimes()) {
			gui.popDialog("Invalid time configuration. Please check that time configurations are valid.\nTotal time must be greater than duration.\nEnd time must be greater than start time.", "Time Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(gui.isStartEndMode()) {
			int[] start = gui.getATimeArr(), end = gui.getBTimeArr();
			int duration = c.timeToSeconds(end[0], end[1], end[2]) - c.timeToSeconds(start[0], start[1], start[2]);
			int durationEach = c.findSplitCount(data[1].length, duration);
			output = c.findDurationStartEnd(data[1], start, end, durationEach);
			output = c.redistribute(output);
			gui.setRecommendedCount("N/A");
			gui.resetTable();
			gui.addMultipleRows(output);
			gui.popDialog("Time duration between end and start time (hh:mm:ss): " + c.secondsToString(duration), "Time Duration between Start-End", JOptionPane.INFORMATION_MESSAGE);
		}else {
			int[] total = gui.getATimeArr();
			int dur = c.findSplitCount(data[1].length, c.timeToSeconds(total));
			gui.setBTime(c.stringToTime(c.secondsToString(dur)));
			output = c.findDuration(data[1], total, dur);
			output = c.redistribute(output);
			gui.setRecommendedCount(dur + " sec(s)");
			gui.resetTable();
			gui.addMultipleRows(output);
		}
	}
	
	private boolean validTimes() {
		if(gui.isStartEndMode()) {
			int start = gui.getATime(), end = gui.getBTime();
			return !(end < start || (start == 0 && end == 0));
		}else {
			int total = gui.getATime(), dur = gui.getBTime();
			return !(total < dur);
		}
	}

}
