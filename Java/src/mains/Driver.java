/**
 * @author Escalona, J.M.
 */


package mains;

import java.util.Scanner;

import javax.swing.JOptionPane;

import files.FileHandler;
import gui.GUI;
import compute.Compute;

public class Driver {

	private static GUI g;
	private static Controller c;
	
	public static void main(String[] args) {
		gui();
		//tempGUI();
	}

	public static void gui() {
		g = new GUI(true);
		c = new Controller(g);
		g.setDefaultDisplay();
		g.setVisible(true);
		g.clearIO();
		g.setListener(c);
	}
	
	public static void tempGUI() {
		System.out.println("=========================");
		System.out.println("=====TimeDistributor=====");
		System.out.println("=========================");
		System.out.println(" Author: Escalona, J.M.");
		System.out.println("=========================");
		Scanner scan = new Scanner(System.in);
		
		FileHandler fh = new FileHandler();

		String[][] data = fh.readFile();
		if(data  == null) {
			JOptionPane.showMessageDialog(null, "No file selected, exiting program...");
			System.exit(0);
		}
		System.out.println("Number of Students: " + data[1].length);
		System.out.println("Names: ");
		for(String n: data[1])
			System.out.println(n);	
		
		Compute c = new Compute();
		
		System.out.println("\n\n");
		int[] t = {0,0,0};
		System.out.println("Total Time: ");
		String raw_total = JOptionPane.showInputDialog("Enter total time (hh:mm:ss, include ':'): ");
		t[0] = Integer.parseInt(raw_total.substring(0,2));
		t[1] = Integer.parseInt(raw_total.substring(3,5));
		t[2] = Integer.parseInt(raw_total.substring(6,8));
		int total = c.timeToSeconds(t[0],t[1],t[2]);
		
		System.out.println(t[0] + "," + t[1] + "," + t[2]);
		
		System.out.println("\n\n");
		int[] d = {0,0,0};
		String raw_duration = JOptionPane.showInputDialog("Enter duration (hh:mm:ss, include ':'): ");
		d[0] = Integer.parseInt(raw_duration.substring(0,2));
		d[1] = Integer.parseInt(raw_duration.substring(3,5));
		d[2] = Integer.parseInt(raw_duration.substring(6,8));
		int duration = c.timeToSeconds(d[0],d[1],d[2]);
		
		int distribute = c.count(duration,total);
		
		System.out.println("\n\n");
		System.out.println("Total Time: " + t[0] + ":" + t[1] + ":" + t[2]);
		System.out.println("Duration: " + d[0] + ":" + d[1] + ":" + d[2]);
		System.out.println("Count: " + distribute);
		
		if(distribute > data[1].length)
			JOptionPane.showMessageDialog(null, "Persons not enough for specified time distribution.");
		
		String[][] list = c.findDuration(data[1], d, t, duration);
		for(String[] l: list)
			System.out.println(l[0] + ", " + l[1] + ", " + l[2] + ", " + l[3] + ", " + l[4]);
		
		fh.writeFile(list);
		
		scan.close();
		System.exit(0);
	}
}
