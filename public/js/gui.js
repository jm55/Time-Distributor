function checkGUIConnection(){
    console.log('gui.js accessible!');
}

function getATime(){
    var list = [0,0,0];
    if($("#aTime_h").val() != "")
        list[0] = Number.parseInt($("#aTime_h").val());
    if($("#aTime_m").val() != "")
        list[1] = Number.parseInt($("#aTime_m").val());
    if($("#aTime_s").val() != "")
        list[2] = Number.parseInt($("#aTime_s").val());
    return list;
}

function getBTime(){
    var list = [0,0,0];
    if($("#bTime_h").val() != "")
        list[0] = Number.parseInt($("#bTime_h").val());
    if($("#bTime_m").val() != "")
        list[1] = Number.parseInt($("#bTime_m").val());
    if($("#bTime_s").val() != "")
        list[2] = Number.parseInt($("#bTime_s").val());
    return list;
}