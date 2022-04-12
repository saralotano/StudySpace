package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "studyspace")
@NamedQueries({
        @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
        , @NamedQuery(name = "Room.findHowManyRooms", query = "SELECT DISTINCT (r.name) FROM Room r")
        , @NamedQuery(name = "Room.findByDate", query = "SELECT r FROM Room r WHERE r.date = :date")
        , @NamedQuery(name = "Room.findByIdroomDateTimeslot", query = "SELECT r FROM Room r WHERE r.idroom = :idroom AND r.date = :date AND r.timeslot = :timeslot")
})

public class Room implements Serializable {
    private int idroom;
    private String name;
    private int capacity;
    private byte maintenance;
    private Date date;
    private String timeslot;
    private int availableSeats;

    @Id
    @Column(name = "idroom", nullable = false)
    public int getIdroom() {
        return idroom;
    }

    public void setIdroom(int idroom) {
        this.idroom = idroom;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "maintenance", nullable = false)
    public byte getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(byte maintenance) {
        this.maintenance = maintenance;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "timeslot", nullable = false, length = 45)
    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    @Basic
    @Column(name = "availableSeats", nullable = false)
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int a) {
        this.availableSeats = a;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return idroom == room.idroom && capacity == room.capacity && maintenance == room.maintenance && availableSeats == room.availableSeats && Objects.equals(name, room.name) && Objects.equals(date, room.date) && Objects.equals(timeslot, room.timeslot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idroom, name, capacity, maintenance, date, timeslot, availableSeats);
    }

    @Override
    public String toString() {
        return "Room{" +
                "idroom=" + idroom +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", maintenance=" + maintenance +
                ", date=" + date +
                ", timeslot='" + timeslot + '\'' +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
