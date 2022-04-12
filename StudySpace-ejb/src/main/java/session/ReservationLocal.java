package session;

import entity.Reservation;
import entity.User;
import javax.ejb.Local;
import java.util.List;

@Local
public interface ReservationLocal {

    void create(Reservation reservation);

    void edit(Reservation reservation);

    void remove(Reservation reservation);

    Reservation find(Object id);

    List<Reservation> findAll();

    List<Reservation> findRange(int[] range);

    List<Reservation> findByIduser(User user);

    Reservation findByIduserIdroom(int iduser, int idroom);

    int count();

}
