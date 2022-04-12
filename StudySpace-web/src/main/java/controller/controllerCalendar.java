package controller;

import entity.Reservation;
import entity.User;
import session.ReservationLocal;
import session.RoomLocal;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@WebServlet(name = "controllerCalendar", value = "/controllerCalendar")
public class controllerCalendar extends HttpServlet {

    @EJB
    private RoomLocal roomLocal;

    @EJB
    private ReservationLocal reservationLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String selectedDate = request.getParameter("selectedDate");

        Date dt;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);
        } catch (ParseException ex) {
            dt = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(dt);
        getServletContext().setAttribute("room", roomLocal.findByDate(dt));

        if(session.getAttribute("admin") != null){
            getServletContext().setAttribute("currentDateAdmin", currentDate);
            response.sendRedirect("./admin.jsp");
        }else{

            User user = (User)request.getSession().getAttribute("user");
            List<Reservation> userRes = reservationLocal.findByIduser(user);
            List<Integer> idRoom = new ArrayList<>();

            for(Reservation r : userRes){
                idRoom.add(r.getIdroom());
            }

            getServletContext().setAttribute("userReservation", idRoom);
            getServletContext().setAttribute("currentDate", currentDate);
            response.sendRedirect("./user.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
