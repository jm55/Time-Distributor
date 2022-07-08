console.log("script.js");
alert("This web app is not ready, go away.");

$(document).ready(()=>{
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
    //Compute

    //Reset
});