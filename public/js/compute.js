function checkComputeConnection(){
    console.log('compute.js accessible!');
}

/**
 * 
 * @param {Number} hr 
 * @param {Number} min 
 * @param {Number} sec 
 * @returns 
 */
function timeToSeconds(hr, min, sec){
    return Number(hr*3600) + Number(min*60) + sec;
}

/**
 * 
 * @param {Number} each 
 * @param {Number} total 
 * @returns 
 */
function count(each, total){
    return total/each;
}

/**
 * 
 * @param {list} name 
 * @param {list} dur 
 * @param {list} total 
 * @param {Number} duration 
 * @param {Number} recommended
 */
function findDuration(name, total, dur, duration, recommended=0){
    var out = [];
    
    var max = timeToSeconds(total[0], total[1], total[2]);

    if(name.length > recommended || name.length < recommended)
        duration = count(name.length, max);
        
    $("#recommendDuration").text("Recommended Duration: " + secondsToString(duration));

    var start = 0, end = duration;
    for(var i = 0; i < name.length; i++){
        var sub_out = [];
        sub_out.push((i+1) + ""); //ID
        sub_out.push(name[i]); //Name
        sub_out.push(secondsToString(start)); //Start
        // if(i == name.length-1)
        //     end = max;
        sub_out.push(secondsToString(end)); //End
        sub_out.push((Math.floor(end-start)).toFixed(0)); //Duration
        start = end;
        end += duration;

        out.push(sub_out);
    }
    return out;
}

/**
 * 
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

    var clamp0 = Number(startSec), clamp1 = Number(startSec + durationEach);
    for(var i = 0; i < name.length; i++){
        var sub = [];
        sub.push((i+1) + ""); //ID
        sub.push(name[i]); //Name
        sub.push(secondsToString(clamp0)); //Start
        if(i == name.length-1)
            clamp1 = endSec;
        sub.push(secondsToString(clamp1)); //End
        sub.push((Math.floor(clamp1-clamp0)).toFixed(0)); //Duration
        out.push(sub);

        clamp0 = Number(clamp1);
        clamp1 += Number(durationEach);
    }
    return out;
}

/**
 * 
 * @param {*} in_seconds 
 */
function secondsToString(in_seconds){
    var sec_num = parseInt(in_seconds,10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    return hours+':'+minutes+':'+seconds;
}

/**
 * 
 * @param {String} raw_duration 
 */
function splitTimeString(raw_duration){
    var d = [];
    d.push(Number.parseInt(raw_duration.substring(0,2)));
    d.push(Number.parseInt(raw_duration.substring(3,5)));
    d.push(Number.parseInt(raw_duration.substring(6,8)));
    console.log('splitTimeString: ', d);
    return d;
}

function redistribute(data){
    var max = Number.parseInt(data[data.length-1][4]);
    var subsequent = Number.parseInt(data[data.length-2][4]);
    var excess = max - subsequent;
    if(excess > 10){
        var ctr = data.length-2;
        while(excess > 10){
            var n = 0;
            if(excess >= 10)
                n = Number.parseInt(data[ctr][4]) + 10;
            else 
                n = Number.parseInt(data[ctr][4] + excess);
            data[ctr][4] = String(ctr);
            excess -= 10;
            ctr -= 1;
            if(ctr < 0)
                ctr = data.length-2;
        }
    }
    return data;
}