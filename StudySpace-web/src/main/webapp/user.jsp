<%@ page import="java.util.List" %>
<%@ page import="entity.Room" %>
<%--
  Created by IntelliJ IDEA.
  User: sara
  Date: 01/09/2021
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script  src="./js/handlerReservation.js"></script>
    <script  src="./js/handlerCalendar.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="./css/userStyle.css">
    <link rel="stylesheet" href="./css/headerStyle.css">
    <title>User - Home</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<body>
    <div  id="header">
        <img  src="./images/study.svg" alt="Icon" id="logo">
        <%if(session.getAttribute("user") != null) { %>
        <p id="welcome"> Welcome ${sessionScope.user.username} </p>
        <% }%>
        <a href="./controllerLogout" id="logout"> Logout </a>
    </div>
    <div>

        <div id="calendar">
            Selected date:
            <input type="date" value="${currentDate}" id="currentDate" onchange="HandlerCalendar.changeDate(this.id);">
        </div>

        <table>

            <% List<Integer> userReservation = (List<Integer>)request.getServletContext().getAttribute("userReservation");%>
            <tr>
                <th> </th>

                <%for(String roomName: (List<String>)request.getServletContext().getAttribute("roomName")){ %>
                <th>
                    <% out.print(roomName);%>
                </th>
                <% } %>
            </tr>

            <tr>
                <td class="timeslot">
                    Morning
                </td>
                <%
                    for(Room room: (List<Room>)request.getServletContext().getAttribute("room")){
                        if(room.getTimeslot().compareTo("morning") == 0){
                %>
                <td>
                        <% String state = "available";
                            if(userReservation.contains(room.getIdroom())){
                                state = "booked";
                            }
                            else if(room.getMaintenance() == 1 || room.getAvailableSeats() == 0){
                                state = "not_available";
                            }
                        %>
                    <div id="<%=room.getIdroom()%>" class="<%=state%>">
                        <div> Total seats: <% out.print(room.getCapacity());%> </div>
                        <div> Available seats: <% out.print(room.getAvailableSeats());%> </div>
                        <br>

                        <%if(state.compareTo("available") == 0){%>
                            <input type="button" class="button" value="Book" onclick="HandlerReservation.reservationEvent(this.parentNode.id)">
                        <%}
                        else if(state.compareTo("booked")==0){%>
                            <input type="button" class="button" value="Cancel" onclick="HandlerReservation.reservationEvent(this.parentNode.id)">
                        <%}
                        else if(state.compareTo("not_available")==0){%>
                            <input type="button" class="not_avail"  value="Not available" disabled>
                        <%}%>
                    </div>
                </td>
                <% }
                }%>
            </tr>

            <tr>
                <td class="timeslot">
                    Afternoon
                </td>
                <%
                    for(Room room: (List<Room>)request.getServletContext().getAttribute("room")){
                        if(room.getTimeslot().compareTo("afternoon") == 0){
                %>
                <td>
                    <%  String state = "available";
                        if(userReservation.contains(room.getIdroom())){
                            state = "booked";
                        }
                        else if(room.getMaintenance() == 1 || room.getAvailableSeats() == 0){
                            state = "not_available";
                        }
                    %>
                    <div id="<%=room.getIdroom()%>" class="<%=state%>">
                        <div> Total seats: <% out.print(room.getCapacity());%> </div>
                        <div> Available seats: <% out.print(room.getAvailableSeats());%> </div>
                        <br>

                        <%if(state.compareTo("available") == 0){%>
                        <input type="button" class="button" value="Book" onclick="HandlerReservation.reservationEvent(this.parentNode.id)">
                        <%}
                        else if(state.compareTo("booked")==0){%>
                        <input type="button" class="button" value="Cancel" onclick="HandlerReservation.reservationEvent(this.parentNode.id)">
                        <%}
                        else if(state.compareTo("not_available")==0){%>
                        <input type="button" class="not_avail" value="Not available" disabled>
                        <%}%>
                    </div>
                </td>
                <% }
                }%>
            </tr>
        </table>
    </div>

</body>

</html>
