package controller;

import entity.Admin;
import entity.Reservation;
import entity.Room;
import entity.User;
import session.AdminLocal;
import session.ReservationLocal;
import session.RoomLocal;
import session.UserLocal;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@WebServlet(name = "controllerLogin", value = "/controllerLogin", loadOnStartup = 1)
public class controllerLogin extends HttpServlet {

    @EJB
    private UserLocal userLocal;

    @EJB
    private AdminLocal adminLocal;

    @EJB
    private ReservationLocal reservationLocal;

    @EJB
    private RoomLocal roomLocal;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getServletContext().setAttribute("currentDate", sdf.format(date));
        getServletContext().setAttribute("currentDateAdmin", sdf.format(date));
        List<String> roomName = roomLocal.findHowManyRooms();
        getServletContext().setAttribute("roomName", roomName);
        List<Room> room = roomLocal.findByDate(date);
        getServletContext().setAttribute("room", room);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //this is an additional control, even if both username and password fields are required
        if(username == null || password == null){
            response.sendRedirect("./index.jsp");
            return;
        }

        User user = userLocal.login(username, password);

        if(user != null) {
            session.setAttribute("user", user);
            session.setAttribute("loggedIn", true);

            List<Reservation> userRes = reservationLocal.findByIduser(user);
            List<Integer> idRoom = new ArrayList<>();

            for(Reservation r : userRes){
                idRoom.add(r.getIdroom());
            }

            getServletContext().setAttribute("userReservation", idRoom);
            response.sendRedirect("./user.jsp");

        }
        else {
            Admin admin = adminLocal.login(username, password);

            if(admin != null){
                session.setAttribute("admin", admin);
                session.setAttribute("loggedIn", true);
                response.sendRedirect("./admin.jsp");
                return;
            }

            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Username and/or password not correct');");
            out.println("location='index.jsp';");
            out.println("</script>");
            out.close();
        }
    }
}
