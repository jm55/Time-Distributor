//console.log("script.js");
//alert("This web app is not ready, go away.");
var reader = new FileReader();
var data = [];
var list = [];
$(document).ready(()=>{
    checkComputeConnection();
    checkGUIConnection();
    console.log('validator accessible: ' + validator.isBoolean('true'));
    reader.onload = function (event) {
        var text = event.target.result;
        if(text.includes('\r'))
            list = text.split('\r\n');
        else
            list = text.split('\n');
        cleanNames();
        $("#nameCounter").text("Name(s) detected: " + list.length);
    };
    //console.log("JQuery accessible");
    //Start-End Mode
    $('#startendMode').click(function(){
        var state = $(this).is(':checked');
        //console.log('StartEndMode: ' + state);
        if(state){
            $("#aTime_label").text("Start Time (hh:mm:ss):");
            $("#bTime_label").text("End Time (hh:mm:ss):");
            $('#recommendCounter').text("Recommended Count: N/A");
            $("#recommendDuration").text("Recommended Duration: N/A");
        }else{
            $("#aTime_label").text("Total Time (hh:mm:ss):");
            $("#bTime_label").text("Duration Time (hh:mm:ss):");
            $('#recommendCounter').text("Recommended Count: " + (findRecommendedCount()-1).toFixed(0));
            var total = timeToSeconds(aTime[0], aTime[1], aTime[2]);
            var duration = timeToSeconds(bTime[0], bTime[1], bTime[2]);
            var recommended = (count(duration,total)-1).toFixed(0);
            $("#recommendCounter").text("Recommended Count: " + recommended);
        }
    });
    //File
    $('#files:file').on("change", function(){
        //console.log("File");
        getNames();
    });
    //Compute
    $('#compute').click(function(){
        //console.log("Compute");
        if(!getNames())
            alert("No names file provided, please try again.");
        compute(list);
    });
    //Reset
    $('#reset').click(function(){
        //console.log("Reset");
        location.reload(); //hehe, i'm just lazy, that's all
    });
    //Save
    $('#save').click(function(){
        //console.log("Save");
        saveData();
    });
    $('#recommended').click(function(){
        var total = null, n = null;
        total = prompt("Enter total time (hh:mm:ss): ");
        if(total == null)
            return;
        else{
            if(total.length < 8){
                alert("Time format not followed, please try again.");
                return;
            }
            var h = total.substring(0,2),m = total.substring(3,5), s = total.substring(6,7);
            if(!validator.isNumeric(h) && !validator.isNumeric(m) && !validator.isNumeric(s)){
                alert("Invalid entry, please try again.");
                return;
            }
            n = prompt("Enter number of persons: ");
            if(!validator.isNumeric(n)){
                alert("Invalid entry, please try again.");
                return;
            }
            var totalSec = timeToSeconds(Number.parseInt(h),Number.parseInt(m),Number.parseInt(s));
            // @ts-ignore
            var split_time = count(Number.parseInt(n),totalSec);
            alert("Recommended time for specified total time and number of persons:\n" + secondsToString(split_time));
        }
    });
});

function saveData(){
    if(data.length == 0){
        alert('No data to save!');
    }else{
        //Reference: https://stackoverflow.com/a/14966131
        var header = [["ID", "Name", "Start Time", "End Time", "Duration (sec)\n"]];
        let csvContent = "data:text/csv;charset=utf-8," + header.map(e => e.join(",")).join("\n") + data.map(e => e.join(",")).join("\n");
        var encodedUri = encodeURI(csvContent);
        window.open(encodedUri);
    }   
}

function getNames(){
    var file = readFile('files');
    if(file){
        reader.readAsText(file);
        return true;
    }else
        return false;
}

//Reference: https://love2dev.com/blog/javascript-remove-from-array/#:~:text=Using%20the%20Array%20filter%20Method%20to%20Remove%20Items%20By%20Value
function cleanNames(){
    list = list.filter(function(value, index, arr){ 
        return value != '';
    });
    for(var i = 0;  i < list.length-1; i++){
        if(list[i].charAt(list[i].length) == ('\r'))
            list[i].substring(0, list[i].length-2);
    }
}

function readFile (id) {
    var input = document.getElementById(id);
    var files = input.files;
    return files[0];
}

function compute(names){
    if(names.length == 0){
        alert("No names found on memory, please try again.");
        return;
    }
    if(!validTimes()){
        alert("Invalid time configuration. Please check that time configurations are valid.\nTotal time must be greater than duration.\nEnd time must be greater than start time.");
        return;
    }
    var aTime = getATime();
    var bTime = getBTime();
    if(getMode()){ //Start-End Mode
        var start = timeToSeconds(aTime[0], aTime[1], aTime[2]);
        var end = timeToSeconds(bTime[0], bTime[1], bTime[2]);
        var duration = end-start;
        var durationEach = count(list.length,duration);
        var output = findDurationStartEnd(list, aTime, bTime, duration, durationEach);
        data = redistribute(output);
        flushToTable(output);
        $("#recommendDuration").text("Recommended Duration: N/A");
    }else{ //Duration Mode
        var total = timeToSeconds(aTime[0], aTime[1], aTime[2]);
        var duration = timeToSeconds(bTime[0], bTime[1], bTime[2]);
        var recommended = (count(duration,total)-1).toFixed(0);
        $("#recommendCounter").text("Recommended Count: " + recommended);
        if(recommended < list.length)
            alert("Specified duration not optimal, recommending a new duration.");
        else
            alert("Number of people to specified duration is not optimal. Recommending a new duration");
        var output = findDuration(list, aTime, bTime, duration, Number.parseInt(recommended));
        data = redistribute(output);
        flushToTable(output);
    }
}

function flushToTable(data){
    $("#output_table").find("tr:gt(0)").remove();
    for(var d of data){
        var row = document.createElement('tr');
        var id = document.createElement('th');
        var name = document.createElement('th');
        var start = document.createElement('th');
        var end = document.createElement('th');
        var duration = document.createElement('th');

        id.innerHTML = (d[0]);
        name.innerHTML = (d[1]);
        start.innerHTML = (d[2]);
        end.innerHTML = (d[3]);
        duration.innerHTML = (d[4]);

        row.append(id);
        row.append(name);
        row.append(start);
        row.append(end);
        row.append(duration);

        $("#output_table").append(row);
    }
    //$("someTableSelector").find("tr:gt(0)").remove();
}

function getMode(){
    return $("#startendMode").is(':checked');
}

function validTimes(){
    var aTime_raw = getATime();
    var bTime_raw = getBTime();

    var aTime = timeToSeconds(aTime_raw[0],aTime_raw[1],aTime_raw[2]);
    var bTime = timeToSeconds(bTime_raw[0],bTime_raw[1],bTime_raw[2]);

    if(aTime == 0 && bTime == 0)
        return false;

    if(getMode()){
        if(bTime < aTime)
            return false;
    }else{
        if(aTime < bTime)
            return false;
    }
    return true;
}

function findRecommendedCount(){
    var raw_A = getATime();
    var raw_B = getBTime();

    var aTime = timeToSeconds(raw_A[0],raw_A[1],raw_A[2]);
    var bTime = timeToSeconds(raw_B[0],raw_B[1],raw_B[2]);

    return count (bTime, aTime);
}