/**
 * @author Escalona, J.M.
 */

package compute;

public class Compute {
	public int timeToSeconds(int[] timeUnits) {
		return timeToSeconds(timeUnits[0], timeUnits[1], timeUnits[2]);
	}
	
	public int timeToSeconds(int hr, int min, int sec) {
		return (hr*3600) + (min*60) + sec;
	}
	
	public int findSplitCount(int each, int total) {
		if(each == 0)
			return total / 1;
		return total/each;
	}
	
	public String[][] findDuration(String[] name, int[] total, int duration){
		String[][] out = new String[name.length][5];
		int start = 0, end = duration;
		for(int i = 0; i < name.length; i++) {
			out[i][0] = (i+1) + "";
			out[i][1] = name[i];
			out[i][2] = secondsToString(start); //Start time
			if(i == name.length-1)
				end = timeToSeconds(total[0], total[1], total[2]);
			out[i][3] = secondsToString(end); //End time
			out[i][4] = (end-start) + "";
			start = end;
			end += duration;
		}
		return out;
	}
	
	public String[][] findDurationStartEnd(String[] name, int[] start, int[] end, int durationEach){
		String[][] out = new String[name.length][];
		int startSec = timeToSeconds(start[0], start[1], start[2]);
		int endSec = timeToSeconds(end[0],end[1],end[2]);
		int startTime = startSec, endTime = startSec+durationEach;
		for(int i = 0; i < name.length; i++) {
			out[i] = new String[5];
			out[i][0] = (i+1) + ""; //ID
			out[i][1] = name[i]; //Name
			out[i][2] = secondsToString(startTime); //Start time
			if(i == name.length-1)
				endTime = endSec;
			out[i][3] = secondsToString(endTime); //End time
			out[i][4] = (endTime-startTime) + ""; //Duration
			startTime = endTime;
			endTime += durationEach;
		}
		return out;
	}
	
	private String timeToString(int hr, int min, int sec) {
		String out = "";
		out = (hr < 10) ? out + "0" + hr + ":" : out + hr + ":";
		out = (min < 10) ? out + "0" + min + ":" : out + min + ":";
		out = (sec < 10) ? out + "0" + sec + "" : out + sec + "";
		return out;
	}
	
	public String secondsToString(int seconds) {
		int hr = (int)Math.ceil(seconds/3600);
		seconds -= (hr*3600);
		int min = (int)Math.ceil(seconds/60);
		seconds -= (min*60);
		int sec = seconds;
		return timeToString(hr, min, sec);
	}
	
	public int[] stringToTime(String durationString) {
		int[] d = new int[3];
		d[0] = Integer.parseInt(durationString.substring(0,2));
		d[1] = Integer.parseInt(durationString.substring(3,5));
		d[2] = Integer.parseInt(durationString.substring(6,8));
		return d;
	}
	
	public String[][] redistribute(String[][] input){
		int max = Integer.parseInt(input[input.length-1][4]); //Total Time
		int subsequent = Integer.parseInt(input[input.length-2][4]); //Duration Time
		int excess = max - subsequent;
		if (excess <= 10)
			return input;
		int ctr = input.length-2;
		while(excess > 0) {
			int n = (excess >= 10) ? Integer.parseInt(input[ctr][4]) + 10 : Integer.parseInt(input[ctr][4] + excess);
			input[ctr][4] = n + ""; // Duration
			excess -= 10;
			ctr--;
			if(ctr < 0)
				ctr = input.length-2;
		}
		return input;
	}
}
