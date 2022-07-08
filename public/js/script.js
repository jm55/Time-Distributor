console.log("script.js");
alert("This web app is not ready, go away.");
var reader = new FileReader();
var data = [];
var list = [];
$(document).ready(()=>{

    reader.onload = function (event) {
        var text = event.target.result;
        if(text.includes('\r'))
            list = text.split('\r\n');
        else
            list = text.split('\n');
        cleanNames();
        $("#nameCounter").text("Name(s) detected: " + list.length);
    };
    console.log("JQuery accessible");
    //Start-End Mode
    $('#startendMode').click(function(){
        var state = $(this).is(':checked');
        console.log('StartEndMode: ' + state);
        if(state){
            $("#aTime_label").text("Start Time (hh:mm:ss):");
            $("#bTime_label").text("End Time (hh:mm:ss):");
        }else{
            $("#aTime_label").text("Total Time (hh:mm:ss):");
            $("#bTime_label").text("Duration Time (hh:mm:ss):");
        }
    });
    //File
    $('#files:file').on("change", function(){
        console.log("File");
        var names = getNames();
        
    });
    //Compute
    $('#compute').click(function(){
        console.log("Compute");
        var names = getNames();
    });
    //Reset
    $('#reset').click(function(){
        console.log("Reset");
    });
    //Save
    $('#save').click(function(){
        console.log("Save");
    });
});

function getNames(){
    var file = readFile('files');
    reader.readAsText(file);
}

//https://love2dev.com/blog/javascript-remove-from-array/#:~:text=Using%20the%20Array%20filter%20Method%20to%20Remove%20Items%20By%20Value
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

function compute(){

}

function flushToTable(data){

}