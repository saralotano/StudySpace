function HandlerCalendar(){}

HandlerCalendar.URL = "./controllerCalendar";

HandlerCalendar.changeDate = function(calendar){
    var date = document.getElementById(calendar);
    var queryString = "?selectedDate=" + date.value;
    var url = HandlerCalendar.URL + queryString;
    window.location.href = url
}
