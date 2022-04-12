package entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ReservationPK implements Serializable {
    private int idreservation;
    private int iduser;
    private int idroom;

    @Column(name = "idreservation", nullable = false)
    @Id
    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {this.idreservation = idreservation; }

    @Column(name = "iduser", nullable = false)
    @Id
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Column(name = "idroom", nullable = false)
    @Id
    public int getIdroom() {
        return idroom;
    }

    public void setIdroom(int idroom) {
        this.idroom = idroom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationPK that = (ReservationPK) o;
        return idreservation == that.idreservation && iduser == that.iduser && idroom == that.idroom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreservation, iduser, idroom);
    }
}
