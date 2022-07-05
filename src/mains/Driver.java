package mains;

import java.util.Scanner;

import files.FileHandler;
import compute.Compute;

public class Driver {

	public static void main(String[] args) {
		System.out.println("=======================");
		System.out.println("====TimeDistributor====");
		System.out.println("=======================");
		
		Scanner scan = new Scanner(System.in);
		
		FileHandler fh = new FileHandler();

		String[] names = fh.readFile();
		System.out.println("Number of Students: " + names.length);
		System.out.println("Names: ");
		for(String n: names)
			System.out.println(n);	
		
		Compute c = new Compute();
		
		System.out.println("\n\n");
		int[] t = {0,0,0};
		System.out.println("Total Time: ");
		System.out.print("Enter hr: ");
		t[0] = Integer.parseInt(scan.nextLine());
		System.out.print("Enter min: ");
		t[1] = Integer.parseInt(scan.nextLine());
		System.out.print("Enter sec: ");
		t[2] = Integer.parseInt(scan.nextLine());
		int total = c.timeToSeconds(t[0],t[1],t[2]);
		
		System.out.println("\n\n");
		int[] d = {0,0,0};
		System.out.println("Duration Each: ");
		System.out.print("Enter hr: ");
		d[0] = Integer.parseInt(scan.nextLine());
		System.out.print("Enter min: ");
		d[1] = Integer.parseInt(scan.nextLine());
		System.out.print("Enter sec: ");
		d[2] = Integer.parseInt(scan.nextLine());
		int duration = c.timeToSeconds(d[0],d[1],d[2]);
		
		int distribute = c.count(duration,total);
		
		System.out.println("\n\n");
		System.out.println("Total Time: " + t[0] + ":" + t[1] + ":" + t[2]);
		System.out.println("Duration: " + d[0] + ":" + d[1] + ":" + d[2]);
		System.out.println("Count: " + distribute);
		if(distribute > names.length)
			System.out.println("Persons not enough for specified time distribution.");
		
		String[][] list = c.findDuration(names, d, t, duration);
		for(String[] l: list)
			System.out.println(l[0] + ", " + l[1] + ", " + l[2]);
		
		fh.writeFile(list);
		
		System.exit(0);
	}

}
