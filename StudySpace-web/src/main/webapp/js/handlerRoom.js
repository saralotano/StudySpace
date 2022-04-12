function HandlerRoom(){}

HandlerRoom.URL = "./controllerAdmin";
HandlerRoom.CAPACITY = "0";
HandlerRoom.AVAILABLE = "1";
HandlerRoom.NOT_AVAILABLE = "-1";


//MODIFY ROOM CAPACITY
HandlerRoom.changeCapacity = function(idRoom){
    var value = document.getElementById("capacity"+idRoom).value;
    var queryString = "?roomId=" + idRoom + "&action=" + HandlerRoom.CAPACITY + "&value=" + value;
    var url = HandlerRoom.URL + queryString;
    window.location.href = url;
}

//MODIFY ROOM AVAILABILITY
HandlerRoom.changeEvent = function(idRoom, idState){
    var action;
    if(idState == "A")
        action = 1;
    else if(idState == "NA")
        action = -1;
    else
        action = null;

    var queryString = "?roomId=" + idRoom + "&action=" + action;
    var url = HandlerRoom.URL + queryString;
    window.location.href = url;

}
