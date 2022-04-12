package controller;

import session.RoomLocal;
import utility.ReservationManager;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "controllerAdmin", value = "/controllerAdmin")
public class controllerAdmin extends HttpServlet {

    @EJB
    private RoomLocal roomLocal;

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

    public static final String AVAILABLE = "1";
    public static final String NOT_AVAILABLE = "-1";
    public static final String CAPACITY = "0";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        int roomId = Integer.parseInt(request.getParameter("roomId"));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(date);
        String queryDate = (String) getServletContext().getAttribute("currentDateAdmin");

        //Room properties cannot be modified for the current date
        if(queryDate.compareTo(todayDate) != 0){

            if(action.compareTo(AVAILABLE) == 0){
                reservationManager.modifyRoomMaintenance(roomId, (byte) 0);
            }
            else if(action.compareTo(NOT_AVAILABLE) == 0){
                if(!reservationManager.modifyRoomMaintenance(roomId, (byte) 1)){
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('At least one reservation is present. It is not possible to make the room not available');");
                    out.println("location='admin.jsp';");
                    out.println("</script>");
                    out.close();
                    return;
                }
            }
            else if(action.compareTo(CAPACITY) == 0){
                int capacity = Integer.parseInt(request.getParameter("value"));
                if(!reservationManager.modifyRoomCapacity(roomId, capacity)){
                    //if the method returns false it means that the room capacity cannot be updated
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Number of reservations already present > New capacity value');");
                    out.println("location='admin.jsp';");
                    out.println("</script>");
                    out.close();
                    return;
                }
            }

            Date dt = null;
            try {
                dt = new SimpleDateFormat("yyyy-MM-dd").parse(queryDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            getServletContext().setAttribute("room", roomLocal.findByDate(dt));
            response.sendRedirect("./admin.jsp");
        }
        else{
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Room properties cannot be modified for the current date');");
            out.println("location='admin.jsp';");
            out.println("</script>");
            out.close();
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
