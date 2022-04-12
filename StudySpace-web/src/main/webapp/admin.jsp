<%@ page import="java.util.List" %>
<%@ page import="entity.Room" %>
<%--
  Created by IntelliJ IDEA.
  User: sara
  Date: 07/09/2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script  src="./js/handlerRoom.js"></script>
    <script  src="./js/handlerCalendar.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="./css/adminStyle.css">
    <link rel="stylesheet" href="./css/headerStyle.css">
    <title>Admin - Home</title>
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<body>
    <div  id="header">
        <img  src="./images/study.svg" alt="Icon" id="logo">
        <%if(session.getAttribute("admin") != null) {%>
        <p id="welcome"> Configuration panel </p>
        <% }%>
        <a href="./controllerLogout" id="logout"> Logout </a>

        <div id="calendar">
            Selected date:
            <input type="date" value="${currentDateAdmin}" id="currentDate" onchange="HandlerCalendar.changeDate(this.id);">
        </div>

    </div>

    <div>
        <table>
            <tr>
                <th> </th>

                <%for(String roomName: (List<String>)request.getServletContext().getAttribute("roomName")){ %>
                <th>
                    <% out.print(roomName);%>
                </th>
                <% }  %>
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
                    <div>
                        <div> Current available seats: <%out.print(room.getAvailableSeats());%> </div>
                        <div> Current reservations: <%out.print(room.getCapacity() - room.getAvailableSeats());%> </div>

                        <div id="<%=room.getIdroom()%>" class="room_info">

                            <p>Room capacity: </p>
                            <div id="<%=room.getIdroom()%>">
                                <input type="text" class="capacity" value="<%=room.getCapacity()%>" id="capacity<%=room.getIdroom()%>">
                                <input type="button" value="Modify" name="<%=room.getIdroom()%>" onclick="HandlerRoom.changeCapacity(this.parentNode.id);">
                            </div>

                            <p>Room status:</p>

                            <%if(room.getMaintenance() == 0) {%>
                                <input type="radio" id="A" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);" value="A" checked>
                                <label for="A">Available</label><br>
                                <input type="radio" id="NA" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);" value="NA">
                                <label for="NA">Not available</label>
                            <%}
                            else {%>
                                <input type="radio" id="A" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);"  value="A">
                                <label for="A">Available</label><br>
                                <input type="radio" id="NA" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);"  value="NA" checked>
                                <label for="NA">Not available</label>
                            <%}%>

                        </div>
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
                    <div>
                        <div> Current available seats: <%out.print(room.getAvailableSeats());%> </div>
                        <div> Current reservations: <%out.print(room.getCapacity() - room.getAvailableSeats());%> </div>

                        <div id="<%=room.getIdroom()%>" class="room_info">

                            <p>Room capacity: </p>
                            <div id="<%=room.getIdroom()%>">
                                <input type="text" class="capacity" value="<%=room.getCapacity()%>" id="capacity<%=room.getIdroom()%>">
                                <input type="button" value="Modify" name="<%=room.getIdroom()%>" onclick="HandlerRoom.changeCapacity(this.parentNode.id);">
                            </div>

                            <p>Room status:</p>

                            <%if(room.getMaintenance() == 0) {%>
                                <input type="radio" id="A" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);" value="A" checked>
                                <label for="A">Available</label><br>
                                <input type="radio" id="NA" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);" value="NA">
                                <label for="NA">Not available</label>
                            <%}
                            else {%>
                                <input type="radio" id="A" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);"  value="A">
                                <label for="A">Available</label><br>
                                <input type="radio" id="NA" name="<%=room.getIdroom()%>" onchange="HandlerRoom.changeEvent(this.parentNode.id, this.id);"  value="NA" checked>
                                <label for="NA">Not available</label>
                            <%}%>

                        </div>
                    </div>
                </td>
                <% }
                }%>
            </tr>

        </table>

    </div>

</body>
</html>
