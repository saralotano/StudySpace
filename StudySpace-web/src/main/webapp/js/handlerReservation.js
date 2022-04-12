function HandlerReservation(){}

HandlerReservation.URL = "./controllerReservation";
HandlerReservation.BOOK = "1";
HandlerReservation.CANCEL = "-1";

HandlerReservation.reservationEvent = function(idRoom){
        var room = document.getElementById(idRoom);
        var state = room.getAttribute("class");
        var action;
        if(state == "booked")
                action = -1;
        else if(state=="available")
                action = 1;
        else
                action = null;
        var queryString = "?roomId=" + idRoom + "&action=" + action;
        var url = HandlerReservation.URL + queryString;
        window.location.href = url;
    }

