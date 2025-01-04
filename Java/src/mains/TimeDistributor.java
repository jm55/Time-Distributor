/**
 * @author Escalona, J.M.
 */

package mains;

import java.util.Scanner;

import javax.swing.JOptionPane;

import files.FileHandler;
import gui.GUI;
import compute.Compute;

public class TimeDistributor {

	private static GUI g;
	private static Controller c;
	
	public static void main(String[] args) {
		gui();
	}

	public static void gui() {
		g = new GUI(true);
		c = new Controller(g);
		g.setDefaultDisplay();
		g.setVisible(true);
		g.clearIO();
		g.setListener(c);
	}
}
