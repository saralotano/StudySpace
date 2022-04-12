package utility;

import entity.Reservation;
import entity.Room;
import session.ReservationLocal;
import session.RoomLocal;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@LocalBean
public class ReservationManager implements ReservationManagerLocal {

    @EJB
    private RoomLocal roomLocal;

    @EJB
    private ReservationLocal reservationLocal;

    private ReadWriteLock locksMapLock;
    private HashMap<Integer, ReentrantLock> locks;          //key: idRoom, value: reentrant lock
    private ConcurrentHashMap<Integer, Integer> roomMap;    //key: idRoom, value: number of available seats

    @PostConstruct
    private void init() {
        locksMapLock = new ReentrantReadWriteLock();
        locks = new HashMap<>();
        roomMap = new ConcurrentHashMap<>();

        List<Room> room =  roomLocal.findAll();
        int key;
        for(Room r: room){
            key = r.getIdroom();
            locks.put(key, new ReentrantLock());
            roomMap.put(key, r.getAvailableSeats());
        }
    }


    @Override
    public boolean bookSeatRoom(int idRoom, int idUser) {
        boolean booked;

        locksMapLock.readLock().lock();
        ReentrantLock lock = locks.get(idRoom);
        lock.lock();
        try{
            int availableSeats = roomMap.get(idRoom);

            //a new reservation is possible only if there are available seats
            if(availableSeats > 0){
                Room room = roomLocal.find(idRoom);
                reservationLocal.create(new Reservation(idRoom, idUser));
                room.setAvailableSeats(availableSeats - 1);
                roomLocal.edit(room);
                roomMap.compute(idRoom, (k, v) -> (availableSeats - 1));
                booked = true;
            }
            else{
                booked = false;
            }

        }finally{
            lock.unlock();
            locksMapLock.readLock().unlock();
        }
        return booked;
    }

    @Override
    public boolean deleteReservation(int idRoom, int idUser) {
        boolean deleted;

        locksMapLock.readLock().lock();
        ReentrantLock lock = locks.get(idRoom);
        lock.lock();
        try{
            Room room = roomLocal.find(idRoom);
            Reservation r = reservationLocal.findByIduserIdroom(idUser, idRoom);

            if(r!=null && room!=null){
                int seats = roomMap.get(idRoom);
                reservationLocal.remove(r);
                room.setAvailableSeats(seats + 1);
                roomLocal.edit(room);
                roomMap.compute(idRoom, (k, v) -> (seats + 1));
                deleted = true;
            }
            else{
                deleted = false;
            }

        }finally{
            lock.unlock();
            locksMapLock.readLock().unlock();
        }
        return deleted;
    }

    @Override
    public boolean modifyRoomCapacity(int idRoom, int capacity) {
        boolean modified;

        locksMapLock.writeLock().lock();
        ReentrantLock lock = locks.get(idRoom);
        lock.lock();

        try{
            Room room = roomLocal.find(idRoom);
            //When the admin modifies the capacity of the room the system must take into account
            //the number of available seats in that room in order to consider the reservations
            //made by the users before the change of the capacity value.

            //CASE 1: the room has not previous reservations so the capacity and the number
            // of available seats are set equal to the new capacity value inserted by the admin
            if(room.getCapacity() == room.getAvailableSeats()){
                room.setCapacity(capacity);
                room.setAvailableSeats(capacity);
                roomLocal.edit(room);
                roomMap.compute(idRoom, (k, v) -> (room.getAvailableSeats()));
                modified = true;
            }
            //CASE 2: the room has previous reservations
            else{
                //CASE 2.1: the new capacity is larger than or equal to the original one, so the system
                // must add new available seats to that room and also update its capacity value
                if(capacity >= room.getCapacity()){
                    room.setAvailableSeats(room.getAvailableSeats() + (capacity - room.getCapacity()));
                    room.setCapacity(capacity);
                    roomLocal.edit(room);
                    roomMap.compute(idRoom, (k, v) -> (room.getAvailableSeats()));
                    modified = true;
                }
                //CASE 2.2: the new capacity is smaller than the original one
                else{
                    //CASE 2.2.1: the new capacity is larger or equal to the difference between
                    // the original room capacity and the current available seats
                    if(capacity >= (room.getCapacity() - room.getAvailableSeats())){
                        room.setAvailableSeats(room.getAvailableSeats() - (room.getCapacity() - capacity));
                        room.setCapacity(capacity);
                        roomLocal.edit(room);
                        roomMap.compute(idRoom, (k, v) -> (room.getAvailableSeats()));
                        modified = true;
                    }
                    //CASE 2.2.2: the new capacity is smaller than the difference between
                    // the original room capacity and the current available seats.
                    // In this case the room capacity cannot be updated because there are
                    // already too many reservations for that room
                    else{
                        modified = false;
                    }
                }
            }
        }finally{
            lock.unlock();
            locksMapLock.writeLock().unlock();
        }
        return modified;
    }

    @Override
    public boolean modifyRoomMaintenance(int idRoom, byte maintenance) {
        boolean modified;

        locksMapLock.writeLock().lock();
        ReentrantLock lock = locks.get(idRoom);
        lock.lock();
        try{
            Room room = roomLocal.find(idRoom);

            //it is possible to make the room not available only if there are no
            // reservations already present for that room
            if(room.getAvailableSeats() == room.getCapacity() && maintenance==1){
                room.setMaintenance(maintenance);
                roomLocal.edit(room);
                modified = true;
            }
            else{
                modified = false;
            }

        }finally{
            lock.unlock();
            locksMapLock.writeLock().unlock();
        }
        return modified;
    }

}
