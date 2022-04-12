package session;

import entity.Room;
import javax.ejb.Local;
import java.util.List;

@Local
public interface RoomLocal {
    void create(Room customer);

    void edit(Room customer);

    void remove(Room customer);

    Room find(Object id);

    List<Room> findAll();

    List<Room> findRange(int[] range);

    List<String> findHowManyRooms();

    List<Room> findByDate(java.util.Date date);

    Integer findByIdroomDateTimeslot(Integer idroom, java.util.Date date, String timeslot);

    int count();

}
