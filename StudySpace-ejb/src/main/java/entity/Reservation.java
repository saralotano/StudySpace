package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservation", schema = "studyspace")
@IdClass(ReservationPK.class)
@NamedQueries({
        @NamedQuery(name = "Reservation.findAll", query = "SELECT r from Reservation r")
        , @NamedQuery(name = "Reservation.findByIduser", query = "SELECT r FROM Reservation r WHERE r.iduser = :iduser")
        , @NamedQuery(name = "Reservation.findByIduserIdroom", query = "SELECT r FROM Reservation r WHERE r.iduser = :iduser AND r.idroom = :idroom")
        , @NamedQuery(name = "Reservation.findByIdreservation", query = "SELECT r FROM Reservation r WHERE r.idreservation = :idreservation")

})
public class Reservation implements Serializable {
    private int idreservation;
    private int iduser;
    private int idroom;

    @Id
    @Column(name = "idreservation", nullable = false)
    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }


    @Id
    @Column(name = "iduser", nullable = false)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Id
    @Column(name = "idroom", nullable = false)
    public int getIdroom() {
        return idroom;
    }

    public void setIdroom(int idroom) {
        this.idroom = idroom;
    }

    public Reservation() {

    }

    public Reservation(int room, int user) {
        this.idroom = room;
        this.iduser = user;
    }

}
