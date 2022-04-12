package controller;

import entity.Reservation;
import entity.User;
import session.ReservationLocal;
import session.RoomLocal;
import utility.ReservationManager;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "controllerReservation", value = "/controllerReservation")
public class controllerReservation extends HttpServlet {

    @EJB
    private RoomLocal roomLocal;

    @EJB
    private ReservationLocal reservationLocal;

    ReservationManager reservationManager = lookupReservationManager();

    private ReservationManager lookupReservationManager(){
        try {
            Context c = new InitialContext();
            return (ReservationManager) c.lookup("java:global/StudySpace-web-1.0-SNAPSHOT/ReservationManager!utility.ReservationManager");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public static final String BOOK = "1";
    public static final String CANCEL = "-1";

    private void updateData(User user){
        Date dt;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd").parse((String) getServletContext().getAttribute("currentDate"));
        } catch (ParseException ex) {
            dt = new Date();
        }

        getServletContext().setAttribute("room", roomLocal.findByDate(dt));
        List<Reservation> userRes = reservationLocal.findByIduser(user);
        List<Integer> idRoom = new ArrayList<>();

        for(Reservation r : userRes){
            idRoom.add(r.getIdroom());
        }

        getServletContext().setAttribute("userReservation", idRoom);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        User user = (User)request.getSession().getAttribute("user");

        if(action.compareTo(BOOK) == 0){

            if(roomLocal.find(roomId).getMaintenance() == 0  && roomLocal.find(roomId).getCapacity() > 0){
                if(!reservationManager.bookSeatRoom(roomId, user.getIduser())){
                    updateData(user);
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Maximum room capacity reached');");
                    out.println("location='user.jsp';");
                    out.println("</script>");
                    out.close();
                    return;
                }
            }
            else {
                updateData(user);
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Sorry, the room is no more available');");
                out.println("location='user.jsp';");
                out.println("</script>");
                out.close();
                return;
            }
        }
        else if(action.compareTo(CANCEL) == 0){
            reservationManager.deleteReservation(roomId, user.getIduser());
        }

        updateData(user);
        response.sendRedirect("./user.jsp");
    }

}
