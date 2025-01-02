/**
 * @author Escalona, J.M.
 */


package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHandler {
	public String[][] readFile() {
		String[][] arr = new String[2][];
		ArrayList<String> raw_input = new ArrayList<String>();
		JFileChooser jfc = new JFileChooser();

		if(jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		File f = jfc.getSelectedFile();
		String extension = f.getName();
		
		if(extension.contains("."))
			extension = extension.substring(extension.lastIndexOf('.'));
		else
			extension = "";
		
		if(!extension.equalsIgnoreCase(".txt")) {
			JOptionPane.showMessageDialog(null, "File selected not allowed, please select .txt files containing names only.");
		}else {
			try {
				Scanner fscan = new Scanner(f);
				while(fscan.hasNext())
					raw_input.add(fscan.nextLine());
				fscan.close();
				arr[0] = new String[1];
				arr[0][0] = f.getAbsolutePath();
				arr[1] = new String[raw_input.size()];
				arr[1] = raw_input.toArray(arr[1]);
				return arr;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Error reading file...");
			}
		}
		return null;
	}
	
	public void writeFile(String[][] input) {
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			f = new File(f.getAbsolutePath() + ".csv");
			System.out.println("Saving as: " + f.getAbsolutePath());
			
			try {
				if(!f.exists())
					f.createNewFile();
				FileWriter fwriter = new FileWriter(f.getAbsolutePath());
				fwriter.write("Number,Name,Start,End,Duration (sec)" + "\n");
				for(String[] i: input)
					fwriter.write(i[0] + ", " + i[1] + ","  + i[2] + ","  + i[3] + "," + i[4] + "\n");
				fwriter.close();
				
				JOptionPane.showMessageDialog(null, "Please do view/open the newly save file in MS Excel and save as a spreadsheet when needed.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error creating new file!");
			}
		}
	}
}
