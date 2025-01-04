function checkGUIConnection(){
    console.log('GUI');
}

/**
 * Gets A time (first time input)
 * @returns Returns the list of time A values in [h,m,s]
 */
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

/**
 * Gets B time (second time input)
 * @returns Returns the list of time B values in [h,m,s]
 */
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