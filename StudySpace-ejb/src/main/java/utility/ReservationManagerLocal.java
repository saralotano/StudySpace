package utility;

import javax.ejb.Local;

@Local
public interface ReservationManagerLocal {

    boolean bookSeatRoom(int idRoom, int idUser);

    boolean deleteReservation(int idRoom, int idUser);

    boolean modifyRoomCapacity(int idRoom, int capacity);

    boolean modifyRoomMaintenance(int idRoom, byte maintenance);
}
