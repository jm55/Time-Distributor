package compute;

public class Compute {
	public int timeToSeconds(int hr, int min, int sec) {
		return (hr*3600) + (min*60) + sec;
	}
	
	public int count(int each, int total) {
		return total/each;
	}
	
	public String[][] findDuration(String[] name, int[] each, int[] total, int duration){
		String[][] out = new String[name.length][];
		
		int max = timeToSeconds(total[0], total[1], total[2]);
		int start = 0, end = duration;
		for(int i = 0; i < name.length; i++) {
			out[i] = new String[5];
			out[i][0] = (i+1) + "";
			out[i][1] = name[i];
			out[i][2] = secondsToString(start); //Start time
			if(i == name.length-1)
				end = max;
			out[i][3] = secondsToString(end); //End time
			out[i][4] = (end-start) + "";
			start = end;
			end += duration;
		}
		
		return out;
	}
	
	public String secondsToString(int seconds) {
		int hr = (int)Math.ceil(seconds/3600);
		seconds -= (hr*3600);
		int min = (int)Math.ceil(seconds/60);
		seconds -= (min*60);
		int sec = seconds;
		
		String out = "";
		
		//HR
		if(hr < 10)
			out += "0" + hr;
		else
			out += hr + "";
		out += ":";
		
		//MIN
		if(min < 10)
			out += "0" + min;
		else
			out += min + "";
		out += ":";
		
		//SEC
		if(sec < 10)
			out += "0" + sec;
		else
			out += sec;
		
		return out;
	}
	
	public int[] splitTimeString(String raw_duration) {
		int[] d = new int[3];
		d[0] = Integer.parseInt(raw_duration.substring(0,2));
		d[1] = Integer.parseInt(raw_duration.substring(3,5));
		d[2] = Integer.parseInt(raw_duration.substring(6,8));
		return d;
	}
}
