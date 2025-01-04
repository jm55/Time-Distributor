function checkComputeConnection(){
    console.log('Compute');
}

/**
 * Converts given time duration (hh:mm:ss) to seconds equivalent
 * @param {Number} hr Hour
 * @param {Number} min Min
 * @param {Number} sec Sec
 * @returns Time equivalent in seconds
 */
function timeToSeconds(hr, min, sec){
    return Number(hr*3600) + Number(min*60) + sec;
}

/**
 * Counts quantity given an total and each. Essentially does total/each
 * @param {Number} each Each amount
 * @param {Number} total Total to be divided
 * @returns Resulting value dividing total to each
 */
function count(each, total){
    return total/each;
}

/**
 * Compute the duration given total time and durations for each name
 * @param {list} name List of people
 * @param {list} dur List of durations
 * @param {list} total Total time (hh:mm:ss)
 * @param {Number} duration Duration for each name
 * @param {Number} recommended Recommended number of people based on the total time and time duration for each
 * @returns List containing ID,Name, Start, End, Duration
 */
function findDuration(name, total, dur, duration, recommended=0){
    var out = [];
    var max = timeToSeconds(total[0], total[1], total[2]);

    //name.length != recommended; then compute for proper duration
    if(name.length > recommended || name.length < recommended)
        duration = count(name.length, max);
        
    $("#recommendDuration").text("Recommended Duration: " + secondsToString(duration)); //Directly sets the recommended duration to the web page

    var start = 0, end = duration;
    for(var i = 0; i < name.length; i++){
        var sub_out = [];
        sub_out.push((i+1) + ""); //ID
        sub_out.push(name[i]); //Name
        sub_out.push(secondsToString(start)); //Start
        sub_out.push(secondsToString(end)); //End
        sub_out.push((Math.floor(end-start)).toFixed(0)); //Duration
        start = end;
        end += duration;
        out.push(sub_out);
    }
    return out;
}

/**
 * Compute the duration givent the start & end time for each name.
 * @param {list} name 
 * @param {list} start
 * @param {list} end 
 * @param {Number} duration 
 * @param {Number} durationEach
 */
function findDurationStartEnd(name, start, end, duration, durationEach){
    var out = [];

    var startSec = timeToSeconds(start[0],start[1],start[2]);
    var endSec = timeToSeconds(end[0],end[1],end[2]);

    var startTime = Number(startSec), endTime = Number(startSec + durationEach);
    for(var i = 0; i < name.length; i++){
        var sub = [];
        sub.push((i+1) + ""); //ID
        sub.push(name[i]); //Name
        sub.push(secondsToString(startTime)); //Start
        if(i == name.length-1)
            endTime = endSec;
        sub.push(secondsToString(endTime)); //End
        sub.push((Math.floor(endTime-startTime)).toFixed(0)); //Duration
        out.push(sub);

        startTime = Number(endTime);
        endTime += Number(durationEach);
    }
    return out;
}

/**
 * Converts seconds into time string equivalent (hh:mm:ss).
 * @param {*} in_seconds Input seconds
 * @returns Time string equivalent of inputted seconds.
 */
function secondsToString(in_seconds){
    var sec_num = parseInt(in_seconds,10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0" + hours;}
    if (minutes < 10) {minutes = "0" + minutes;}
    if (seconds < 10) {seconds = "0" + seconds;}
    return hours+':'+minutes+':'+seconds;
}

/**
 * Divides time string into int values of [h,m,s]
 * @param {String} raw_duration Time string duration in hh:mm:ss
 * @returns List containing [h,m,s]
 */
function splitTimeString(raw_duration){
    var d = [];
    d.push(Number.parseInt(raw_duration.substring(0,2)));
    d.push(Number.parseInt(raw_duration.substring(3,5)));
    d.push(Number.parseInt(raw_duration.substring(6,8)));
    //console.log('splitTimeString: ', d);
    return d;
}

/**
 * Redistributes time duration allocation to each person in the list.
 * @param {list} data 
 * @returns A redistributed time duration list of data
 */
function redistribute(data){
    var max = Number.parseInt(data[data.length-1][4]); //Get highest (always at the end of the list)
    var subsequent = Number.parseInt(data[data.length-2][4]); //Get subsequent value of highest
    /**
     * Explanation goes that the last person might end up with a higher duration than others,
     * thus redistribution will be done for equal workloads.
     * 
     * Sample:
     * Person 0 Duration 10
     * Person 1 Duration 10
     * ...
     * Person n-1 Duration 10
     * Person n Duration 60
     * 
     * The excess (e) time duration is computed as d[n]-d[n-1], where d is duration of the person.
     * The redistributable time is computed as e/(n-1).
     * 
     * The example therefore has an excess time of 50 (60-10) and a redistributable time of 50/(n-1).
     */
    var excess = max - subsequent; //Compute for excess
    var redistributable = excess/(data.length-1);
    if(excess <= 10){
        return data;
    }
    var ctr = data.length-2;
    while(excess > 10){
        var n = (excess >= 10) ? Number.parseInt(data[ctr][4]) + 10 : Number.parseInt(data[ctr][4] + excess);
        data[ctr][4] = String(ctr);
        excess -= 10;
        ctr -= 1;
        if(ctr < 0)
            ctr = data.length-2;
    }
    return data;
}